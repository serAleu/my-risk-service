package asia.atmonline.myriskservice.services.blacklists;

import asia.atmonline.myriskservice.data.entity.impl.requests.BlacklistRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.BlacklistResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BlacklistRequestJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.BlacklistResponseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.BlacklistsRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.services.BaseChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlacklistChecksService extends BaseChecksService<BlacklistsRequest> {

  private final BlacklistRequestJpaRepository blacklistRequestJpaRepository;
  private final BlacklistResponseJpaRepository blacklistResponseJpaRepository;

  @Override
  public RiskResponse process(BlacklistsRequest request) {
    return new RiskResponse();
  }

  @Override
  public void saveRequest(BlacklistsRequest request) {
    blacklistRequestJpaRepository.save(new BlacklistRequestJpaEntity());
  }

  @Override
  public void saveResponse(RiskResponse riskResponse) {
    blacklistResponseJpaRepository.save(new BlacklistResponseJpaEntity());
  }
}