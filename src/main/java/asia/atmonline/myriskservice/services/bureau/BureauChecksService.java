package asia.atmonline.myriskservice.services.bureau;

import asia.atmonline.myriskservice.data.entity.impl.requests.BureauRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.BureauResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BureauRequestJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.BureauResponseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.BureauRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.services.BaseChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BureauChecksService extends BaseChecksService<BureauRequest> {

  private final BureauRequestJpaRepository bureauRequestJpaRepository;
  private final BureauResponseJpaRepository bureauResponseJpaRepository;

  @Override
  public RiskResponse process(BureauRequest request) {
    return new RiskResponse();
  }

  @Override
  public void saveRequest(BureauRequest request) {
    bureauRequestJpaRepository.save(new BureauRequestJpaEntity());
  }

  @Override
  public void saveResponse(RiskResponse riskResponse) {
    bureauResponseJpaRepository.save(new BureauResponseJpaEntity());
  }
}