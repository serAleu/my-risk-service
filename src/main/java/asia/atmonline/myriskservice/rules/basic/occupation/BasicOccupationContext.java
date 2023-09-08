package asia.atmonline.myriskservice.rules.basic.occupation;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicOccupationContext extends BaseBasicContext {

  private final OccupationTypeDictionary clientOccupationType;
  private final List<OccupationTypeDictionary> occupationTypeDictionaries;

  public BasicOccupationContext(boolean isFinalChecks, OccupationTypeDictionary clientOccupationType, List<OccupationTypeDictionary> occupationTypeDictionaries) {
    super(isFinalChecks);
    this.clientOccupationType = clientOccupationType;
    this.occupationTypeDictionaries = occupationTypeDictionaries;
  }
}
