package asia.atmonline.myriskservice.services.bureau;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BureauChecksService implements BaseRiskChecksService {

//  private final ExperianCCRISFeignClient ccrisFeignClient;
//  private final ExperianRetrieveReportFeignClient retrieveReportFeignClient;

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
//    response.setRequestId(request.getId());
//    response.setApplicationId(request.getApplicationId());

//    первый запрос в экспириан проверяем есть ли в бюро информация о заемщике

//    ExperianCCRISSearchRequest searchRequest = new ExperianCCRISSearchRequest();
//    String searchResponseString = ccrisFeignClient.getCCRISInfo(searchRequest.toString());
//    ExperianCCRISSearchResponse searchResponse = XmlUtils.parse(searchResponseString, ExperianCCRISSearchResponse.class);

//    второй запрос в экспириан запускаем формирование отчета, в ответ получаю токены для запроса сформированного отчета

//    ExperianCCRISEntityRequest entityRequest = new ExperianCCRISEntityRequest();
//    String entityResponseString = ccrisFeignClient.getCCRISInfo(entityRequest.toString());
//    ExperianCCRISEntityResponse entityResponse = XmlUtils.parse(entityResponseString, ExperianCCRISEntityResponse.class);

//    третий запрос в экспириан передаем полученные во 2ом запросе токены для получения сформированного отчета

//    ExperianRetrieveReportRequest reportRequest = new ExperianRetrieveReportRequest(entityResponse.getToken1(), entityResponse.getToken2());
//    String reportResponseString = retrieveReportFeignClient.getExperianReport(reportRequest.toString());
//    ExperianRetrieveReportResponse reportResponse = XmlUtils.parse(reportResponseString, ExperianRetrieveReportResponse.class);

    return new RiskResponseJpaEntity();
  }
}