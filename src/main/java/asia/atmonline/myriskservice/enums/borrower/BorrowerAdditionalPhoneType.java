package asia.atmonline.myriskservice.enums.borrower;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BorrowerAdditionalPhoneType {
    BACKOFFICE_USER("BACKOFFICE_USER"),
    SKIP_VENDOR("Skip Vendor"),
    SKIP_MANUAL_GROUP("Skip Manual Group");

    @JsonValue
    private String value;

    @JsonCreator
    public static BorrowerAdditionalPhoneType fromValues(String value) {
        for (BorrowerAdditionalPhoneType type : BorrowerAdditionalPhoneType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
}
