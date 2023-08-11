package asia.atmonline.myriskservice.services.cooldown;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.COOLDOWN;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.CooldownRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.credit.CreditJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.CooldownRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownContext;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownRule;
import asia.atmonline.myriskservice.rules.cooldown.applim_2d.CooldownApplim2dContext;
import asia.atmonline.myriskservice.rules.cooldown.applim_5w.CooldownApplim5wContext;
import asia.atmonline.myriskservice.rules.cooldown.applim_9m.CooldownApplim9mContext;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CooldownChecksService extends BaseChecksService<CooldownRequest, CooldownRequestJpaEntity> {

  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final CreditJpaRepository creditJpaRepository;
  private final List<? extends BaseCooldownRule<? extends BaseCooldownContext>> rules;

  public CooldownChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories,
      CreditApplicationJpaRepository creditApplicationJpaRepository, CreditJpaRepository creditJpaRepository,
      List<? extends BaseCooldownRule<? extends BaseCooldownContext>> rules) {
    super(repositories);
    this.creditApplicationJpaRepository = creditApplicationJpaRepository;
    this.creditJpaRepository = creditJpaRepository;
    this.rules = rules;
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseJpaEntity<CooldownSqsProducer> process(CooldownRequest request) {
    RiskResponseJpaEntity<CooldownSqsProducer> response = new RiskResponseJpaEntity<>();
    List<CreditApplication> creditApplicationList = creditApplicationJpaRepository.findByBorrowerId(request.getBorrowerId());
    List<Credit> creditList = creditJpaRepository.findByBorrowerId(request.getBorrowerId());
    Integer numOf2DApplications = creditApplicationJpaRepository.countByBorrowerIdAndRequestedAtBetween(request.getBorrowerId(),
        getLocalDateTimeInPastFromHours(CooldownApplim2dContext.HOURS_TO_CHECK_NUM), LocalDateTime.now());
    Integer numOf5wApplications = creditApplicationJpaRepository.countByBorrowerIdAndRequestedAtBetween(request.getBorrowerId(),
        getLocalDateTimeInPastFromHours(getHoursFromDays(CooldownApplim5wContext.DAYS_TO_CHECK_NUM)), LocalDateTime.now());
    Integer numOf9mApplications = creditApplicationJpaRepository.countByBorrowerIdAndRequestedAtBetween(request.getBorrowerId(),
        getLocalDateTimeInPastFromHours(getHoursFromDays(CooldownApplim9mContext.DAYS_TO_CHECK_NUM)), LocalDateTime.now());
    for (BaseCooldownRule rule : rules) {
      response = rule.execute(
          rule.getCurrentCooldownRuleContext(creditApplicationList, creditList, numOf2DApplications, numOf5wApplications, numOf9mApplications));
      if (REJECT.equals(response.getDecision())) {
        return response;
      }
    }
    return response;
  }

  @Override
  public boolean accept(CooldownRequest request) {
    return request != null && COOLDOWN.equals(request.getCheck()) && request.getBorrowerId() != null;
  }

  @Override
  public CooldownRequestJpaEntity getRequestEntity(CooldownRequest request) {
    return new CooldownRequestJpaEntity().setBorrowerId(request.getBorrowerId());
  }

  private Integer getHoursFromDays(Integer days) {
    return days * 24;
  }

  private LocalDateTime getLocalDateTimeInPastFromHours(Integer hours) {
    return LocalDateTime.now().minusHours(hours);
  }
}