package asia.atmonline.myriskservice.services.seon.blackbox;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.SEON_BLACKBOX;

import asia.atmonline.myriskservice.data.entity.impl.requests.SeonBlackboxRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.SeonBlackboxResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.SeonBlackboxRequestJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.SeonBlackboxResponseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.SeonBlackboxRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.services.BaseChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeonBlackboxService extends BaseChecksService<SeonBlackboxRequest> {

  private final SeonBlackboxRequestJpaRepository seonBlackboxRequestJpaRepository;
  private final SeonBlackboxResponseJpaRepository seonBlackboxResponseJpaRepository;

  @Override
  public RiskResponse process(SeonBlackboxRequest request) {
    return null;
  }

  @Override
  public boolean accept(SeonBlackboxRequest request) {
    return request != null && SEON_BLACKBOX.equals(request.getCheck());
  }

  @Override
  public void saveRequest(SeonBlackboxRequest request) {
    seonBlackboxRequestJpaRepository.save(new SeonBlackboxRequestJpaEntity());
  }

  @Override
  public void saveResponse(RiskResponse riskResponse) {
    seonBlackboxResponseJpaRepository.save(new SeonBlackboxResponseJpaEntity());
  }
}
