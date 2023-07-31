package asia.atmonline.myriskservice.services.score.web;

import asia.atmonline.myriskservice.enums.ProductCode;
import java.time.LocalDateTime;

public class Cache {

  public static String ilScoreModel;
  public static LocalDateTime ilScoreModelUpdDt;
  public static String rs1ScoreModel;
  public static LocalDateTime rs1ScoreModelUpdDt;
  public static String rs2ScoreModel;
  public static LocalDateTime rs2ScoreModelUpdDt;
  public static String rs3PlusScoreModel;
  public static LocalDateTime rs3PlusScoreModelUpdDt;

  public static Boolean checkModLastUpdDtm(ProductCode productCode) {
    LocalDateTime now = LocalDateTime.now();
    boolean needed = false;
    switch (productCode) {
      case IL:
        if (ilScoreModelUpdDt == null || now.minusMinutes(10L)
            .isAfter(ilScoreModelUpdDt)) {
          ilScoreModelUpdDt = now;
          needed = true;
        }
      case RS1:
        if (rs1ScoreModelUpdDt == null || now.minusMinutes(10L)
            .isAfter(rs1ScoreModelUpdDt)) {
          rs1ScoreModelUpdDt = now;
          needed = true;
        }
      case RS2:
        if (rs2ScoreModelUpdDt == null || now.minusMinutes(10L)
            .isAfter(rs2ScoreModelUpdDt)) {
          rs2ScoreModelUpdDt = now;
          needed = true;
        }
      case RS3, RS4, RS5, RS6, RS7:
        if (rs3PlusScoreModelUpdDt == null || now.minusMinutes(10L)
            .isAfter(rs3PlusScoreModelUpdDt)) {
          rs3PlusScoreModelUpdDt = now;
          needed = true;
        }
    }
    return needed;
  }
}