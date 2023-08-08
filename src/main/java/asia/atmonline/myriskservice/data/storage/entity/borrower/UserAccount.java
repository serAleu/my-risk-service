package asia.atmonline.myriskservice.data.storage.entity.borrower;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class UserAccount extends BaseStorageEntity {

  @Column(name = "external_id", unique = true)
  private String externalId;

  @Column(name = "login", unique = true)
  private String login;

  @Column(name = "password")
  private String password;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "secret_question")
  private String secretQuestion;

  @Column(name = "secret_answer")
  private String secretAnswer;

  @Column(name = "secret_key")
  private String secretKey;

  @Column(name = "locked", nullable = false, columnDefinition = "bool default false")
  private boolean locked = false;

  @Column(name = "locked_until")
  private LocalDateTime lockedUntil;

  @Column(name = "login_attempt", nullable = false, columnDefinition = "integer default 0")
  private Integer loginAttempt = 0;
}