package asia.atmonline.myriskservice.web.blacklist.dto;

import asia.atmonline.myriskservice.enums.application.ProductCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistRecordForm {

  private String id;
  private String passportNumber;
  private String phone;
  private String bankAccount;
  private Long creditApplicationId;
  private String decisionRuleVersion;
  private String decisionTreeVersion;
  private ProductCode productCode;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    BlacklistRecordForm other = (BlacklistRecordForm) obj;
    if (id == null) {
      return other.id == null;
    } else {
      return id.equals(other.id);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }
}
