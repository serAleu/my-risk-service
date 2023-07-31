package asia.atmonline.myriskservice.services.seon.data;

import asia.atmonline.myriskservice.data.entity.impl.requests.SeonDataRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.SeonDataResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.SeonDataRequestJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.SeonDataResponseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.SeonDataRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.services.BaseChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeonDataService extends BaseChecksService<SeonDataRequest> {

  private final SeonDataRequestJpaRepository seonDataRequestJpaRepository;
  private final SeonDataResponseJpaRepository seonDataResponseJpaRepository;

  @Override
  public RiskResponse process(SeonDataRequest request) {
    return new RiskResponse();
  }

  @Override
  public void saveRequest(SeonDataRequest request) {
    seonDataRequestJpaRepository.save(new SeonDataRequestJpaEntity());
  }

  @Override
  public void saveResponse(RiskResponse riskResponse) {
    seonDataResponseJpaRepository.save(new SeonDataResponseJpaEntity());
  }
}
