package asia.atmonline.myriskservice.web.bureau.ccris.dto.search.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExperianCCRISSearchRequest {

  @XmlAttribute(name = "ProductType")
  private String productType;
  @XmlAttribute(name = "GroupCode")
  private String groupCode;
  @XmlAttribute(name = "EntityName")
  private String entityName;
  @XmlAttribute(name = "EntityId")
  private String entityId;
  @XmlAttribute(name = "EntityId2")
  private String entityId2;
  @XmlAttribute(name = "Country")
  private String country;
  @XmlAttribute(name = "DOB")
  private String dob;
}
