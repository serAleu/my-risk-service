package asia.atmonline.myriskservice.services.blacklists;

import static asia.atmonline.myriskservice.enums.risk.BlacklistSource.SYSTEM;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.blacklists.BlacklistBaseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.blacklists.BlacklistRule;
import asia.atmonline.myriskservice.data.risk.entity.blacklists.calculations.ClientBlLevelRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.blacklists.impl.BlacklistBankAccountRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.blacklists.impl.BlacklistPassportNumberRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.blacklists.impl.BlacklistPhoneRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.blacklists.BlacklistBankAccountRiskJpaRepository;
import asia.atmonline.myriskservice.data.risk.repositories.blacklists.BlacklistPassportNumberRiskJpaRepository;
import asia.atmonline.myriskservice.data.risk.repositories.blacklists.BlacklistPhoneRiskJpaRepository;
import asia.atmonline.myriskservice.data.risk.repositories.blacklists.BlacklistRuleRiskJpaRepository;
import asia.atmonline.myriskservice.data.risk.repositories.blacklists.calculations.ClientBlLevelRiskJpaRepository;
import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.repositories.borrower.BorrowerJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.credit.CreditJpaRepository;
import asia.atmonline.myriskservice.enums.application.ProductCode;
import asia.atmonline.myriskservice.enums.risk.BlacklistSource;
import asia.atmonline.myriskservice.enums.risk.RejectionReasonCode;
import asia.atmonline.myriskservice.rules.blacklist.phone.BlacklistPhoneContext;
import asia.atmonline.myriskservice.rules.blacklist.phone.BlacklistPhoneRule;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import asia.atmonline.myriskservice.web.blacklist.dto.BlacklistRecordForm;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class BlacklistChecksService implements BaseRiskChecksService {

  private final BlacklistPhoneRule blacklistPhoneRule;
  private final BlacklistPhoneRiskJpaRepository blacklistPhoneJpaRepository;
  private final BlacklistBankAccountRiskJpaRepository blacklistBankAccountJpaRepository;
  private final BlacklistPassportNumberRiskJpaRepository blacklistPassportNumberJpaRepository;
  private final BlacklistRuleRiskJpaRepository blacklistRuleJpaRepository;
  private final ClientBlLevelRiskJpaRepository clientBlLevelJpaRepository;
  private final CreditJpaRepository creditJpaRepository;
  private final BorrowerJpaRepository borrowerJpaRepository;

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    String phoneNum = request.getPhone();
    Long numberOfNotFinishedCredits = 0L;
    Long numberOfFinishedCredits = 0L;
    Optional<Borrower> borrower = borrowerJpaRepository.findBorrowerByPersonalDataMobilePhone(phoneNum);
    if (borrower.isPresent()) {
      Long borrowerId = borrower.get().getId();
      numberOfNotFinishedCredits = creditJpaRepository.countNotFinishedCredits(borrowerId);
      numberOfFinishedCredits = creditJpaRepository.countFinishedCredits(borrowerId);
      clientBlLevelJpaRepository.save(new ClientBlLevelRiskJpaEntity()
          .setBorrowerId(borrowerId)
          .setPhone(phoneNum)
          .setBlLevel(numberOfFinishedCredits.intValue()));
    }
    List<BlacklistPhoneRiskJpaEntity> entities = blacklistPhoneJpaRepository.findByPhoneAndExpiredAtAfterOrderByAddedAtDesc(phoneNum,
        LocalDateTime.now());
    RiskResponseJpaEntity response = blacklistPhoneRule.execute(new BlacklistPhoneContext(entities, numberOfNotFinishedCredits.intValue(), numberOfFinishedCredits.intValue()));
    response.setPhone(request.getPhone());
    response.setRequestId(request.getId());
    return response;
  }

  @Transactional(readOnly = true)
  public boolean checkPhoneBlackList(final String phone) {
    return checkPhonesBlackList(List.of(phone));
  }

  @Transactional(readOnly = true)
  public boolean checkPhonesBlackList(final List<String> phones) {
    return blacklistPhoneJpaRepository.existsByPhoneInAndExpiredAtAfter(phones, LocalDateTime.now());
  }

  public boolean checkBankAccountBlacklist(String accounts) {
    return accounts != null && checkBankAccountsBlacklist(List.of(accounts));
  }

  @Transactional(readOnly = true)
  public boolean checkBankAccountsBlacklist(final List<String> accounts) {
    return CollectionUtils.isNotEmpty(accounts)
        && blacklistBankAccountJpaRepository.existsByBankAccountInAndExpiredAtAfter(accounts, LocalDateTime.now());
  }

  public boolean checkPassportNumberBlacklist(String passportNumber) {
    return passportNumber != null && checkPassportNumbersBlacklist(List.of(passportNumber));
  }

  @Transactional(readOnly = true)
  public boolean checkPassportNumbersBlacklist(final List<String> passportNumber) {
    List<String> passportNumberCaseSensitivity = Optional.ofNullable(passportNumber).orElse(List.of()).stream()
        .map(num -> List.of(num.toLowerCase(),num.toUpperCase()))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
    return CollectionUtils.isNotEmpty(passportNumberCaseSensitivity)
        && blacklistPassportNumberJpaRepository.existsByPassportNumberInAndExpiredAtAfter(passportNumberCaseSensitivity, LocalDateTime.now());
  }

  public void save(Long applicationId, Long borrowerId, RejectionReasonCode code) {
    Optional<Borrower> borrower = borrowerJpaRepository.findById(borrowerId);
    borrower.ifPresent(value -> save(value, code, applicationId));
  }

  public void save(Borrower borrower, RejectionReasonCode code, Long applicationId) {
    BlacklistRecordForm form = new BlacklistRecordForm();
    form.setBankAccount(borrower.getBorrowerAccount());
    form.setPassportNumber(borrower.getBorrowerNIC());
    form.setPhone(borrower.getBorrowerPhone());
    form.setProductCode(ProductCode.IL);
    form.setId(code.name());
    form.setCreditApplicationId(applicationId);
    save(form, SYSTEM, null);
  }

  @Transactional
  public void save(BlacklistRecordForm form, BlacklistSource source, Long userId) {
    if (form.getId() == null) {
      return;
    }

    Optional<BlacklistRule> optionalBlacklistRule = blacklistRuleJpaRepository.findById(form.getId());
    BlacklistRule rule = null;
    if(optionalBlacklistRule.isPresent()) {
      rule = optionalBlacklistRule.get();
    }
    if (rule == null || rule.getDays() == null || rule.getDays() <= 0) {
      return;
    }

    String blReason = rule.getId();
    if (StringUtils.isNotEmpty(form.getPassportNumber()) && rule.isAddIdNumber()) {
      List<BlacklistPassportNumberRiskJpaEntity> blackListIdNumbers = blacklistPassportNumberJpaRepository.findByPassportNumberAndBlReasonAndExpiredAtAfterOrderByAddedAtDesc(
          form.getPassportNumber(), blReason, LocalDateTime.now());
      BlacklistPassportNumberRiskJpaEntity entity;
      if (CollectionUtils.isNotEmpty(blackListIdNumbers)) {
        entity = blackListIdNumbers.get(0);
      } else {
        entity = new BlacklistPassportNumberRiskJpaEntity(null);
      }
      entity.setPassportNumber(form.getPassportNumber());
      entity.setCreditApplicationId(form.getCreditApplicationId());
      saveIdNumToBl(entity, form, source, userId);
    }
    if (StringUtils.isNotEmpty(form.getPhone()) && rule.isAddPhone()) {
      List<BlacklistPhoneRiskJpaEntity> blackListPhones = blacklistPhoneJpaRepository.findByPhoneAndBlReasonAndExpiredAtAfterOrderByAddedAtDesc(
          form.getPhone(), blReason, LocalDateTime.now());
      BlacklistPhoneRiskJpaEntity entity;
      if (CollectionUtils.isNotEmpty(blackListPhones)) {
        entity = blackListPhones.get(0);
      } else {
        entity = new BlacklistPhoneRiskJpaEntity(null);
      }
      entity.setCreditApplicationId(form.getCreditApplicationId());
      entity.setPhone(form.getPhone());
      savePhoneNumToBl(entity, form, source, userId);
    }
    if (StringUtils.isNotEmpty(form.getBankAccount()) && rule.isAddBankAccount()) {
      BlacklistBankAccountRiskJpaEntity entity;
      List<BlacklistBankAccountRiskJpaEntity> blackListBankAccounts = blacklistBankAccountJpaRepository.findByBankAccountAndBlReasonAndExpiredAtAfterOrderByAddedAtDesc(
          form.getBankAccount(), blReason, LocalDateTime.now());
      if (CollectionUtils.isNotEmpty(blackListBankAccounts)) {
        entity = blackListBankAccounts.get(0);
      } else {
        entity = new BlacklistBankAccountRiskJpaEntity(null);
      }
      entity.setCreditApplicationId(form.getCreditApplicationId());
      entity.setBankAccount(form.getBankAccount());
      saveBankAccountToBl(entity, form, source, userId);
    }
  }

  private void getFilledBlEntity(BlacklistBaseRiskJpaEntity entity, BlacklistRecordForm form, BlacklistSource source, Long userId) {
    Optional<BlacklistRule> optionalBlacklistRule = blacklistRuleJpaRepository.findById(form.getId());
    if(optionalBlacklistRule.isPresent()) {
      BlacklistRule rule = optionalBlacklistRule.get();
      if (Objects.nonNull(entity.getId())) {
        entity.setExpiredAt(entity.getExpiredAt().plusDays(rule.getDays()));
      } else {
        final LocalDateTime addedAt = LocalDateTime.now();
        final LocalDateTime expiredAt = addedAt.plusDays(rule.getDays());
        entity.setAddedAt(addedAt);
        entity.setExpiredAt(expiredAt);
      }
      entity.setBlLevel(defineBlLevel(form.getProductCode()));
      entity.setAddedBy(userId);
      entity.setSource(source);
      entity.setBlReason(form.getId());
    }
  }

  private void saveIdNumToBl(BlacklistPassportNumberRiskJpaEntity entity, BlacklistRecordForm form, BlacklistSource source, Long userId) {
    getFilledBlEntity(entity, form, source, userId);
    blacklistPassportNumberJpaRepository.save(entity);
  }

  private void savePhoneNumToBl(BlacklistPhoneRiskJpaEntity entity, BlacklistRecordForm form, BlacklistSource source, Long userId) {
    getFilledBlEntity(entity, form, source, userId);
    blacklistPhoneJpaRepository.save(entity);
  }

  private void saveBankAccountToBl(BlacklistBankAccountRiskJpaEntity entity, BlacklistRecordForm form, BlacklistSource source, Long userId) {
    getFilledBlEntity(entity, form, source, userId);
    blacklistBankAccountJpaRepository.save(entity);
  }

  private int defineBlLevel(ProductCode code) {
    return switch (code) {
      case IL -> 0;
      case RS1 -> 1;
      case RS2 -> 2;
      case RS3 -> 3;
      case RS4 -> 4;
      case RS5 -> 5;
      case RS6 -> 6;
      case RS7 -> 7;
    };
  }
}