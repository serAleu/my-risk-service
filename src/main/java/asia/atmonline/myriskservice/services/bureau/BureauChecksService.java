package asia.atmonline.myriskservice.services.bureau;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import asia.atmonline.myriskservice.web.bureau.ccris.client.ExperianCCRISFeignClient;
import asia.atmonline.myriskservice.web.bureau.ccris.dto.confirm_entity.request.ExperianCCRISEntityRequest;
import asia.atmonline.myriskservice.web.bureau.ccris.dto.confirm_entity.response.ExperianCCRISEntityResponse;
import asia.atmonline.myriskservice.web.bureau.ccris.dto.search.request.ExperianCCRISSearchRequest;
import asia.atmonline.myriskservice.web.bureau.ccris.dto.search.response.ExperianCCRISSearchResponse;
import asia.atmonline.myriskservice.web.bureau.retrieve_report.client.ExperianRetrieveReportFeignClient;
import asia.atmonline.myriskservice.web.bureau.retrieve_report.dto.request.ExperianRetrieveReportRequest;
import asia.atmonline.myriskservice.web.bureau.retrieve_report.dto.response.ExperianRetrieveReportResponse;
import asia.atmonline.myriskservice.web.utils.XmlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BureauChecksService implements BaseRiskChecksService {

  private final ExperianCCRISFeignClient ccrisFeignClient;
  private final ExperianRetrieveReportFeignClient retrieveReportFeignClient;

  @Override
  public RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request) {

//    первый запрос в экспириан проверяем есть ли в бюро информация о заемщике

    ExperianCCRISSearchRequest searchRequest = new ExperianCCRISSearchRequest();
    String searchResponseString = ccrisFeignClient.getCCRISInfo(searchRequest.toString());
    ExperianCCRISSearchResponse searchResponse = XmlUtils.parse(searchResponseString, ExperianCCRISSearchResponse.class);

//    второй запрос в экспириан запускаем формирование отчета, в ответ получаю токены для запроса сформированного отчета

    ExperianCCRISEntityRequest entityRequest = new ExperianCCRISEntityRequest();
    String entityResponseString = ccrisFeignClient.getCCRISInfo(entityRequest.toString());
    ExperianCCRISEntityResponse entityResponse = XmlUtils.parse(entityResponseString, ExperianCCRISEntityResponse.class);

//    третий запрос в экспириан передаем полученные во 2ом запросе токены для получения сформированного отчета

    ExperianRetrieveReportRequest reportRequest = new ExperianRetrieveReportRequest(entityResponse.getToken1(), entityResponse.getToken2());
    String reportResponseString = retrieveReportFeignClient.getExperianReport(reportRequest.toString());
    ExperianRetrieveReportResponse reportResponse = XmlUtils.parse(reportResponseString, ExperianRetrieveReportResponse.class);

    return new RiskResponseRiskJpaEntity();
  }
}