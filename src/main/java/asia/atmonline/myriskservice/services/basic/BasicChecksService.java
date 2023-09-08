package asia.atmonline.myriskservice.services.basic;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.RiskRequestJpaRepository;
import asia.atmonline.myriskservice.data.risk.repositories.RiskResponseJpaRepository;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.property.DictionaryAddressCityJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.property.DictionaryOccupationTypeJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.property.DictionaryWorkingIndustryJpaRepository;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicChecksService implements BaseRiskChecksService {

  private final List<? extends BaseBasicRule<? extends BaseBasicContext>> rules;
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final DictionaryAddressCityJpaRepository dictionaryAddressCityJpaRepository;
  private final DictionaryWorkingIndustryJpaRepository dictionaryWorkingIndustryJpaRepository;
  private final DictionaryOccupationTypeJpaRepository dictionaryOccupationTypeJpaRepository;
  private final RiskResponseJpaRepository responseJpaRepository;
  private final RiskRequestJpaRepository requestJpaRepository;

  @Value("${rules.basic.age-2-low}")
  private Integer rulesBasicPermittedAge2Low;
  @Value("${rules.basic.age-2-high}")
  private Integer rulesBasicPermittedAge2High;
  @Value("${rules.basic.income}")
  private Long rulesBasicPermittedIncome;

  @Override
  public RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request) {
    return process(request, false);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request, boolean isFinalCheck) {
    RiskResponseRiskJpaEntity response = new RiskResponseRiskJpaEntity();
    response.setRequestId(request.getId());
    Optional<CreditApplication> creditApplication = creditApplicationJpaRepository.findById(response.getApplicationId());
    if (creditApplication.isPresent() && creditApplication.get().getBorrower() != null) {
      Borrower borrower = creditApplication.get().getBorrower();
      Integer age = Period.between(borrower.getPersonalData().getBirthDate(), LocalDate.now()).getYears();
      WorkingIndustryDictionary clientWorkingIndustry = borrower.getEmploymentData().getWorkingIndustry();
      OccupationTypeDictionary clientOccupationType = borrower.getEmploymentData().getOccupationType();
      Long income = borrower.getEmploymentData().getIncome().longValue();
      AddressCityDictionary registrationsAddressData = borrower.getResidenceCity();
      for (BaseBasicRule rule : rules) {
        response = rule.execute(rule.getContext(isFinalCheck, dictionaryAddressCityJpaRepository.findAll(), dictionaryOccupationTypeJpaRepository.findAll(),
            dictionaryWorkingIndustryJpaRepository.findAll(), age, rulesBasicPermittedAge2High, rulesBasicPermittedAge2Low, clientWorkingIndustry,
            clientOccupationType, income, rulesBasicPermittedIncome, registrationsAddressData));
        if (response != null && REJECT.equals(response.getDecision())) {
          if (response.getRejectionReason() != null) {
            rule.saveToBlacklists(borrower.getId(), response.getRejectionReason());
          }
          return response;
        }
      }
    }
    return response;
  }
}