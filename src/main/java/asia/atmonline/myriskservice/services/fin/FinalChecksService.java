package asia.atmonline.myriskservice.services.fin;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.FINAL;

import asia.atmonline.myriskservice.data.entity.impl.requests.FinalRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.FinalResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.FinalRequestJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.FinalResponseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.FinalRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.services.BaseChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinalChecksService extends BaseChecksService<FinalRequest> {

  private final FinalRequestJpaRepository finalRequestJpaRepository;
  private final FinalResponseJpaRepository finalResponseJpaRepository;

  @Override
  public RiskResponse process(FinalRequest request) {
    return new RiskResponse();
  }

  @Override
  public boolean accept(FinalRequest request) {
    return request != null && FINAL.equals(request.getCheck());
  }

  @Override
  public void saveRequest(FinalRequest request) {
    finalRequestJpaRepository.save(new FinalRequestJpaEntity());
  }

  @Override
  public void saveResponse(RiskResponse riskResponse) {
    finalResponseJpaRepository.save(new FinalResponseJpaEntity());
  }
}
