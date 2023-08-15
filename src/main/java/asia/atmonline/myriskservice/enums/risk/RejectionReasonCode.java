package asia.atmonline.myriskservice.enums.risk;

public enum RejectionReasonCode {
  SEONPHONE(1),
  BLACKLIST(2),
  ACTIVE_LOAN(3),
  ACTIVE_APP(4),
  APPLIM_2D(5),
  APPLIM_5W(6),
  APPLIM_9M(7),
  BL_ACCOUNT(8),
  BL_PASSPORT(9),
  DD_MAXDPD(10),
  DD_ACTLOAN(11),
  DD_ACTAPPL(12),
  DD_REJECTS(13),
  AGE2LOW(14),
  AGE2HIGH(15),
  REGION(16),
  OCCUPATION(17),
  INDUSTRY(18),
  INCOME2LOW(19);

  private final int ruleId;

  RejectionReasonCode(int ruleId) {
    this.ruleId = ruleId;
  }

  public int getRuleId() {
    return ruleId;
  }
}