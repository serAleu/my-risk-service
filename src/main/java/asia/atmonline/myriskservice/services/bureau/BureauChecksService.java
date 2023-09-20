package asia.atmonline.myriskservice.services.bureau;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.borrower.BorrowerJpaRepository;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import asia.atmonline.myriskservice.web.bureau.ccris.client.ExperianCCRISFeignClient;
import asia.atmonline.myriskservice.web.bureau.ccris.dto.confirm_entity.request.ExperianCCRISEntityRequest;
import asia.atmonline.myriskservice.web.bureau.ccris.dto.confirm_entity.request.ExperianCCRISEntityRequestBody;
import asia.atmonline.myriskservice.web.bureau.ccris.dto.confirm_entity.response.ExperianCCRISEntityResponse;
import asia.atmonline.myriskservice.web.bureau.ccris.dto.search.request.ExperianCCRISSearchRequest;
import asia.atmonline.myriskservice.web.bureau.ccris.dto.search.request.ExperianCCRISSearchRequestBody;
import asia.atmonline.myriskservice.web.bureau.ccris.dto.search.response.ExperianCCRISSearchResponse;
import asia.atmonline.myriskservice.web.bureau.retrieve_report.client.ExperianRetrieveReportFeignClient;
import asia.atmonline.myriskservice.web.bureau.retrieve_report.dto.request.ExperianRetrieveReportRequest;
import asia.atmonline.myriskservice.web.bureau.retrieve_report.dto.request.ExperianRetrieveReportRequestBody;
import asia.atmonline.myriskservice.web.bureau.retrieve_report.dto.response.ExperianRetrieveReportResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BureauChecksService implements BaseRiskChecksService {

  private final ExperianCCRISFeignClient ccrisFeignClient;
  private final ExperianRetrieveReportFeignClient retrieveReportFeignClient;
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final BorrowerJpaRepository borrowerJpaRepository;
  private final ObjectMapper mapper;

  @Value("${experian.ccris.group-code}")
  private String experianCCRISGroupCode;
  @Value("${experian.ccris.product-type}")
  private String experianCCRISProductType;
  @Value("${experian.review.product-type}")
  private String experianReviewProductType;

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = new RiskResponseJpaEntity();
    response.setRequestId(request.getId());
    response.setApplicationId(request.getApplicationId());
    Long borrowerId = creditApplicationJpaRepository.findBorrowerIdById(request.getApplicationId());
    Optional<Borrower> borrower = borrowerJpaRepository.findById(borrowerId);
    if (borrower.isPresent()) {
      try {

//    первый запрос в экспириан проверяем есть ли в бюро информация о заемщике
        ExperianCCRISSearchRequest searchRequest = new ExperianCCRISSearchRequest()
            .setRequest(new ExperianCCRISSearchRequestBody()
                .setCountry("MY")
                .setDOB(borrower.get().getPersonalData().getBirthDate())
                .setEntityId(borrower.get().getBorrowerNIC())
                .setEntityId2("")
                .setGroupCode(experianCCRISGroupCode)
                .setProductType(experianCCRISProductType));

        String ccrisResponseString = ccrisFeignClient.getCCRISInfo(searchRequest.toString());

        ExperianCCRISSearchResponse experianCCRISSearchResponse = mapper.readValue(ccrisResponseString, ExperianCCRISSearchResponse.class);

        experianCCRISSearchResponse.getCCrisIdentities().forEach(identityResponse -> {

          //    второй запрос в экспириан запускаем формирование отчета, в ответ получаю токены для запроса сформированного отчета

          ExperianCCRISEntityRequest entityRequest = new ExperianCCRISEntityRequest()
              .setRequest(new ExperianCCRISEntityRequestBody()
                  .setCRefId(identityResponse.getCRefId())
                  .setConsentGranted("Y")
                  .setEntityKey(identityResponse.getEntityKey())
                  .setEmailAddress(borrower.get().getPersonalData().getEmail())
                  .setMobileNo(borrower.get().getPersonalData().getMobilePhone())
                  .setEnquiryPurpose("NEW APPLICATION")
                  .setProductType(experianReviewProductType)
                  .setLastKnownAddress(""));

          String entityResponseString = ccrisFeignClient.getCCRISInfo(entityRequest.toString());
          try {
            ExperianCCRISEntityResponse experianCCRISEntityResponse = mapper.readValue(ccrisResponseString, ExperianCCRISEntityResponse.class);

            //    третий запрос в экспириан передаем полученные во 2ом запросе токены для получения сформированного отчета

            ExperianRetrieveReportRequest reportRequest = new ExperianRetrieveReportRequest()
                .setRequest(new ExperianRetrieveReportRequestBody()
                    .setToken1(experianCCRISEntityResponse.getToken1())
                    .setToken2(experianCCRISEntityResponse.getToken2()));

            ExperianRetrieveReportResponse reportResponse = retrieveReportFeignClient.getExperianReport(reportRequest);
          } catch (JsonProcessingException e) {

          }

        });

      } catch (JsonProcessingException e) {

      }
    }
    return response;
  }
}