package asia.atmonline.myriskservice.services.bureau;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BUREAU;
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
import java.time.LocalDate;
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

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = new RiskResponseJpaEntity();
    response.setRequestId(request.getId());
    response.setApplicationId(request.getApplicationId());
    response.setCheckType(BUREAU);
    response.setDecision(APPROVE);
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
          CreditBureauInfo info = getSearchInfo(request, searchRequest, requestToCcrisInfoDttm, ccrisSearchResponseString, responseFromCcrisInfoDttm,
              identityResponse);
          info = creditBureauInfoJpaRepository.save(info);
          ExperianCCRISEntityRequest confirmEntityRequest = getExperianCCRISEntityRequest(borrower.get(), identityResponse);
          LocalDateTime confirmRequestDttm = LocalDateTime.now();
          String confirmEntityResponseString = ccrisFeignClient.getCCRISInfo(confirmEntityRequest.toString());
//          добавить условие, что если респонс без токенов, то значит в бюро нет информации
          try {
            ExperianCCRISConfirmEntityResponse experianCCRISConfirmEntityResponse = mapper.readValue(confirmEntityResponseString,
                ExperianCCRISConfirmEntityResponse.class);
            setConfirmEntityData(info, confirmEntityRequest, confirmRequestDttm, confirmEntityResponseString, experianCCRISConfirmEntityResponse);
            info = creditBureauInfoJpaRepository.save(info);
            ExperianRetrieveReportRequest reportRequest = getExperianReportRequest(experianCCRISConfirmEntityResponse);
//            Thread.sleep(experianRetrieveReportTimeoutBeforeRequest);
            LocalDateTime reportRequestDtm = LocalDateTime.now();
            String reportResponseString = ccrisFeignClient.getExperianReport(reportRequest.toString());
            saveReportInfo(info, reportRequest, reportRequestDtm, reportResponseString);
            ExperianRetrieveReportResponse reportResponse = mapper.readValue(reportResponseString, ExperianRetrieveReportResponse.class);
            saveCreditBureauInfoDetails(reportResponse, info);
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

  private void saveReportInfo(CreditBureauInfo info, ExperianRetrieveReportRequest reportRequest, LocalDateTime reportRequestDtm,
      String reportResponseString) {
    info.setCcrisReportRequestDttm(reportRequestDtm)
        .setCcrisReportRequestJson(reportRequest.toString())
        .setCcrisReportResponseStatus(SUCCESS)
        .setCcrisReportResponseJson(reportResponseString)
        .setCcrisReportResponseDttm(LocalDateTime.now());
    creditBureauInfoJpaRepository.save(info);
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

  private void saveCreditBureauInfoDetails(ExperianRetrieveReportResponse reportResponse, CreditBureauInfo info) {
    reportResponse.getBankingInfo().getCcrisBankingDetails().getOutstandingCredit()
        .forEach(outstandingCredit -> outstandingCredit.getMaster().forEach(master -> {
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
              .setCreditPosition(LocalDate.now().getMonth().getValue());
          creditBureauInfoDetailsJpaRepository.save(creditBureauInfoDetails);
        }));
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
        .setCcrisConfirmResponseStatus(SUCCESS)
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