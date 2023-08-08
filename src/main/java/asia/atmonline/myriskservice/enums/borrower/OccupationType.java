package asia.atmonline.myriskservice.enums.borrower;

import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;

@Getter
@AllArgsConstructor
public enum OccupationType {
  WORKING_WITH_CONTRACT(false),
  WORKING_PERMANENT(false),
  WORKING_PROBATION(false),
  WORKING_WITHOUT_CONTRACT(true),
  BUSINESS_OWNER_OR_SELF_EMPLOYEE(false),
  UNEMPLOYED(false),
  STUDENT_NOT_WORKING(true),
  RETIRED_BUT_WORKING(true),
  RETIRED_AND_GET_PENSION_ONLY(true),
  PENSIONER(false),
  WORKING_ON_PART_TIME(false);

  private boolean excludedOnPortal;

  public static OccupationType[] getActive() {
    return EnumUtils.getEnumList(OccupationType.class).stream()
        .filter(occupationType -> !occupationType.isExcludedOnPortal())
        .sorted(Comparator.comparing(OccupationType::name))
        .toArray(OccupationType[]::new);
  }

  public static OccupationType[] getExcluded() {
    return EnumUtils.getEnumList(OccupationType.class).stream()
        .filter(OccupationType::isExcludedOnPortal)
        .sorted(Comparator.comparing(OccupationType::name))
        .toArray(OccupationType[]::new);
  }

}
