package asia.atmonline.myriskservice.web.bureau.experian.dto.search.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExperianCCRISIdentityResponse {

  @JsonProperty("CRefId")
  private Long cRefId;

  @JsonProperty("EntityKey")
  private String entityKey;

  @JsonProperty("EntityId")
  private String entityId;

  @JsonProperty("EntityId2")
  private String entityId2;

  @JsonProperty("EntityName")
  private String entityName;

  @JsonProperty("EntityDOBDOC")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate entityDOBDOC;

  @JsonProperty("EntityGroupCode")
  private String entityGroupCode;

  @JsonProperty("EntityState")
  private String entityState;

  @JsonProperty("EntityNationality")
  private String entityNationality;

  @JsonProperty("CcrisNote")
  private String cCrisNote;
}