package asia.atmonline.myriskservice.services.blacklists;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.BL;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.blacklists.calculations.ClientBlLevelJpaEntity;
import asia.atmonline.myriskservice.data.entity.blacklists.entity.BlackListRule;
import asia.atmonline.myriskservice.data.entity.blacklists.entity.BlacklistBaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistBankAccountJpaEntity;
import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistIdNumberJpaEntity;
import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistPhoneJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.BlacklistRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.blacklists.BlacklistBankAccountJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.blacklists.BlacklistIdNumberJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.blacklists.BlacklistPhoneJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.blacklists.BlacklistRuleJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.blacklists.calculations.ClientBlLevelJpaRepository;
import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.repositories.borrower.BorrowerJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.credit.CreditJpaRepository;
import asia.atmonline.myriskservice.enums.application.ProductCode;
import asia.atmonline.myriskservice.enums.risk.BlacklistSource;
import asia.atmonline.myriskservice.messages.request.impl.BlacklistsRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.blacklist.BlacklistSqsProducer;
import asia.atmonline.myriskservice.rules.blacklist.phone.BlacklistPhoneContext;
import asia.atmonline.myriskservice.rules.blacklist.phone.BlacklistPhoneRule;
import asia.atmonline.myriskservice.services.BaseChecksService;
import asia.atmonline.myriskservice.web.blacklist.dto.BlacklistRecordForm;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BlacklistChecksService extends BaseChecksService<BlacklistsRequest, BlacklistRequestJpaEntity> {

  private final BlacklistPhoneRule blacklistPhoneRule;
  private final BlacklistPhoneJpaRepository blacklistPhoneJpaRepository;
  private final BlacklistBankAccountJpaRepository blacklistBankAccountJpaRepository;
  private final BlacklistIdNumberJpaRepository blacklistIdNumberJpaRepository;
  private final BlacklistRuleJpaRepository blacklistRuleJpaRepository;
  private final ClientBlLevelJpaRepository clientBlLevelJpaRepository;
  private final CreditJpaRepository creditJpaRepository;
  private final BorrowerJpaRepository borrowerJpaRepository;

  public BlacklistChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories, BlacklistPhoneRule blacklistPhoneRule,
      BlacklistPhoneJpaRepository blacklistPhoneJpaRepository, BlacklistBankAccountJpaRepository blacklistBankAccountJpaRepository,
      BlacklistIdNumberJpaRepository blacklistIdNumberJpaRepository, BlacklistRuleJpaRepository blacklistRuleJpaRepository,
      ClientBlLevelJpaRepository clientBlLevelJpaRepository, CreditJpaRepository creditJpaRepository, BorrowerJpaRepository borrowerJpaRepository) {
    super(repositories);
    this.blacklistPhoneRule = blacklistPhoneRule;
    this.blacklistPhoneJpaRepository = blacklistPhoneJpaRepository;
    this.blacklistBankAccountJpaRepository = blacklistBankAccountJpaRepository;
    this.blacklistIdNumberJpaRepository = blacklistIdNumberJpaRepository;
    this.blacklistRuleJpaRepository = blacklistRuleJpaRepository;
    this.clientBlLevelJpaRepository = clientBlLevelJpaRepository;
    this.creditJpaRepository = creditJpaRepository;
    this.borrowerJpaRepository = borrowerJpaRepository;
  }

  @Override
  public RiskResponseJpaEntity<BlacklistSqsProducer> process(BlacklistsRequest request) {
    String phoneNum = request.getPhone_num();
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

  @Override
  public boolean accept(BlacklistsRequest request) {
    return request != null && BL.equals(request.getCheck()) && request.getPhone_num() != null;
  }

  @Override
  public BlacklistRequestJpaEntity getRequestEntity(BlacklistsRequest request) {
    return new BlacklistRequestJpaEntity().setPhoneNum(request.getPhone_num());
  }

  @Transactional(readOnly = true)
  public boolean checkPhonesBlackList(final List<String> phones) {
    return blacklistPhoneJpaRepository.existsByPhoneInAndExpiredAtAfter(phones, LocalDateTime.now());
  }

  @Transactional
  public void save(BlacklistRecordForm form, BlacklistSource source, Long userId) {
    if (form.getRuleId() == null) {
      return;
    }

    BlackListRule rule = blacklistRuleJpaRepository.findByRuleId(form.getRuleId());
    if (rule == null) {
      return;
    }

    Long ruleId = rule.getId();
    if (StringUtils.isNotEmpty(form.getIdNumber()) && rule.isAddIdNumber()) {
      List<BlacklistIdNumberJpaEntity> blackListIdNumbers = blacklistIdNumberJpaRepository.findByIdNumberAndRuleIdAndExpiredAtAfterOrderByAddedAtDesc(
          form.getIdNumber(), ruleId, LocalDateTime.now());
      BlacklistIdNumberJpaEntity entity;
      if (CollectionUtils.isNotEmpty(blackListIdNumbers)) {
        entity = blackListIdNumbers.get(0);
      } else {
        entity = new BlacklistIdNumberJpaEntity(null);
      }
      entity.setIdNumber(form.getIdNumber());
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
    final BlackListRule rule = blacklistRuleJpaRepository.findByRuleId(form.getRuleId());
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

  private void saveIdNumToBl(BlacklistIdNumberJpaEntity entity, BlacklistRecordForm form, BlacklistSource source, Long userId) {
    getFilledBlEntity(entity, form, source, userId);
    blacklistIdNumberJpaRepository.save(entity);
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