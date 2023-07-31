package asia.atmonline.myriskservice.services.dedup;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.DEDUP3;

import asia.atmonline.myriskservice.data.entity.impl.requests.Dedup3RequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.Dedup3ResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.Dedup3RequestJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.Dedup3ResponseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.Dedup3Request;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.services.BaseChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Dedup3ChecksService extends BaseChecksService<Dedup3Request> {

  private final Dedup3RequestJpaRepository dedup3RequestJpaRepository;
  private final Dedup3ResponseJpaRepository dedup3ResponseJpaRepository;

  @Override
  public RiskResponse process(Dedup3Request request) {
    return new RiskResponse();
  }

  @Override
  public boolean accept(Dedup3Request request) {
    return request != null && DEDUP3.equals(request.getCheck());
  }

  @Override
  public void saveRequest(Dedup3Request request) {
    dedup3RequestJpaRepository.save(new Dedup3RequestJpaEntity());
  }

  @Override
  public void saveResponse(RiskResponse riskResponse) {
    dedup3ResponseJpaRepository.save(new Dedup3ResponseJpaEntity());
  }
}