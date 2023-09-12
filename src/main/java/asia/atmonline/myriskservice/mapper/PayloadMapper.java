package asia.atmonline.myriskservice.mapper;

import asia.atmonline.myriskservice.consumer.payload.RequestPayload;
import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PayloadMapper {

  RiskRequestJpaEntity payloadToEntity(RequestPayload payload);

  RiskResponseJpaEntity payloadToEntity(ResponsePayload payload);

  RequestPayload entityToPayload(RiskRequestJpaEntity payload);

  ResponsePayload entityToPayload(RiskResponseJpaEntity payload);

}
