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
import asia.atmonline.myriskservice.data.storage.repositories.property.DictionaryAddressCityJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.property.DictionaryOccupationTypeJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.property.DictionaryWorkingIndustryJpaRepository;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BasicChecksService extends BaseChecksService {

  private final List<? extends BaseBasicRule<? extends BaseBasicContext>> rules;
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final DictionaryAddressCityJpaRepository dictionaryAddressCityJpaRepository;
  private final DictionaryWorkingIndustryJpaRepository dictionaryWorkingIndustryJpaRepository;
  private final DictionaryOccupationTypeJpaRepository dictionaryOccupationTypeJpaRepository;
  private final BasicSqsProducer producer;

  @Value("${rules.basic.age-2-low}")
  private Integer rulesBasicPermittedAge2Low;
  @Value("${rules.basic.age-2-high}")
  private Integer rulesBasicPermittedAge2High;
  @Value("${rules.basic.income}")
  private Long rulesBasicPermittedIncome;

  public BasicChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories,
      List<? extends BaseBasicRule<? extends BaseBasicContext>> rules, CreditApplicationJpaRepository creditApplicationJpaRepository,
      DictionaryAddressCityJpaRepository dictionaryAddressCityJpaRepository,
      DictionaryWorkingIndustryJpaRepository dictionaryWorkingIndustryJpaRepository,
      DictionaryOccupationTypeJpaRepository dictionaryOccupationTypeJpaRepository, BasicSqsProducer producer) {
    super(repositories);
    this.rules = rules;
    this.creditApplicationJpaRepository = creditApplicationJpaRepository;
    this.dictionaryAddressCityJpaRepository = dictionaryAddressCityJpaRepository;
    this.dictionaryWorkingIndustryJpaRepository = dictionaryWorkingIndustryJpaRepository;
    this.dictionaryOccupationTypeJpaRepository = dictionaryOccupationTypeJpaRepository;
    this.producer = producer;
  }

  @SuppressWarnings({"unchecked"})
  public BasicSqsProducer getProducer() {
    return producer;
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseJpaEntity<BasicSqsProducer> process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity<BasicSqsProducer> response = new RiskResponseJpaEntity<>();
    Optional<CreditApplication> creditApplication = creditApplicationJpaRepository.findById(response.getCredit_application_id());
    if (creditApplication.isPresent() && creditApplication.get().getBorrower() != null) {
      Borrower borrower = creditApplication.get().getBorrower();
      Integer age = Period.between(borrower.getPersonalData().getBirthDate(), LocalDate.now()).getYears();
      WorkingIndustry workingIndustry = borrower.getEmploymentData().getWorkingIndustry();
      OccupationType occupationType = borrower.getBorrowerOccupationType();
      Long income = borrower.getEmploymentData().getIncome().longValue();
      AddressData registrationsAddressData = borrower.getRegistrationAddress();
      for (BaseBasicRule rule : rules) {
        response = rule.execute(rule.getContext(false, dictionaryAddressCityJpaRepository.findAll(), dictionaryOccupationTypeJpaRepository.findAll(),
            dictionaryWorkingIndustryJpaRepository.findAll(), age, rulesBasicPermittedAge2High, rulesBasicPermittedAge2Low, workingIndustry,
            occupationType, income, rulesBasicPermittedIncome, registrationsAddressData));
        if (response != null && REJECT.equals(response.getDecision())) {
          if (response.getRejection_reason_code() != null) {
            rule.saveToBlacklists(borrower.getId(), response.getRejection_reason_code());
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