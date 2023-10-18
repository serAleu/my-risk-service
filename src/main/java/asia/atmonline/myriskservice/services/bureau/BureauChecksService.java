package asia.atmonline.myriskservice.services.bureau;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BUREAU;
import static asia.atmonline.myriskservice.enums.risk.ExperianCallStatus.ERROR;
import static asia.atmonline.myriskservice.enums.risk.ExperianCallStatus.SUCCESS;
import static asia.atmonline.myriskservice.enums.risk.FinalDecision.APPROVE;

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
import asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.request.ExperianRetrieveReportRequest;
import asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.request.ExperianRetrieveReportRequestBody;
import asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.response.ExperianRetrieveReportResponse;
import asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.response.SubAccount;
import asia.atmonline.myriskservice.web.bureau.experian.dto.search.request.ExperianCCRISSearchRequest;
import asia.atmonline.myriskservice.web.bureau.experian.dto.search.request.ExperianCCRISSearchRequestBody;
import asia.atmonline.myriskservice.web.bureau.experian.dto.search.response.ExperianCCRISIdentityResponse;
import asia.atmonline.myriskservice.web.bureau.experian.dto.search.response.ExperianCCRISSearchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

  public static final String ENQUIRY_PURPOSE = "NEW APPLICATION";
  public static final String CONSENT_GRANTED = "Y";
  public static final String EXPERIAN_REQUESTED_COUNTRY = "MY";
  public static final Long EXPERIAN_BUREAU_ID = 1L;

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = getApprovedRiskResponseJpaEntity(request);
    Long borrowerId = creditApplicationJpaRepository.findBorrowerIdById(request.getApplicationId());
    Optional<Borrower> borrower = borrowerJpaRepository.findById(borrowerId);
    if (borrower.isPresent() && borrower.get().getName() != null && borrower.get().getBorrowerNIC() != null
        && borrower.get().getPersonalData() != null && borrower.get().getPersonalData().getBirthDate() != null) {
      ExperianCCRISSearchRequest searchRequest = getExperianCCRISSearchRequest(borrower.get());
      CreditBureauInfo initCreditBureauInfo = getInitCreditBureauInfo(request, searchRequest);
      initCreditBureauInfo = creditBureauInfoJpaRepository.save(initCreditBureauInfo);
      LocalDateTime requestToCcrisInfoDttm = LocalDateTime.now();
      String ccrisSearchResponseStr = getExperianSearchInfo(searchRequest, initCreditBureauInfo);
      if (StringUtils.isBlank(ccrisSearchResponseStr)) {
        return response;
      }
      LocalDateTime responseFromCcrisInfoDttm = LocalDateTime.now();
      ExperianCCRISSearchResponse experianCCRISSearchResponse = getExperianCCRISSearchResponse(request.getApplicationId(), initCreditBureauInfo,
          ccrisSearchResponseStr);
      experianCCRISSearchResponse.getCCrisIdentities().forEach(identityResponse -> {
        CreditBureauInfo info = getSearchInfo(request, searchRequest, requestToCcrisInfoDttm, ccrisSearchResponseStr, responseFromCcrisInfoDttm,
            identityResponse);
        info = creditBureauInfoJpaRepository.save(info);
        ExperianCCRISEntityRequest confirmEntityRequest = getExperianCCRISEntityRequest(borrower.get(), identityResponse);
        ExperianCCRISConfirmEntityResponse experianCCRISConfirmEntityResponse = getExperianCCRISConfirmEntityResponse(request.getApplicationId(),
            info, confirmEntityRequest);
        info = creditBureauInfoJpaRepository.save(info);
        if (SUCCESS.equals(info.getCcrisConfirmResponseStatus())) {
          ExperianRetrieveReportRequest reportRequest = getExperianReportRequest(experianCCRISConfirmEntityResponse);
          ExperianRetrieveReportResponse reportResponse = getAndSaveExperianReport(request.getApplicationId(), reportRequest.toString(), info,
              reportRequest);
          saveExperianScoreAndCreditBureauInfoDetails(reportResponse, info);
        }
      });
    }
    return response;
  }

  private ExperianCCRISConfirmEntityResponse getExperianCCRISConfirmEntityResponse(Long applicationId, CreditBureauInfo info,
      ExperianCCRISEntityRequest confirmEntityRequest) {
    ExperianCCRISConfirmEntityResponse experianCCRISConfirmEntityResponse = new ExperianCCRISConfirmEntityResponse();
    LocalDateTime confirmRequestDttm = LocalDateTime.now();
    String confirmEntityResponseString = "";
    try {
      confirmEntityResponseString = ccrisFeignClient.getCCRISInfo(confirmEntityRequest.toString());
      experianCCRISConfirmEntityResponse = mapper.readValue(confirmEntityResponseString, ExperianCCRISConfirmEntityResponse.class);
    } catch (Exception e) {
      log.error("Exception while getting experience report. application_id = " + applicationId + " " + e.getMessage());
    }
    setConfirmEntityData(info, confirmEntityRequest, confirmRequestDttm, confirmEntityResponseString, experianCCRISConfirmEntityResponse);
    return experianCCRISConfirmEntityResponse;
  }

  private ExperianRetrieveReportResponse getAndSaveExperianReport(Long applicationId, String request, CreditBureauInfo info,
      ExperianRetrieveReportRequest reportRequest) {
    ExperianRetrieveReportResponse reportResponse = new ExperianRetrieveReportResponse();
    String reportResponseString = "";
    LocalDateTime reportRequestDtm = LocalDateTime.now();
    try {
      Thread.sleep(experianRetrieveReportTimeoutBeforeRequest);
      reportRequestDtm = LocalDateTime.now();
      reportResponseString = ccrisFeignClient.getExperianReport(request);
      reportResponse = mapper.readValue(reportResponseString, ExperianRetrieveReportResponse.class);
    } catch (Exception e) {
      info.setCcrisReportResponseStatus(ERROR);
      log.error("Exception while getting experience report. application_id = " + applicationId + " " + e.getMessage());
    }
    saveReportInfo(info, reportRequest, reportRequestDtm, reportResponseString);
    return reportResponse;
  }

  @NotNull
  private RiskResponseJpaEntity getApprovedRiskResponseJpaEntity(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = new RiskResponseJpaEntity();
    response.setRequestId(request.getId());
    response.setApplicationId(request.getApplicationId());
    response.setCheckType(BUREAU);
    response.setDecision(APPROVE);
    return response;
  }

  private void saveReportInfo(CreditBureauInfo info, ExperianRetrieveReportRequest reportRequest, LocalDateTime reportRequestDtm,
      String reportResponseString) {
    info.setCcrisReportRequestDttm(reportRequestDtm)
        .setCcrisReportRequestJson(reportRequest.toString())
        .setCcrisReportResponseStatus(ERROR.equals(info.getCcrisReportResponseStatus()) ? ERROR : SUCCESS)
        .setCcrisReportResponseJson(reportResponseString)
        .setCcrisReportResponseDttm(LocalDateTime.now());
    creditBureauInfoJpaRepository.save(info);
  }

  @NotNull
  private ExperianCCRISSearchResponse getExperianCCRISSearchResponse(Long applicationId, CreditBureauInfo initCreditBureauInfo,
      String ccrisSearchResponseString) {
    try {
      ExperianCCRISSearchResponse experianCCRISSearchResponse = mapper.readValue(ccrisSearchResponseString, ExperianCCRISSearchResponse.class);
      if (!experianCCRISSearchResponse.getCCrisIdentities().isEmpty()) {
        creditBureauInfoJpaRepository.deleteById(initCreditBureauInfo.getId());
      }
      return experianCCRISSearchResponse;
    } catch (JsonProcessingException e) {
      log.error("Exception while generating Experian JSON object. application_id = " + applicationId + " " + e.getMessage());
      return new ExperianCCRISSearchResponse();
    }
  }

  private void saveExperianScoreAndCreditBureauInfoDetails(ExperianRetrieveReportResponse reportResponse, CreditBureauInfo info) {
    if (reportResponse.getSummary() != null && reportResponse.getSummary().getIScore() != null) {
      info.setScore(reportResponse.getSummary().getIScore().getIScore())
          .setScoreGrade(reportResponse.getSummary().getIScore().getRiskGrade());
    }
    creditBureauInfoJpaRepository.save(info);
    if (reportResponse.getBankingInfo() != null && reportResponse.getBankingInfo().getCcrisBankingDetails() != null
        && reportResponse.getBankingInfo().getCcrisBankingDetails().getOutstandingCredit() != null) {
      reportResponse.getBankingInfo().getCcrisBankingDetails().getOutstandingCredit()
          .forEach(outstandingCredit -> outstandingCredit.getMaster().forEach(master -> {
            String concatenatedCreditPositions = getConcatSubAccountCreditPositions(outstandingCredit.getSubAccount());
            String m0 = null;
            String replacedConcatenatedCreditPositions = concatenatedCreditPositions != null ? concatenatedCreditPositions.replaceAll("[^0-9]","") : null;
            if(replacedConcatenatedCreditPositions != null && replacedConcatenatedCreditPositions.length() == 12) {
              m0 = replacedConcatenatedCreditPositions.substring(0, 1);
              replacedConcatenatedCreditPositions = replacedConcatenatedCreditPositions.substring(1);
            }
            CreditBureauInfoDetails creditBureauInfoDetails = new CreditBureauInfoDetails()
                .setCcrisResponseId(info.getId())
                .setMasterId(master.getMasterId())
                .setDate(master.getDate())
                .setCapacity(master.getCapacity())
                .setLenderType(master.getLenderType())
                .setCreditLimit(Long.parseLong(master.getLimit()))
                .setLegalStatus(master.getLegalStatus())
                .setLegalStatusDate(master.getLegalStatusDate())
                .setMasterCollateralType(master.getCollateralType())
                .setFinancialGroupResidentStatus(master.getFinancialGroupResidentStatus())
                .setMasterCollateralTypeCode(master.getCollateralTypeCode())
                .setStatus(getConcatStatuses(outstandingCredit.getSubAccount()))
                .setRestructureRescheduleDate(getConcatRestructureRescheduleDates(outstandingCredit.getSubAccount()))
                .setFacility(getConcatFacilities(outstandingCredit.getSubAccount()))
                .setTotalOutstandingBalance(getConcatTotalOutstandingBalances(outstandingCredit.getSubAccount()))
                .setTotalOutstandingBalanceBnm(getConcatTotalOutstandingBalancesBnm(outstandingCredit.getSubAccount()))
                .setBalanceUpdatedDate(getConcatTotalBalanceUpdatedDates(outstandingCredit.getSubAccount()))
                .setInstallmentAmount(getConcatInstallmentAmounts(outstandingCredit.getSubAccount()))
                .setPrincipleRepaymentTerm(getConcatPrincipleRepaymentTerms(outstandingCredit.getSubAccount()))
                .setSubAccountCollateralType(getConcatSubAccountCollateralTypes(outstandingCredit.getSubAccount()))
                .setSubAccountCollateralTypeCode(getConcatSubAccountCollateralTypeCodes(outstandingCredit.getSubAccount()))
                .setCreditPosition(concatenatedCreditPositions)
                .setM0(getCreditPositionInt(m0, 0))
                .setM1(getCreditPositionInt(replacedConcatenatedCreditPositions, 0))
                .setM2(getCreditPositionInt(replacedConcatenatedCreditPositions, 1))
                .setM3(getCreditPositionInt(replacedConcatenatedCreditPositions, 2))
                .setM4(getCreditPositionInt(replacedConcatenatedCreditPositions, 3))
                .setM5(getCreditPositionInt(replacedConcatenatedCreditPositions, 4))
                .setM6(getCreditPositionInt(replacedConcatenatedCreditPositions, 5))
                .setM7(getCreditPositionInt(replacedConcatenatedCreditPositions, 6))
                .setM8(getCreditPositionInt(replacedConcatenatedCreditPositions, 7))
                .setM9(getCreditPositionInt(replacedConcatenatedCreditPositions, 8))
                .setM10(getCreditPositionInt(replacedConcatenatedCreditPositions, 9))
                .setM11(getCreditPositionInt(replacedConcatenatedCreditPositions, 10));
            creditBureauInfoDetailsJpaRepository.save(creditBureauInfoDetails);
          }));
    }
  }

  private Integer getCreditPositionInt(String str, Integer num) {
    if(StringUtils.isBlank(str)) return null;
    str = Character.toString(str.charAt(num));
    try {
      return Integer.parseInt(str);
    } catch (Exception e) {
      return -1;
    }
  }

  private String getConcatSubAccountCreditPositions(List<List<SubAccount>> subAccount) {
    List<String> list = new ArrayList<>();
    subAccount.forEach(subAccounts -> subAccounts.forEach(sa -> list.addAll(sa.getCreditPosition())));
    return list.toString();
  }

  private String getConcatSubAccountCollateralTypeCodes(List<List<SubAccount>> subAccount) {
    List<String> list = new ArrayList<>();
    subAccount.forEach(subAccounts -> subAccounts.forEach(sa -> list.add(sa.getCollateralTypeCode())));
    return list.toString();
  }

  private String getConcatSubAccountCollateralTypes(List<List<SubAccount>> subAccount) {
    List<String> list = new ArrayList<>();
    subAccount.forEach(subAccounts -> subAccounts.forEach(sa -> list.add(sa.getCollateralType())));
    return list.toString();
  }

  private String getConcatPrincipleRepaymentTerms(List<List<SubAccount>> subAccount) {
    List<String> list = new ArrayList<>();
    subAccount.forEach(subAccounts -> subAccounts.forEach(sa -> list.add(sa.getPrincipleRepaymentTerm())));
    return list.toString();
  }

  private String getConcatInstallmentAmounts(List<List<SubAccount>> subAccount) {
    List<String> list = new ArrayList<>();
    subAccount.forEach(subAccounts -> subAccounts.forEach(sa -> list.add(sa.getInstallmentAmount())));
    return list.toString();
  }

  private String getConcatTotalBalanceUpdatedDates(List<List<SubAccount>> subAccount) {
    List<String> list = new ArrayList<>();
    subAccount.forEach(subAccounts -> subAccounts.forEach(sa -> list.add(sa.getBalanceUpdatedDate())));
    return list.toString();
  }

  private String getConcatTotalOutstandingBalancesBnm(List<List<SubAccount>> subAccount) {
    List<String> list = new ArrayList<>();
    subAccount.forEach(subAccounts -> subAccounts.forEach(sa -> list.add(sa.getTotalOutstandingBalanceBnm())));
    return list.toString();
  }

  private String getConcatTotalOutstandingBalances(List<List<SubAccount>> subAccount) {
    List<String> list = new ArrayList<>();
    subAccount.forEach(subAccounts -> subAccounts.forEach(sa -> list.add(sa.getTotalOutstandingBalance())));
    return list.toString();
  }

  private String getConcatStatuses(List<List<SubAccount>> subAccount) {
    List<String> list = new ArrayList<>();
    subAccount.forEach(subAccounts -> subAccounts.forEach(sa -> list.add(sa.getStatus())));
    return list.toString();
  }

  private String getConcatRestructureRescheduleDates(List<List<SubAccount>> subAccount) {
    List<String> list = new ArrayList<>();
    subAccount.forEach(subAccounts -> subAccounts.forEach(sa -> list.add(sa.getRestructureRescheduleDate())));
    return list.toString();
  }

  private String getConcatFacilities(List<List<SubAccount>> subAccount) {
    List<String> list = new ArrayList<>();
    subAccount.forEach(subAccounts -> subAccounts.forEach(sa -> list.add(sa.getFacility())));
    return list.toString();
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
        .setCcrisSearchResponseStatus(SUCCESS)
        .setCcrisSearchCrefid(identityResponse.getCRefId())
        .setCcrisSearchEntitykey(identityResponse.getEntityKey())
        .setCcrisConfirmResponseStatus(ExperianCallStatus.UNKNOWN)
        .setCcrisReportResponseStatus(ExperianCallStatus.UNKNOWN);
  }

  private ExperianCCRISEntityRequest getExperianCCRISEntityRequest(Borrower borrower, ExperianCCRISIdentityResponse identityResponse) {
    return new ExperianCCRISEntityRequest()
        .setRequest(new ExperianCCRISEntityRequestBody()
            .setCRefId(identityResponse.getCRefId())
            .setConsentGranted(CONSENT_GRANTED)
            .setEntityKey(identityResponse.getEntityKey())
            .setEmailAddress(borrower.getPersonalData().getEmail())
            .setMobileNo(borrower.getPersonalData().getMobilePhone())
            .setEnquiryPurpose(ENQUIRY_PURPOSE)
            .setProductType(experianReviewProductType)
            .setLastKnownAddress(""));
  }

  private void setConfirmEntityData(CreditBureauInfo info, ExperianCCRISEntityRequest confirmEntityRequest, LocalDateTime confirmRequestDttm,
      String confirmEntityResponseString, ExperianCCRISConfirmEntityResponse experianCCRISConfirmEntityResponse) {
    info.setCcrisConfirmRequestDttm(confirmRequestDttm)
        .setCcrisConfirmRequestJson(confirmEntityRequest.toString())
        .setCcrisConfirmResponseStatus(
            isCcrisConfirmResponseStatusSuccess(confirmEntityResponseString, experianCCRISConfirmEntityResponse) ? SUCCESS : ERROR)
        .setCcrisConfirmResponseDttm(LocalDateTime.now())
        .setCcrisConfirmResponseJson(confirmEntityResponseString)
        .setCcrisConfirmToken1(experianCCRISConfirmEntityResponse.getToken1() != null ? experianCCRISConfirmEntityResponse.getToken1() : null)
        .setCcrisConfirmToken2(experianCCRISConfirmEntityResponse.getToken2() != null ? experianCCRISConfirmEntityResponse.getToken2() : null);
    if(ERROR.equals(info.getCcrisConfirmResponseStatus())) {
      info.setCcrisConfirmErrorCode("NO INFO IN THE BUREAU");
    }
  }

  private boolean isCcrisConfirmResponseStatusSuccess(String confirmEntityResponseString,
      ExperianCCRISConfirmEntityResponse experianCCRISConfirmEntityResponse) {
    return !StringUtils.isBlank(confirmEntityResponseString) && experianCCRISConfirmEntityResponse.getToken1() != null
        && experianCCRISConfirmEntityResponse.getToken2() != null;
  }

  private CreditBureauInfo getInitCreditBureauInfo(RiskRequestJpaEntity request, ExperianCCRISSearchRequest searchRequest) {
    return new CreditBureauInfo()
        .setBureauId(EXPERIAN_BUREAU_ID)
        .setApplicationId(request.getApplicationId())
        .setCcrisSearchRequestJson(searchRequest.toString())
        .setCcrisSearchResponseStatus(ExperianCallStatus.UNKNOWN);
  }

  private ExperianCCRISSearchRequest getExperianCCRISSearchRequest(Borrower borrower) {
    return new ExperianCCRISSearchRequest()
        .setRequest(new ExperianCCRISSearchRequestBody()
            .setCountry(EXPERIAN_REQUESTED_COUNTRY)
//            .setEntityName(borrower.getName())
            .setEntityName("COURTS NAME IRISSU 1")
            .setDOB(borrower.getPersonalData().getBirthDate())
//            .setEntityId(borrower.getBorrowerNIC())
            .setEntityId("740424125653")
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