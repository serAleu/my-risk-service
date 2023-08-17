package asia.atmonline.myriskservice.rules.score.call3;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.score.ScoreSqsProducer;
import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class ScoreCall3Rule extends BaseScoreRule<ScoreCall3Context> {

  public ScoreCall3Rule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<ScoreSqsProducer> execute(ScoreCall3Context context) {
    RiskResponseJpaEntity<ScoreSqsProducer> response = super.execute(context);
    return response;
  }

  @Override
  public ScoreCall3Context getContext(Integer decision, Long limit, Integer term) {
    return null;
  }
}
