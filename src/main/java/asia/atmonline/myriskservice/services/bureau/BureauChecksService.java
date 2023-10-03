package asia.atmonline.myriskservice.services.bureau;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.experian.CreditBureauInfo;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.experian.CreditBureauInfoDetails;
import asia.atmonline.myriskservice.data.risk.repositories.external_responses.experian.CreditBureauInfoDetailsJpaRepository;
import asia.atmonline.myriskservice.data.risk.repositories.external_responses.experian.CreditBureauInfoJpaRepository;
import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.borrower.BorrowerJpaRepository;
import asia.atmonline.myriskservice.enums.risk.ExperianCallStatus;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import asia.atmonline.myriskservice.web.bureau.experian.client.ExperianCCRISFeignClient;
import asia.atmonline.myriskservice.web.bureau.experian.dto.confirm_entity.request.ExperianCCRISEntityRequest;
import asia.atmonline.myriskservice.web.bureau.experian.dto.confirm_entity.request.ExperianCCRISEntityRequestBody;
import asia.atmonline.myriskservice.web.bureau.experian.dto.confirm_entity.response.ExperianCCRISConfirmEntityResponse;
import asia.atmonline.myriskservice.web.bureau.experian.dto.search.request.ExperianCCRISSearchRequest;
import asia.atmonline.myriskservice.web.bureau.experian.dto.search.request.ExperianCCRISSearchRequestBody;
import asia.atmonline.myriskservice.web.bureau.experian.dto.search.response.ExperianCCRISIdentityResponse;
import asia.atmonline.myriskservice.web.bureau.experian.dto.search.response.ExperianCCRISSearchResponse;
import asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.request.ExperianRetrieveReportRequest;
import asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.request.ExperianRetrieveReportRequestBody;
import asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.response.ExperianRetrieveReportResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BureauChecksService implements BaseRiskChecksService {

  private final ExperianCCRISFeignClient ccrisFeignClient;
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final BorrowerJpaRepository borrowerJpaRepository;
  private final CreditBureauInfoJpaRepository creditBureauInfoJpaRepository;
  private final CreditBureauInfoDetailsJpaRepository creditBureauInfoDetailsJpaRepository;
  private final ObjectMapper mapper;

  @Value("${experian.ccris.group-code}")
  private String experianCCRISGroupCode;
  @Value("${experian.ccris.product-type}")
  private String experianCCRISProductType;
  @Value("${experian.review.product-type}")
  private String experianReviewProductType;
  @Value("${experian.retrieve-report.timeout-before-request}")
  private Long experianRetrieveReportTimeoutBeforeRequest;

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = new RiskResponseJpaEntity();
    response.setRequestId(request.getId());
    response.setApplicationId(request.getApplicationId());
    Long borrowerId = creditApplicationJpaRepository.findBorrowerIdById(request.getApplicationId());
    Optional<Borrower> borrower = borrowerJpaRepository.findById(borrowerId);
    if (borrower.isPresent()) {
      try {
        ExperianCCRISSearchRequest searchRequest = getExperianCCRISSearchRequest(borrower.get());
        CreditBureauInfo initCreditBureauInfo = getInitCreditBureauInfo(request, searchRequest);
        initCreditBureauInfo = creditBureauInfoJpaRepository.save(initCreditBureauInfo);
        LocalDateTime requestToCcrisInfoDttm = LocalDateTime.now();
        String ccrisSearchResponseString = getExperianSearchInfo(searchRequest, initCreditBureauInfo);
        if (StringUtils.isBlank(ccrisSearchResponseString)) {
          return response;
        }
        LocalDateTime responseFromCcrisInfoDttm = LocalDateTime.now();
        ExperianCCRISSearchResponse experianCCRISSearchResponse = getExperianCCRISSearchResponse(initCreditBureauInfo, ccrisSearchResponseString);
        experianCCRISSearchResponse.getCCrisIdentities().forEach(identityResponse -> {
          CreditBureauInfo info = getSearchInfo(request, searchRequest, requestToCcrisInfoDttm, ccrisSearchResponseString, responseFromCcrisInfoDttm, identityResponse);
          info = creditBureauInfoJpaRepository.save(info);
          ExperianCCRISEntityRequest confirmEntityRequest = getExperianCCRISEntityRequest(borrower.get(), identityResponse);
          LocalDateTime confirmRequestDttm = LocalDateTime.now();
          String confirmEntityResponseString = ccrisFeignClient.getCCRISInfo(confirmEntityRequest.toString());
          try {
            ExperianCCRISConfirmEntityResponse experianCCRISConfirmEntityResponse = mapper.readValue(confirmEntityResponseString,
                ExperianCCRISConfirmEntityResponse.class);
            setConfirmEntityData(info, confirmEntityRequest, confirmRequestDttm, confirmEntityResponseString, experianCCRISConfirmEntityResponse);
            info = creditBureauInfoJpaRepository.save(info);
            ExperianRetrieveReportRequest reportRequest = getExperianReportRequest(experianCCRISConfirmEntityResponse);
            Thread.sleep(experianRetrieveReportTimeoutBeforeRequest);
            ExperianRetrieveReportResponse reportResponse = ccrisFeignClient.getExperianReport(reportRequest);
            creditBureauInfoDetailsJpaRepository.save(getCreditBureauInfoDetails(reportResponse, info));
          } catch (Exception e) {
            log.error("Exception while getting experience report. application_id = " + request.getApplicationId() + " " + e.getMessage());
          }
        });
      } catch (JsonProcessingException e) {
        log.error("Exception while generating Experian JSON object. application_id = " + request.getApplicationId() + " " + e.getMessage());
      }
    }
    return response;
  }

  @NotNull
  private ExperianCCRISSearchResponse getExperianCCRISSearchResponse(CreditBureauInfo initCreditBureauInfo, String ccrisSearchResponseString)
      throws JsonProcessingException {
    ExperianCCRISSearchResponse experianCCRISSearchResponse = mapper.readValue(ccrisSearchResponseString, ExperianCCRISSearchResponse.class);
    if (!experianCCRISSearchResponse.getCCrisIdentities().isEmpty()) {
      creditBureauInfoJpaRepository.deleteById(initCreditBureauInfo.getId());
    }
    return experianCCRISSearchResponse;
  }

  private CreditBureauInfoDetails getCreditBureauInfoDetails(ExperianRetrieveReportResponse reportResponse, CreditBureauInfo info) {
    return new CreditBureauInfoDetails()
        .setCcrisResponseId(info.getId())
        .setMasterId(1L)
        .setDate(LocalDateTime.now())
        .setCapacity("2")
        .setLenderType(3L)
        .setLimit(10000L)
        .setLegalStatus("legalStatus")
        .setLegalStatusDate(LocalDateTime.now())
        .setMasterCollateralType("collateralType")
        .setFinancialGroupResidentStatus("finStatus")
        .setMasterCollateralTypeCode("masterCollateralTypeCode")
        .setStatus("status")
        .setRestructureRescheduleDate(LocalDateTime.now())
        .setFacility("facility")
        .setTotalOutstandingBalance(1000.0)
        .setTotalOutstandingBalanceBnm(1000.0)
        .setBalanceUpdatedDate(LocalDateTime.now())
        .setInstallmentAmount(10000L)
        .setPrincipleRepaymentTerm(10)
        .setSubAccountCollateralType("subAccountType")
        .setSubAccountCollateralTypeCode(32)
        .setCreditPosition(9);
  }

  private ExperianRetrieveReportRequest getExperianReportRequest(ExperianCCRISConfirmEntityResponse experianCCRISConfirmEntityResponse) {
    return new ExperianRetrieveReportRequest()
        .setRequest(new ExperianRetrieveReportRequestBody()
            .setToken1(experianCCRISConfirmEntityResponse.getToken1())
            .setToken2(experianCCRISConfirmEntityResponse.getToken2()));
  }

  private CreditBureauInfo getSearchInfo(RiskRequestJpaEntity request, ExperianCCRISSearchRequest searchRequest,
      LocalDateTime requestToCcrisInfoDttm, String ccrisSearchResponseString, LocalDateTime responseFromCcrisInfoDttm,
      ExperianCCRISIdentityResponse identityResponse) {
    return new CreditBureauInfo().setBureauId(1L)
        .setApplicationId(request.getApplicationId())
        .setCcrisSearchRequestDttm(requestToCcrisInfoDttm)
        .setCcrisSearchRequestJson(searchRequest.toString())
        .setCcrisSearchResponseDttm(responseFromCcrisInfoDttm)
        .setCcrisSearchResponseJson(ccrisSearchResponseString)
        .setCcrisSearchResponseStatus(ExperianCallStatus.SUCCESS)
        .setCcrisSearchCrefid(identityResponse.getCRefId())
        .setCcrisSearchEntitykey(identityResponse.getEntityKey())
        .setCcrisConfirmResponseStatus(ExperianCallStatus.UNKNOWN)
        .setCcrisReportResponseStatus(ExperianCallStatus.UNKNOWN);
  }

  private ExperianCCRISEntityRequest getExperianCCRISEntityRequest(Borrower borrower, ExperianCCRISIdentityResponse identityResponse) {
    return new ExperianCCRISEntityRequest()
        .setRequest(new ExperianCCRISEntityRequestBody()
            .setCRefId(identityResponse.getCRefId())
            .setConsentGranted("Y")
            .setEntityKey(identityResponse.getEntityKey())
            .setEmailAddress(borrower.getPersonalData().getEmail())
            .setMobileNo(borrower.getPersonalData().getMobilePhone())
            .setEnquiryPurpose("NEW APPLICATION")
            .setProductType(experianReviewProductType)
            .setLastKnownAddress(""));
  }

  private void setConfirmEntityData(CreditBureauInfo info, ExperianCCRISEntityRequest confirmEntityRequest, LocalDateTime confirmRequestDttm,
      String confirmEntityResponseString, ExperianCCRISConfirmEntityResponse experianCCRISConfirmEntityResponse) {
    info.setCcrisConfirmRequestDttm(confirmRequestDttm)
        .setCcrisConfirmRequestJson(confirmEntityRequest.toString())
        .setCcrisConfirmResponseStatus(ExperianCallStatus.SUCCESS)
        .setCcrisConfirmResponseDttm(LocalDateTime.now())
        .setCcrisConfirmResponseJson(confirmEntityResponseString)
        .setCcrisConfirmToken1(experianCCRISConfirmEntityResponse.getToken1())
        .setCcrisConfirmToken2(experianCCRISConfirmEntityResponse.getToken2());
  }

  private CreditBureauInfo getInitCreditBureauInfo(RiskRequestJpaEntity request, ExperianCCRISSearchRequest searchRequest) {
    return new CreditBureauInfo()
        .setBureauId(1L)
        .setApplicationId(request.getApplicationId())
        .setCcrisSearchRequestJson(searchRequest.toString())
        .setCcrisSearchResponseStatus(ExperianCallStatus.UNKNOWN);
  }

  private ExperianCCRISSearchRequest getExperianCCRISSearchRequest(Borrower borrower) {
    return new ExperianCCRISSearchRequest()
        .setRequest(new ExperianCCRISSearchRequestBody()
            .setCountry("MY")
            .setDOB(borrower.getPersonalData().getBirthDate())
            .setEntityId(borrower.getBorrowerNIC())
            .setEntityId2("")
            .setGroupCode(experianCCRISGroupCode)
            .setProductType(experianCCRISProductType));
  }

  private String getExperianSearchInfo(ExperianCCRISSearchRequest searchRequest, CreditBureauInfo creditBureauInfo) {
    String ccrisSearchResponseString = "";
    try {
      ccrisSearchResponseString = ccrisFeignClient.getCCRISInfo(searchRequest.toString());
    } catch (Exception e) {
      creditBureauInfo.setCcrisSearchResponseStatus(ExperianCallStatus.ERROR)
          .setCcrisConfirmErrorCode(e.getMessage());
      creditBureauInfoJpaRepository.save(creditBureauInfo);
    }
    return ccrisSearchResponseString;
  }
}