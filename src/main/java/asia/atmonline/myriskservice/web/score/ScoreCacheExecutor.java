package asia.atmonline.myriskservice.web.score;

import asia.atmonline.myriskservice.enums.application.ProductCode;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;

public class ScoreCacheExecutor {

  @Value("${score.cache.reload-time-minutes}")
  public static Long reloadTimeMinutes;
  public static String ilScoreModel;
  public static LocalDateTime ilScoreModelUpdDt;
  public static String rs1ScoreModel;
  public static LocalDateTime rs1ScoreModelUpdDt;
  public static String rs2ScoreModel;
  public static LocalDateTime rs2ScoreModelUpdDt;
  public static String rs3PlusScoreModel;
  public static LocalDateTime rs3PlusScoreModelUpdDt;

  public static Boolean isCachedModelLastUpdDtmAfterReloadTimeMinutes(ProductCode productCode) {
    LocalDateTime now = LocalDateTime.now();
    boolean needed = false;
    if (ilScoreModelUpdDt == null || now.minusMinutes(reloadTimeMinutes)
        .isAfter(ilScoreModelUpdDt)) {
      ilScoreModelUpdDt = now;
      needed = true;
    }
//    switch (productCode) {
//      case IL:
//        if (ilScoreModelUpdDt == null || now.minusMinutes(reloadTimeMinutes)
//            .isAfter(ilScoreModelUpdDt)) {
//          ilScoreModelUpdDt = now;
//          needed = true;
//        }
//      case RS1:
//        if (rs1ScoreModelUpdDt == null || now.minusMinutes(reloadTimeMinutes)
//            .isAfter(rs1ScoreModelUpdDt)) {
//          rs1ScoreModelUpdDt = now;
//          needed = true;
//        }
//      case RS2:
//        if (rs2ScoreModelUpdDt == null || now.minusMinutes(reloadTimeMinutes)
//            .isAfter(rs2ScoreModelUpdDt)) {
//          rs2ScoreModelUpdDt = now;
//          needed = true;
//        }
//      case RS3, RS4, RS5, RS6, RS7:
//        if (rs3PlusScoreModelUpdDt == null || now.minusMinutes(reloadTimeMinutes)
//            .isAfter(rs3PlusScoreModelUpdDt)) {
//          rs3PlusScoreModelUpdDt = now;
//          needed = true;
//        }
//    }
    return needed;
  }
}