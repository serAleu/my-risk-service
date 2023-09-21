package asia.atmonline.myriskservice.data.risk.repositories.external_responses.experian;

import asia.atmonline.myriskservice.data.risk.entity.external_responses.experian.CreditBureauInfo;
import asia.atmonline.myriskservice.data.risk.repositories.BaseRiskJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditBureauInfoJpaRepository extends BaseRiskJpaRepository<CreditBureauInfo> {

}
