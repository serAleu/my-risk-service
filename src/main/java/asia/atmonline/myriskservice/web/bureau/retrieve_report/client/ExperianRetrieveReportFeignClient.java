package asia.atmonline.myriskservice.web.bureau.retrieve_report.client;

import asia.atmonline.myriskservice.web.bureau.retrieve_report.dto.request.ExperianRetrieveReportRequest;
import asia.atmonline.myriskservice.web.bureau.retrieve_report.dto.response.ExperianRetrieveReportResponse;
import feign.Headers;
import feign.RequestLine;
import org.springframework.http.MediaType;

public interface ExperianRetrieveReportFeignClient {

  @RequestLine("POST /json")
  @Headers({
      "Content-Type: " + MediaType.APPLICATION_JSON_VALUE,
      "Authorization: {authorization}"
  })
  ExperianRetrieveReportResponse getExperianReport(ExperianRetrieveReportRequest request);

}
