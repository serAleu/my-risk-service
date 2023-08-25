package asia.atmonline.myriskservice.services.cooldown;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.credit.CreditJpaRepository;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownContext;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownRule;
import asia.atmonline.myriskservice.rules.cooldown.applim_2d.CooldownApplim2dContext;
import asia.atmonline.myriskservice.rules.cooldown.applim_5w.CooldownApplim5wContext;
import asia.atmonline.myriskservice.rules.cooldown.applim_9m.CooldownApplim9mContext;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CooldownChecksService implements BaseRiskChecksService {

  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final CreditJpaRepository creditJpaRepository;
  private final List<? extends BaseCooldownRule<? extends BaseCooldownContext>> rules;

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = new RiskResponseJpaEntity();
    Optional<CreditApplication> creditApplication = creditApplicationJpaRepository.findById(request.getApplicationId());
    if (creditApplication.isPresent() && creditApplication.get().getBorrower() != null) {
      Long borrowerId = creditApplication.get().getBorrower().getId();
      List<CreditApplication> creditApplicationList = creditApplicationJpaRepository.findByBorrowerId(borrowerId);
      List<Credit> creditList = creditJpaRepository.findByBorrowerId(borrowerId);
      Integer numOf2DApplications = creditApplicationJpaRepository.countByBorrowerIdAndRequestedAtBetween(borrowerId,
          getLocalDateTimeInPastFromHours(CooldownApplim2dContext.HOURS_TO_CHECK_NUM), LocalDateTime.now());
      Integer numOf5wApplications = creditApplicationJpaRepository.countByBorrowerIdAndRequestedAtBetween(borrowerId,
          getLocalDateTimeInPastFromHours(getHoursFromDays(CooldownApplim5wContext.DAYS_TO_CHECK_NUM)), LocalDateTime.now());
      Integer numOf9mApplications = creditApplicationJpaRepository.countByBorrowerIdAndRequestedAtBetween(borrowerId,
          getLocalDateTimeInPastFromHours(getHoursFromDays(CooldownApplim9mContext.DAYS_TO_CHECK_NUM)), LocalDateTime.now());
      for (BaseCooldownRule rule : rules) {
        response = rule.execute(
            rule.getContext(creditApplicationList, creditList, numOf2DApplications, numOf5wApplications, numOf9mApplications));
        if (response != null && REJECT.equals(response.getDecision())) {
          if (response.getRejectionReason() != null) {
            rule.saveToBlacklists(borrowerId, response.getRejectionReason());
          }
          return response;
        }
      }
    }
    return response;
  }

  private Integer getHoursFromDays(Integer days) {
    return days * 24;
  }

  private LocalDateTime getLocalDateTimeInPastFromHours(Integer hours) {
    return LocalDateTime.now().minusHours(hours);
  }
}