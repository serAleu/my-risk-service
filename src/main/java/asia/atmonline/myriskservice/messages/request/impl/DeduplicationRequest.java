package asia.atmonline.myriskservice.messages.request.impl;

import asia.atmonline.myriskservice.messages.request.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DeduplicationRequest extends BaseRequest {

  @JsonProperty("borrower_id")
  private Long borrowerId;
  @JsonProperty("passport_number")
  private String passportNumber;
  @JsonProperty("bank_account")
  private String bankAccount;
  @JsonProperty("is_email_confirmed")
  private Boolean isEmailConfirmed;

}
