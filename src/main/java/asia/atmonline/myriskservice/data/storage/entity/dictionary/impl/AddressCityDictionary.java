package asia.atmonline.myriskservice.data.storage.entity.dictionary.impl;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.BaseDictionary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dictionary_address_city")
@NoArgsConstructor
@Getter
@Setter
public class AddressCityDictionary extends BaseDictionary {

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn
  private AddressStateDictionary state;

  @Column(name = "prohibited", nullable = false)
  private boolean prohibited;
}