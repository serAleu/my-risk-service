package asia.atmonline.myriskservice.rules.score.call3err;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.score.ScoreSqsProducer;
import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class ScoreCall3ErrRule extends BaseScoreRule<ScoreCall3ErrContext> {

  public ScoreCall3ErrRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<ScoreSqsProducer> execute(ScoreCall3ErrContext context) {
    RiskResponseJpaEntity<ScoreSqsProducer> response = super.execute(context);
    return response;
  }

  @Override
  public ScoreCall3ErrContext getContext(Integer decision, Long limit, Integer term) {
    return null;
  }
}
