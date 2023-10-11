package asia.atmonline.myriskservice.services.basic;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.borrower.BorrowerJpaRepository;
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
  private final BorrowerJpaRepository borrowerJpaRepository;
  private final DictionaryAddressCityJpaRepository dictionaryAddressCityJpaRepository;
  private final DictionaryWorkingIndustryJpaRepository dictionaryWorkingIndustryJpaRepository;
  private final DictionaryOccupationTypeJpaRepository dictionaryOccupationTypeJpaRepository;

  @Value("${rules.basic.age-2-low}")
  private Integer rulesBasicPermittedAge2Low;
  @Value("${rules.basic.age-2-high}")
  private Integer rulesBasicPermittedAge2High;
  @Value("${rules.basic.income}")
  private Long rulesBasicPermittedIncome;

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    return process(request, false);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request, boolean isFinalCheck) {
    RiskResponseJpaEntity response = new RiskResponseJpaEntity();
    response.setRequestId(request.getId());
    response.setApplicationId(request.getApplicationId());
    Long borrowerId = creditApplicationJpaRepository.findBorrowerIdById(request.getApplicationId());
    Optional<Borrower> borrower = borrowerJpaRepository.findById(borrowerId);
    if (borrower.isPresent()) {
      Integer age =
          borrower.get().getPersonalData() != null && borrower.get().getPersonalData().getBirthDate() != null ? Period.between(borrower.get().getPersonalData().getBirthDate(), LocalDate.now()).getYears() : null;
      WorkingIndustryDictionary clientWorkingIndustry =
          borrower.get().getEmploymentData() != null && borrower.get().getEmploymentData().getWorkingIndustry() != null ? borrower.get()
              .getEmploymentData().getWorkingIndustry() : null;
      OccupationTypeDictionary clientOccupationType =
          borrower.get().getEmploymentData() != null && borrower.get().getEmploymentData().getOccupationType() != null ? borrower.get()
              .getEmploymentData().getOccupationType() : null;
      Long income =
          borrower.get().getEmploymentData() != null && borrower.get().getEmploymentData().getIncome() != null ? borrower.get().getEmploymentData()
              .getIncome().longValue() : null;
      AddressCityDictionary registrationsAddressData = borrower.get().getResidenceCity();
      List<AddressCityDictionary> addressCityDictionaries = dictionaryAddressCityJpaRepository.findAll();
      List<OccupationTypeDictionary> occupationTypeDictionaries = dictionaryOccupationTypeJpaRepository.findAll();
      List<WorkingIndustryDictionary> workingIndustryDictionaries = dictionaryWorkingIndustryJpaRepository.findAll();
      for (BaseBasicRule rule : rules) {
        response = rule.execute(
            rule.getContext(response, isFinalCheck, addressCityDictionaries, occupationTypeDictionaries, workingIndustryDictionaries, age,
                rulesBasicPermittedAge2High, rulesBasicPermittedAge2Low, clientWorkingIndustry, clientOccupationType, income,
                rulesBasicPermittedIncome, registrationsAddressData));
        if (response != null && REJECT.equals(response.getDecision())) {
          if (response.getRejectionReason() != null) {
            rule.saveToBlacklists(request.getApplicationId(), borrower.get().getId(), response.getRejectionReason());
          }
          return response;
        }
      }
    }
    return response;
  }
}