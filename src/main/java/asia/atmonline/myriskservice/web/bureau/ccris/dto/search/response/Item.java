package asia.atmonline.myriskservice.web.bureau.ccris.dto.search.response;

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
public class Item {

  @XmlAttribute(name = "CRefId")
  private String cRefId;
  @XmlAttribute(name = "EntityKey")
  private String entityKey;
  @XmlAttribute(name = "EntityId")
  private String entityId;
  @XmlAttribute(name = "EntityId2")
  private String entityId2;
  @XmlAttribute(name = "EntityName")
  private String entityName;
  @XmlAttribute(name = "EntityDOBDOC")
  private String entityDOBDOC;
  @XmlAttribute(name = "EntityGroupCode")
  private String entityGroupCode;
  @XmlAttribute(name = "EntityState")
  private String entityState;
  @XmlAttribute(name = "EntityNationality")
  private String entityNationality;
  @XmlAttribute(name = "CcrisNote")
  private String cCrisNote;
}