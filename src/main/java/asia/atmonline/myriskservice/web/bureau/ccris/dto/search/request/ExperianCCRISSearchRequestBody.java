package asia.atmonline.myriskservice.web.bureau.ccris.dto.search.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExperianCCRISSearchRequestBody {

  private String ProductType;
  private String GroupCode;
  private String EntityName;
  private String EntityId;
  private String EntityId2;
  private String Country;

//  @JsonProperty("DOB")
//  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//  @JsonDeserialize(using = LocalDateDeserializer.class)
//  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate DOB;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}