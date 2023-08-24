//package asia.atmonline.myriskservice.services.bureau;
//
//import static asia.atmonline.myriskservice.enums.risk.CheckType.BUREAU;
//
//import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
//import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
//import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
//import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
//import asia.atmonline.myriskservice.producers.bureau.BureauSqsProducer;
//import asia.atmonline.myriskservice.services.BaseChecksService;
//import java.util.Map;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BureauChecksService extends BaseChecksService {
//
//  private final BureauSqsProducer producer;
//
//  public BureauChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories, BureauSqsProducer producer) {
//    super(repositories);
//    this.producer = producer;
//  }
//
//  @SuppressWarnings({"unchecked"})
//  public BureauSqsProducer getProducer() {
//    return producer;
//  }
//
//  @Override
//  public RiskResponseJpaEntity<BureauSqsProducer> process(RiskRequestJpaEntity request) {
//    return new RiskResponseJpaEntity<>();
//  }
//
//  @Override
//  public boolean accept(RiskRequestJpaEntity request) {
//    return request != null && BUREAU.equals(request.getCheckType()) && request.getCreditApplicationId() != null;
//  }
//}