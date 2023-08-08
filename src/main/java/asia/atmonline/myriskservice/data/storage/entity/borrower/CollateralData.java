package asia.atmonline.myriskservice.data.storage.entity.borrower;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollateralData {

  @Column(name = "cd_phone_brand")
  private String phoneBrand;

  @Column(name = "cd_phone_model")
  private String phoneModel;

  @Column(name = "cd_phone_color")
  private String phoneColor;

  @Column(name = "cd_phone_year")
  private String phoneYear;

  @Column(name = "cd_phone_imei")
  private String phoneIMEI;
}
