package asia.atmonline.myriskservice.rules.basic.occupation;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicOccupationContext extends BaseBasicContext {

  private final OccupationType occupationType;
  private final List<OccupationTypeDictionary> occupationTypeDictionaries;

  public BasicOccupationContext(boolean isFinalChecks, OccupationType occupationType, List<OccupationTypeDictionary> occupationTypeDictionaries) {
    super(isFinalChecks);
    this.occupationType = occupationType;
    this.occupationTypeDictionaries = occupationTypeDictionaries;
  }
}
