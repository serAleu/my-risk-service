package asia.atmonline.myriskservice.services.dedup;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.DEDUP2;

import asia.atmonline.myriskservice.data.entity.impl.responses.Dedup2ResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.Dedup2RequestJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.Dedup2ResponseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.Dedup2Request;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.services.BaseChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Dedup2ChecksService extends BaseChecksService<Dedup2Request> {

  private final Dedup2RequestJpaRepository dedup2RequestJpaRepository;
  private final Dedup2ResponseJpaRepository dedup2ResponseJpaRepository;

  @Override
  public RiskResponse process(Dedup2Request request) {
    return new RiskResponse();
  }

  @Override
  public boolean accept(Dedup2Request request) {
    return request != null && DEDUP2.equals(request.getCheck());
  }

  @Override
  public void saveRequest(Dedup2Request request) {
    dedup2RequestJpaRepository.save(new Dedup2ResponseJpaEntity());
  }

  @Override
  public void saveResponse(RiskResponse riskResponse) {
    dedup2ResponseJpaRepository.save(new Dedup2ResponseJpaEntity());
  }
}
