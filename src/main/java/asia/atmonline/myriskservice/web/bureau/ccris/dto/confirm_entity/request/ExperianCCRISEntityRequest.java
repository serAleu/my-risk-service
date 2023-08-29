package asia.atmonline.myriskservice.web.bureau.ccris.dto.confirm_entity.request;

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
public class ExperianCCRISEntityRequest {

  @XmlAttribute(name = "ProductType")
  private String productType;
  @XmlAttribute(name = "CRefId")
  private String cRefId;
  @XmlAttribute(name = "EntityKey")
  private String entityKey;
  @XmlAttribute(name = "MobileNo")
  private String mobileNo;
  @XmlAttribute(name = "EmailAddress")
  private String emailAddress;
  @XmlAttribute(name = "LastKnownAddress")
  private String lastKnownAddress;
  @XmlAttribute(name = "ConsentGranted")
  private String ConsentGranted;
  @XmlAttribute(name = "EnquiryPurpose")
  private String enquiryPurpose;
  @XmlAttribute(name = "Ref1")
  private String ref1;
  @XmlAttribute(name = "Ref2")
  private String ref2;
  @XmlAttribute(name = "Ref3")
  private String ref3;
  @XmlAttribute(name = "Ref4")
  private String ref4;
}
