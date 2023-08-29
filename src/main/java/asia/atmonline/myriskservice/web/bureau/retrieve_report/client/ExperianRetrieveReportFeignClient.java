package asia.atmonline.myriskservice.web.bureau.retrieve_report.client;

import feign.Headers;
import feign.RequestLine;
import org.springframework.http.MediaType;

public interface ExperianRetrieveReportFeignClient {

  @RequestLine("POST /report")
  @Headers({
      "Content-Type: " + MediaType.APPLICATION_XML_VALUE,
      "Authorization: {authorization}"
  })
  String getExperianReport(String request);

}
