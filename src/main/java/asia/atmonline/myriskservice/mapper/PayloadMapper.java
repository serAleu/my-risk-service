package asia.atmonline.myriskservice.mapper;

import asia.atmonline.myriskservice.consumer.payload.RequestPayload;
import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PayloadMapper {

  RiskRequestRiskJpaEntity payloadToEntity(RequestPayload payload);

  RiskResponseRiskJpaEntity payloadToEntity(ResponsePayload payload);

  RequestPayload entityToPayload(RiskRequestRiskJpaEntity payload);

  ResponsePayload entityToPayload(RiskResponseRiskJpaEntity payload);

}
