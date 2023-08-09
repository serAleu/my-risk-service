package asia.atmonline.myriskservice.web.blacklist.dto;

import asia.atmonline.myriskservice.enums.application.ProductCode;

public class BlacklistRecordForm {

  private Long id;

  private String idNumber;

  private String phone;

  private String bankAccount;

  private Long ruleId;

  private Long creditApplicationId;

  private String decisionRuleVersion;

  private String decisionTreeVersion;

  private ProductCode productCode;

  public ProductCode getProductCode() {
    return productCode;
  }

  public void setProductCode(ProductCode productCode) {
    this.productCode = productCode;
  }

  public BlacklistRecordForm() {
  }

  public BlacklistRecordForm(Long creditApplicationId, String idNumber, String phone, String bankAccount, Long ruleId, String decisionRuleVersion, ProductCode productCode) {
    this.idNumber = idNumber;
    this.phone = phone;
    this.bankAccount = bankAccount;
    this.ruleId = ruleId;
    this.creditApplicationId = creditApplicationId;
    this.decisionRuleVersion = decisionRuleVersion;
    this.productCode = productCode;
  }

  public Long getCreditApplicationId() {
    return creditApplicationId;
  }

  public void setCreditApplicationId(Long creditApplicationId) {
    this.creditApplicationId = creditApplicationId;
  }

  public String getDecisionRuleVersion() {
    return decisionRuleVersion;
  }

  public void setDecisionRuleVersion(String decisionRuleVersion) {
    this.decisionRuleVersion = decisionRuleVersion;
  }

  public String getDecisionTreeVersion() {
    return decisionTreeVersion;
  }

  public void setDecisionTreeVersion(String decisionTreeVersion) {
    this.decisionTreeVersion = decisionTreeVersion;
  }

  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getBankAccount() {
    return bankAccount;
  }

  public void setBankAccount(String bankAccount) {
    this.bankAccount = bankAccount;
  }

  public Long getRuleId() {
    return ruleId;
  }

  public void setRuleId(Long ruleId) {
    this.ruleId = ruleId;
  }

//  public void validate(Errors errors) {
//    if (StringUtils.isEmpty(idNumber) && StringUtils.isEmpty(phone) && StringUtils.isEmpty(bankAccount)) {
//
//    }
//  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    BlacklistRecordForm other = (BlacklistRecordForm) obj;
    if (id == null) {
      return other.id == null;
    } else {
      return id.equals(other.id);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  private String errorMessage(String message) {
    return message.substring(1, message.length() - 1);
  }
}
