package asia.atmonline.myriskservice.services.basic;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.BASIC;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.BasicRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.data.storage.entity.borrower.AddressData;
import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.repositories.borrower.BorrowerJpaRepository;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import asia.atmonline.myriskservice.messages.request.impl.BasicRequest;
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
public class BasicChecksService extends BaseChecksService<BasicRequest, BasicRequestJpaEntity> {

  private final List<? extends BaseBasicRule<? extends BaseBasicContext>> rules;
  private final BorrowerJpaRepository borrowerJpaRepository;

  public BasicChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories,
      List<? extends BaseBasicRule<? extends BaseBasicContext>> rules, BorrowerJpaRepository borrowerJpaRepository) {
    super(repositories);
    this.rules = rules;
    this.borrowerJpaRepository = borrowerJpaRepository;
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseJpaEntity<BasicSqsProducer> process(BasicRequest request) {
    RiskResponseJpaEntity<BasicSqsProducer> response = new RiskResponseJpaEntity<>();
    Long borrowerId = request.getBorrowerId();
    Optional<Borrower> borrower = borrowerJpaRepository.findById(borrowerId);
    if(borrower.isPresent()) {
      Integer age = Period.between(borrower.get().getPersonalData().getBirthDate(), LocalDate.now()).getYears();
      WorkingIndustry workingIndustry = borrower.get().getEmploymentData().getWorkingIndustry();
      OccupationType occupationType = borrower.get().getBorrowerOccupationType();
      Long income = borrower.get().getEmploymentData().getIncome().longValue();
      AddressData registrationsAddressData = borrower.get().getRegistrationAddress();
      for (BaseBasicRule rule : rules) {
        response = rule.execute(rule.getContext(age, workingIndustry, occupationType, income, registrationsAddressData));
        if (response != null && REJECT.equals(response.getDecision())) {
          if (response.getRejectionReasonCode() != null) {
            rule.saveToBlacklists(borrowerId, response.getRejectionReasonCode());
          }
          return response;
        }
      }
    }
    return response;
  }

  @Override
  public boolean accept(BasicRequest request) {
    return request != null && BASIC.equals(request.getCheck());
  }

  @Override
  public BasicRequestJpaEntity getRequestEntity(BasicRequest request) {
    return new BasicRequestJpaEntity().setBorrowerId(request.getBorrowerId());
  }
}