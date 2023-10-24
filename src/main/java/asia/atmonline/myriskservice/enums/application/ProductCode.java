package asia.atmonline.myriskservice.enums.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductCode {

  IL_Start_RPH(1),
  RS1(2),
  RS2(3),
  RS3(4),
  RS4(5),
  RS5(6),
  RS6(7),
  RS7(8);

  private final long codeNum;

  public static ProductCode getProductByCode(long codeNum) {
    for (ProductCode code : values()) {
      if (code.codeNum == codeNum) {
        return code;
      }
    }
    return null;
  }
}
