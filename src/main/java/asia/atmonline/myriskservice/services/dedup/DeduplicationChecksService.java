package asia.atmonline.myriskservice.services.dedup;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.DEDUP;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.DedupRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.DeduplicationRequest;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationContext;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.BaseChecksService;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DeduplicationChecksService extends BaseChecksService<DeduplicationRequest, DedupRequestJpaEntity> {

  private final List<? extends BaseDeduplicationRule<? extends BaseDeduplicationContext>> rules;
  private final BlacklistChecksService blacklistChecksService;

  public DeduplicationChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories,
      List<? extends BaseDeduplicationRule<? extends BaseDeduplicationContext>> rules, BlacklistChecksService blacklistChecksService) {
    super(repositories);
    this.rules = rules;
    this.blacklistChecksService = blacklistChecksService;
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseJpaEntity<DeduplicationSqsProducer> process(DeduplicationRequest request) {
    Long borrowerId = request.getBorrowerId();
    RiskResponseJpaEntity<DeduplicationSqsProducer> response = new RiskResponseJpaEntity<>();
    boolean isBankAccountMatchedWithBlAccount = blacklistChecksService.checkBankAccountBlacklist(request.getBankAccount());
    boolean isPassportNumMatchedWithBlIdNumber = blacklistChecksService.checkPassportNumberBlacklist(request.getPassportNumber());
    for(BaseDeduplicationRule rule : rules) {
      response = rule.execute(rule.getContext(isBankAccountMatchedWithBlAccount, isPassportNumMatchedWithBlIdNumber));
      if (response != null && REJECT.equals(response.getDecision())) {
        if(response.getRejectionReasonCode() != null) {
          rule.saveToBlacklists(borrowerId, response.getRejectionReasonCode());
        }
        return response;
      }
    }
    return response;
  }

  @Override
  public boolean accept(DeduplicationRequest request) {
    return request != null && DEDUP.equals(request.getCheck()) && request.getBorrowerId() != null;
  }

  @Override
  public DedupRequestJpaEntity getRequestEntity(DeduplicationRequest request) {
    return new DedupRequestJpaEntity().setBorrowerId(request.getBorrowerId())
        .setBankAccount(request.getBankAccount())
        .setPassportNumber(request.getPassportNumber())
        .setIsEmailConfirmed(request.getIsEmailConfirmed());
  }
}