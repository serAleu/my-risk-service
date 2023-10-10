package asia.atmonline.myriskservice.web.bureau.experian.dto.confirm_entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@AllArgsConstructor
public class ExperianCCRISEntityRequestBody {

  private String ProductType;
  private Long CRefId;
  private String EntityKey;
  private String MobileNo;
  private String EmailAddress;
  private String LastKnownAddress;
  private String ConsentGranted;
  private String EnquiryPurpose;
  private String Ref1;
  private String Ref2;
  private String Ref3;
  private String Ref4;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
