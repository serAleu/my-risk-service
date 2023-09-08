package asia.atmonline.myriskservice.enums.risk;

public enum RejectionReasonCode {
  SEONPHONE(1),
  BL_PHONE(2),
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
  INCOME2LOW(19),
  SCORECALL1ERR(20),
  SCORECALL2ERR(21),
  SCORECALL3ERR(22),
  SCORECALL1(23),
  SCORECALL2(24),
  SCORECALL3(25),
  BL_ACCOUNT_F(26),
  BL_PASSPORT_F(27),
  DD_MAXDPD_F(28),
  DD_ACTLOAN_F(29),
  DD_ACTAPPL_F(30),
  DD_REJECTS_F(31),
  AGE2LOW_F(32),
  AGE2HIGH_F(33),
  REGION_F(34),
  OCCUPATION_F(35),
  INDUSTRY_F(36),
  INCOME2LOW_F(37);

  private final int ruleId;

  RejectionReasonCode(int ruleId) {
    this.ruleId = ruleId;
  }

  public int getRuleId() {
    return ruleId;
  }
}