package asia.atmonline.myriskservice.data.storage.entity.borrower;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@Embeddable
public class ContactPersonData {

  @Column(name = "cpd_name")
  private String name;

  @Column(name = "cpd_phone")
  private String phone;

  @Column(name = "cpd_address")
  private String cpdAddress;

  public String getFullName(String lastName, String firstName, String middleName) {
    StringBuilder result = new StringBuilder(StringUtils.EMPTY);
    if (StringUtils.isNotEmpty(lastName)) {
      result.append(StringUtils.capitalize(lastName));
    }
    if (StringUtils.isNotEmpty(firstName)) {
      firstName = StringUtils.capitalize(firstName);
      result.append(StringUtils.SPACE).append(firstName);
    }
    if (StringUtils.isNotEmpty(middleName)) {
      middleName = StringUtils.capitalize(middleName);
      result.append(StringUtils.SPACE).append(middleName);
    }
    return result.toString();
  }
}
