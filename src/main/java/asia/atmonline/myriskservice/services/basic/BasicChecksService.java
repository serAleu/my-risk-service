package asia.atmonline.myriskservice.services.basic;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.BASIC;

import asia.atmonline.myriskservice.data.entity.impl.requests.BasicRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.BasicResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BasicRequestJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.BasicResponseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.BasicRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.services.BaseChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicChecksService extends BaseChecksService<BasicRequest> {

  private final BasicRequestJpaRepository basicRequestJpaRepository;
  private final BasicResponseJpaRepository basicResponseJpaRepository;

  @Override
  public RiskResponse process(BasicRequest request) {
    return new RiskResponse();
  }

  @Override
  public boolean accept(BasicRequest request) {
    return request != null && BASIC.equals(request.getCheck());
  }

  @Override
  public void saveRequest(BasicRequest request) {
    basicRequestJpaRepository.save(new BasicRequestJpaEntity());
  }

  @Override
  public void saveResponse(RiskResponse riskResponse) {
    basicResponseJpaRepository.save(new BasicResponseJpaEntity());
  }
}