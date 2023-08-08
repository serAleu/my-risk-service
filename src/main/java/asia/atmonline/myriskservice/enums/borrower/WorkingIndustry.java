package asia.atmonline.myriskservice.enums.borrower;

import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;

@Getter
@AllArgsConstructor
public enum WorkingIndustry {
  MANUFACTURING(false),
  SECURITY_GUARDS(false),
  TRADING(false),
  CONSTRUCTIONS(false),
  IT_AND_TELECOMMUNICATION(false),
  FUEL_AND_ENERGY(true),
  EDUCATION_AND_SCIENCE(false),
  BANKING_INSURANCE_FINANCE_CONSULTING(false),
  MINING_SECTOR(true),
  FARMERS_AGRICULTURE(false),
  CULTURE_ARTIST(true),
  HEALTH_CARE_MEDICAL(false),
  GOVERNMENT_AUTHORITIES(false),
  BEVERAGE_FOODS(true),
  GARMENTS_FACTORIES(true),
  AIR_FORCE_ARMY_NAVY_POLICE_OTHER_FORCES(false),
  MEDIA(false),
  ADVERTISING_STUDIO(true),
  TEA_FACTORY(true),
  OTHER_SERVICES(false),
  DRIVERS_TRANSPORTATION(false),
  TOURISM_HOTELS(false),
  RESTAURANTS_CAFE(false),
  ATTORNEY_LAWYER_NOTARY(false);

  private final boolean excludedOnPortal;

  public static WorkingIndustry[] getActive() {
    return EnumUtils.getEnumList(WorkingIndustry.class).stream()
        .filter(occupationType -> !occupationType.isExcludedOnPortal())
        .sorted(Comparator.comparing(WorkingIndustry::name))
        .toArray(WorkingIndustry[]::new);
  }

  public static WorkingIndustry[] getExcluded() {
    return EnumUtils.getEnumList(WorkingIndustry.class).stream()
        .filter(WorkingIndustry::isExcludedOnPortal)
        .sorted(Comparator.comparing(WorkingIndustry::name))
        .toArray(WorkingIndustry[]::new);
  }
}
