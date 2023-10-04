package asia.atmonline.myriskservice.web.bureau.experian.client;

import feign.Headers;
import feign.RequestLine;
import org.springframework.http.MediaType;

//@FeignClient(configuration = ExperianCCRISFeignClientConfiguration.class)
public interface ExperianCCRISFeignClient {

  @RequestLine("POST /report")
  @Headers({"Content-Type: " + MediaType.APPLICATION_JSON_VALUE})
  String getCCRISInfo(String request);

//  @RequestLine("POST /json")
//  @Headers({"Content-Type: " + MediaType.APPLICATION_JSON_VALUE})
//  ExperianRetrieveReportResponse getExperianReport(ExperianRetrieveReportRequest request);

  @RequestLine("POST /json")
  @Headers({"Content-Type: " + MediaType.APPLICATION_JSON_VALUE})
  String getExperianReport(String request);

}
