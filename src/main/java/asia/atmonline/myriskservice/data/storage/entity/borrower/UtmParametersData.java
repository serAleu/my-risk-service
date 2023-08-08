package asia.atmonline.myriskservice.data.storage.entity.borrower;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UtmParametersData {

  @Column(name = "utm_source")
  private String source;
  @Column(name = "utm_campaign")
  private String campaign;
  @Column(name = "utm_content")
  private String content;
  @Column(name = "utm_medium")
  private String medium;
  @Column(name = "utm_term")
  private String term;

}
