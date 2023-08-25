package asia.atmonline.myriskservice.services.blacklists;

import static asia.atmonline.myriskservice.enums.risk.BlacklistSource.SYSTEM;

import asia.atmonline.myriskservice.data.entity.blacklists.calculations.ClientBlLevelJpaEntity;
import asia.atmonline.myriskservice.data.entity.blacklists.entity.BlacklistBaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.blacklists.entity.BlacklistRule;
import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistBankAccountJpaEntity;
import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistPassportNumberJpaEntity;
import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistPhoneJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.blacklists.BlacklistBankAccountJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.blacklists.BlacklistPassportNumberJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.blacklists.BlacklistPhoneJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.blacklists.BlacklistRuleJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.blacklists.calculations.ClientBlLevelJpaRepository;
import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.repositories.borrower.BorrowerJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.credit.CreditJpaRepository;
import asia.atmonline.myriskservice.enums.application.ProductCode;
import asia.atmonline.myriskservice.enums.risk.BlacklistSource;
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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class BlacklistChecksService implements BaseRiskChecksService {

  private final BlacklistPhoneRule blacklistPhoneRule;
  private final BlacklistPhoneJpaRepository blacklistPhoneJpaRepository;
  private final BlacklistBankAccountJpaRepository blacklistBankAccountJpaRepository;
  private final BlacklistPassportNumberJpaRepository blacklistPassportNumberJpaRepository;
  private final BlacklistRuleJpaRepository blacklistRuleJpaRepository;
  private final ClientBlLevelJpaRepository clientBlLevelJpaRepository;
  private final CreditJpaRepository creditJpaRepository;
  private final BorrowerJpaRepository borrowerJpaRepository;

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = new RiskResponseJpaEntity();
    response.setRequestId(request.getId());
    String phoneNum = request.getPhone();
    Long borrowerId = null;
    Integer numberOfNotFinishedCredits = 0;
    Integer numberOfFinishedCredits = 0;
    Optional<Borrower> borrower = borrowerJpaRepository.findBorrowerByPersonalDataMobilePhone(phoneNum);
    if (borrower.isPresent()) {
      borrowerId = borrower.get().getId();
      numberOfNotFinishedCredits = creditJpaRepository.notFinishedCreditsCount(borrowerId);
      numberOfFinishedCredits = creditJpaRepository.finishedCreditsCount(borrowerId);
    }
    clientBlLevelJpaRepository.save(new ClientBlLevelJpaEntity()
        .setBorrowerId(borrowerId)
        .setPhone(phoneNum)
        .setBlLevel(numberOfFinishedCredits));
    List<BlacklistPhoneJpaEntity> entities = blacklistPhoneJpaRepository.findByPhoneAndExpiredAtAfterOrderByAddedAtDesc(phoneNum,
        LocalDateTime.now());
    return blacklistPhoneRule.execute(new BlacklistPhoneContext(entities, numberOfNotFinishedCredits, numberOfFinishedCredits));
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

  public void save(Long borrowerId, Integer ruleId) {
    Optional<Borrower> borrower = borrowerJpaRepository.findById(borrowerId);
    borrower.ifPresent(value -> save(value, Long.valueOf(ruleId)));
  }

  public void save(Borrower borrower, Long ruleId) {
    BlacklistRecordForm form = new BlacklistRecordForm();
    form.setBankAccount(borrower.getBorrowerAccount());
    form.setPassportNumber(borrower.getBorrowerNIC());
    form.setPhone(borrower.getBorrowerPhone());
    form.setRuleId(ruleId);
    save(form, SYSTEM, null);
  }

  @Transactional
  public void save(BlacklistRecordForm form, BlacklistSource source, Long userId) {
    if (form.getRuleId() == null) {
      return;
    }

    BlacklistRule rule = blacklistRuleJpaRepository.findByRuleId(form.getRuleId());
    if (rule == null || rule.getDays() == null || rule.getDays() <= 0) {
      return;
    }

    Long ruleId = rule.getId();
    if (StringUtils.isNotEmpty(form.getPassportNumber()) && rule.isAddIdNumber()) {
      List<BlacklistPassportNumberJpaEntity> blackListIdNumbers = blacklistPassportNumberJpaRepository.findByPassportNumberAndRuleIdAndExpiredAtAfterOrderByAddedAtDesc(
          form.getPassportNumber(), ruleId, LocalDateTime.now());
      BlacklistPassportNumberJpaEntity entity;
      if (CollectionUtils.isNotEmpty(blackListIdNumbers)) {
        entity = blackListIdNumbers.get(0);
      } else {
        entity = new BlacklistPassportNumberJpaEntity(null);
      }
      entity.setPassportNumber(form.getPassportNumber());
      entity.setCreditApplicationId(form.getCreditApplicationId());
      saveIdNumToBl(entity, form, source, userId);
    }
    if (StringUtils.isNotEmpty(form.getPhone()) && rule.isAddPhone()) {
      List<BlacklistPhoneJpaEntity> blackListPhones = blacklistPhoneJpaRepository.findByPhoneAndRuleIdAndExpiredAtAfterOrderByAddedAtDesc(
          form.getPhone(), ruleId, LocalDateTime.now());
      BlacklistPhoneJpaEntity entity;
      if (CollectionUtils.isNotEmpty(blackListPhones)) {
        entity = blackListPhones.get(0);
      } else {
        entity = new BlacklistPhoneJpaEntity(null);
      }
      entity.setCreditApplicationId(form.getCreditApplicationId());
      entity.setPhone(form.getPhone());
      savePhoneNumToBl(entity, form, source, userId);
    }
    if (StringUtils.isNotEmpty(form.getBankAccount()) && rule.isAddBankAccount()) {
      BlacklistBankAccountJpaEntity entity;
      List<BlacklistBankAccountJpaEntity> blackListBankAccounts = blacklistBankAccountJpaRepository.findByBankAccountAndRuleIdAndExpiredAtAfterOrderByAddedAtDesc(
          form.getBankAccount(), ruleId, LocalDateTime.now());
      if (CollectionUtils.isNotEmpty(blackListBankAccounts)) {
        entity = blackListBankAccounts.get(0);
      } else {
        entity = new BlacklistBankAccountJpaEntity(null);
      }
      entity.setCreditApplicationId(form.getCreditApplicationId());
      entity.setBankAccount(form.getBankAccount());
      saveBankAccountToBl(entity, form, source, userId);
    }
  }

  private void getFilledBlEntity(BlacklistBaseJpaEntity entity, BlacklistRecordForm form, BlacklistSource source, Long userId) {
    final BlacklistRule rule = blacklistRuleJpaRepository.findByRuleId(form.getRuleId());
    if (Objects.nonNull(entity.getId())) {
      entity.setExpiredAt(entity.getExpiredAt().plusDays(rule.getDays()));
    } else {
      final LocalDateTime addedAt = LocalDateTime.now();
      final LocalDateTime expiredAt = addedAt.plusDays(rule.getDays());
      entity.setAddedAt(addedAt);
      entity.setExpiredAt(expiredAt);
    }
    entity.setBlLevel(defineBlLevel(form.getProductCode()));
    entity.setRule(rule);
    entity.setAddedBy(userId);
    entity.setSource(source);
  }

  private void saveIdNumToBl(BlacklistPassportNumberJpaEntity entity, BlacklistRecordForm form, BlacklistSource source, Long userId) {
    getFilledBlEntity(entity, form, source, userId);
    blacklistPassportNumberJpaRepository.save(entity);
  }

  private void savePhoneNumToBl(BlacklistPhoneJpaEntity entity, BlacklistRecordForm form, BlacklistSource source, Long userId) {
    getFilledBlEntity(entity, form, source, userId);
    blacklistPhoneJpaRepository.save(entity);
  }

  private void saveBankAccountToBl(BlacklistBankAccountJpaEntity entity, BlacklistRecordForm form, BlacklistSource source, Long userId) {
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