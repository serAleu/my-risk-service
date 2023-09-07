package asia.atmonline.myriskservice.data.storage.entity.dictionary;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseDictionary extends BaseStorageEntity {

  private static final String MALAYSIA_LOCALE = "MY";

  @Column(name = "nameEn", nullable = false)
  private String nameEn;

  @Column(name = "nameMy", nullable = false)
  private String nameMy;

  @Column(name = "active")
  private Boolean active;

  public String getLocalizedName(String locale) {
    return MALAYSIA_LOCALE.equalsIgnoreCase(locale) ? getNameMy() : getNameEn();
  }

}
