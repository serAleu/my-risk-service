package asia.atmonline.myriskservice.data.storage.entity.borrower;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class AddressData implements Serializable {

//  @Enumerated(EnumType.STRING)
//  @Column(name = "province")
//  private Province province;
//
//  @Enumerated(EnumType.STRING)
//  @Column(name = "district")
//  private District district;

  @Column(name = "city")
  private String city;

  @Column(name = "house_street")
  private String houseStreet;

  @Column(name = "flat")
  private String flat;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "living_term")
//  private LivingTerm livingTerm;
//
//  @Enumerated(EnumType.STRING)
//  @Column(name = "residential_status")
//  private ResidentialStatus residentialStatus;

  @Column(name = "address_city_id")
  private Long addressCityId;

//  @Transient
//  public String getLineAddress() {
//
//    StringBuilder result = new StringBuilder(EMPTY);
//    String comma = ", ";
//    append(result, LocalizeUtils.getLocalizedValue("province", getProvince()), EMPTY);
//    append(result, LocalizeUtils.getLocalizedValue("district", getDistrict()), comma);
//    append(result, getCity(), comma);
//    append(result, getHouseStreet(), comma);
//    append(result, getFlat(), comma);
//
//    return result.toString();
//  }

//  private void append(StringBuilder target, String value, String delimiter) {
//    if (isEmpty(value)) {
//      return;
//    }
//    final StringBuilder sb = Objects.requireNonNull(target);
//    sb.append(delimiter).append(capitalize(value));
//  }
}
