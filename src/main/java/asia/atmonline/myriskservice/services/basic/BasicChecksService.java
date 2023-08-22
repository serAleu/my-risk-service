package asia.atmonline.myriskservice.services.basic;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BASIC;
import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.borrower.AddressData;
import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BasicChecksService extends BaseChecksService {

  private final List<? extends BaseBasicRule<? extends BaseBasicContext>> rules;
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;

  public BasicChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories,
      List<? extends BaseBasicRule<? extends BaseBasicContext>> rules, CreditApplicationJpaRepository creditApplicationJpaRepository) {
    super(repositories);
    this.rules = rules;
    this.creditApplicationJpaRepository = creditApplicationJpaRepository;
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseJpaEntity<BasicSqsProducer> process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity<BasicSqsProducer> response = new RiskResponseJpaEntity<>();
    Optional<CreditApplication> creditApplication = creditApplicationJpaRepository.findById(response.getCreditApplicationId());
    if (creditApplication.isPresent() && creditApplication.get().getBorrower() != null) {
      Borrower borrower = creditApplication.get().getBorrower();
      Integer age = Period.between(borrower.getPersonalData().getBirthDate(), LocalDate.now()).getYears();
      WorkingIndustry workingIndustry = borrower.getEmploymentData().getWorkingIndustry();
      OccupationType occupationType = borrower.getBorrowerOccupationType();
      Long income = borrower.getEmploymentData().getIncome().longValue();
      AddressData registrationsAddressData = borrower.getRegistrationAddress();
      for (BaseBasicRule rule : rules) {
        response = rule.execute(rule.getContext(age, workingIndustry, occupationType, income, registrationsAddressData));
        if (response != null && REJECT.equals(response.getDecision())) {
          if (response.getRejectionReasonCode() != null) {
            rule.saveToBlacklists(borrower.getId(), response.getRejectionReasonCode());
          }
          return response;
        }
      }
    }
    return response;
  }

  @Override
  public boolean accept(RiskRequestJpaEntity request) {
    return request != null && BASIC.equals(request.getCheckType()) && request.getCreditApplicationId() != null;
  }
}