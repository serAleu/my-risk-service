package asia.atmonline.myriskservice.enums.application;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CreditApplicationStatus {

  INITIAL(1000, true, true),
  DEDUPLICATION2_CHECK_PASSED(1010, true, true),
  FIRST_STEP_COMPLETED(1100, true, true),
  SECOND_STEP_COMPLETED(1110, true, true),
  THIRD_STEP_COMPLETED(1120, true, true),
  FOURTH_STEP_COMPLETED(1130, true, true),
  APPLICATION_SUBMITTED(2000, true, true),
  DEDUPLICATION3_CHECK_PASSED(2100, true, true),
  CREDIT_HISTORY_AVAILABILITY_CHECKED(2110, true, true),
  BASIC_CHECKS_PASSED(2120, true, true),
  FIRST_SCORE_CALCULATION_PASSED(2130, true, true),
  SEON_DATA_COLLECTED(2140, true, true),
  SECOND_SCORE_CALCULATION_PASSED(2150, true, true),
  WAITING_FOR_EXPERIAN_CHECK(2160, true, true),
  EXPERIAN_CHECK_PASSED(2170, true, true),
  EXPERIAN_CHECK_FAILED(2171, true, true),
  CREDIT_BUREAU_DATA_COLLECTED(2180, true, true),
  THIRD_SCORE_CALCULATION_PASSED(2190, true, true),
  VERIFICATION_IN_PROGRESS(3000, true, true),
  VERIFICATION_PASSED(3010, true, true),
  APPROVED(4000, true, true),
  REJECTED(4100, false, false),
  REJECTED_BY_CLIENT(4200, false, false),
  TERMS_CONSENT_RECEIVED(4500, true, true),
  LOAN_EXPLANATIONS_VIEWED(4510, true, true),
  AGREEMENT_CONSENT_RECEIVED(4520, true, true),
  SIGNATURE_RECEIVED(4530, true, true),
  WAITING_FOR_DD_ACTIVATION(4540, true, true),
  CONTRACT_SIGNED(5000, true, true),
  READY_FOR_PAYOUT(5010, true, true),
  PAYOUT_IN_PROGRESS(5020, true, true),
  PAYOUT_CANCELLED(5030, false, true),
  PAYOUT_ERROR(5040, true, true),
  OUTGOING_PAYMENT_SUCCEED(6000, false, true),
  AGREEMENTS_STAMPED(7000, false, true);

  public static final List<CreditApplicationStatus> editableStatuses = Stream.of(values())
      .filter(CreditApplicationStatus::isEditable).collect(Collectors.toList());
  public static final List<CreditApplicationStatus> aliveStatuses = Stream.of(values())
      .filter(CreditApplicationStatus::isAlive).collect(Collectors.toList());
  private final int code;
  private final boolean alive;
  private final boolean editable;

  public static CreditApplicationStatus valueOf(int code) {
    for (CreditApplicationStatus status : values()) {
      if (status.code == code) {
        return status;
      }
    }
    return null;
  }

  public boolean in(CreditApplicationStatus... statuses) {
    for (CreditApplicationStatus status : statuses) {
      if (this == status) {
        return true;
      }
    }
    return false;
  }
}
