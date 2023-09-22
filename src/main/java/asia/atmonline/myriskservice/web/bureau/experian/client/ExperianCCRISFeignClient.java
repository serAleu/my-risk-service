package asia.atmonline.myriskservice.web.bureau.experian.client;

import asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.request.ExperianRetrieveReportRequest;
import asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.response.ExperianRetrieveReportResponse;
import feign.Headers;
import feign.RequestLine;
import org.springframework.http.MediaType;

public interface ExperianCCRISFeignClient {

  @RequestLine("POST /report")
  @Headers({
      "Content-Type: " + MediaType.APPLICATION_JSON_VALUE
//      , "Authorization: {authorization}"
  })
  String getCCRISInfo(String request);

  @RequestLine("POST /json")
  @Headers({
      "Content-Type: " + MediaType.APPLICATION_JSON_VALUE
//      , "Authorization: {authorization}"
  })
  ExperianRetrieveReportResponse getExperianReport(ExperianRetrieveReportRequest request);

}
