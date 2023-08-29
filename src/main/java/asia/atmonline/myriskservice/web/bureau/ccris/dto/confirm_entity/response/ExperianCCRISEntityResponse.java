package asia.atmonline.myriskservice.web.bureau.ccris.dto.confirm_entity.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ExperianCCRISEntityResponse {

  @XmlAttribute(name = "token1")
  private String token1;
  @XmlAttribute(name = "token2")
  private String token2;

}
