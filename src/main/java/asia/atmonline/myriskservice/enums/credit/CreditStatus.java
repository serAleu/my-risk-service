package asia.atmonline.myriskservice.enums.credit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public enum CreditStatus {

  /**
   * Займ активен
   */
  ACTIVE(1700, false, true),
  /**
   * По займу активирована пролонгация
   */
  PROLONGED(1720, false, true),
  /**
   * Займ просрочен
   */
  EXPIRED(1740, false, true),
  /**
   * Калькуляция займа не производится, хотя займ может быть не погашен
   */
  CALCULATION_STOPPED(1800, false, true),
  /**
   * Займ реструктуризирован
   */
//    RESTRUCTURED(1840, false, true),
  /**
   * Займ реструктуризирован и просрочен
   */
//    RESTRUCTURED_EXPIRED(1860, false, true),
  /**
   * Аннулирован
   */
  ANNULLED(1900, true, false),
  /**
   * Займ погашен, остались долги по просрочке
   */
  REPAID_OVERDUE(1940, false, true),
  /**
   * Кредит продан сторонней организации
   */
  SOLD(1950, true, false),
  /**
   * Займ погашен
   */
  FINISHED(2000, true, false);

  private int code;

  private boolean alive;
  private boolean ending;

  CreditStatus(int code, boolean ending, boolean alive) {
    this.code = code;
    this.ending = ending;
    this.alive = alive;
  }

  public int getCode() {
    return code;
  }

  public boolean isAlive() {
    return alive;
  }

  public boolean isEnding() {
    return ending;
  }

  public boolean isCredit() {
    return getCode() >= ACTIVE.getCode();
  }

  public static CreditStatus valueOf(int code) {
    for (CreditStatus status : values()) {
      if (status.code == code) {
        return status;
      }
    }
    return null;
  }

  public boolean in(CreditStatus... statuses) {
    for (CreditStatus status : statuses) {
      if (this == status) {
        return true;
      }
    }
    return false;
  }

  public static Collection<Integer> codes() {
    Collection<Integer> result = new ArrayList<>(values().length);
    for (CreditStatus status : values()) {
      result.add(status.getCode());
    }
    return result;
  }

  public static List<CreditStatus> getNotFinishedStatuses() {
    return Arrays.stream(CreditStatus.values())
            .filter(status -> !status.isEnding() && status.isAlive())
            .collect(Collectors.toList());
  }
}
