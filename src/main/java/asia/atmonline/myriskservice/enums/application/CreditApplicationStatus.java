package asia.atmonline.myriskservice.enums.application;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CreditApplicationStatus {
  /**
   * Заявка создана
   */
  INITIAL(1000, true, false),
  /**
   * Заявка проходит автоматическую верификацию
   */
  SCORING_IN_PROGRESS(1100, true, false),

  WAIT_FOR_RISK_DECISION(1110, true,true),
  /**
   * Заявка отправлена на ручное рассмотрение
   */
  SENT_TO_UNDERWRITING(1120, true, false),
  /**
   * Заявка рассматривается верификатором
   */
  UNDERWRITING_IN_PROGRESS(1140, true, true),
  /**
   * Заявка ожидает подписи клиента на портале
   */
  WAITING_FOR_CLIENT_SIGN(1150, true, true),
  /**
   * Заявка переведена на верификатора для получения подписи клиента
   */
  READY_TO_PICK_UP_FOR_SIGN(1160, true, true),
  /**
   * Заявка отклонена
   */
  REJECTED(1200, false, true),
  // currently used only for MOBILE application
  REJECTED_BY_VERIFIER(1201, false, true),
  /**
   * Заявка одобрена, оплата клиенту пока не подтверждена
   */
  APPROVED(1250, true, true),
  /**
   * Клиент отказался от заявки
   */
  CANCELED_BY_CLIENT(1300, false, true),
  /**
   * Клиент согласился на конечные условия заявки
   */
  CONFIRMED_BY_CLIENT(1350, true, true),
  /**
   * Заявка готова к проведению выплаты
   */
  PREPARED_FOR_OUTGOING_PAYMENT(1400, true, true),
  /**
   * Заявка готова к проведению авто-выплаты
   */
  READY_FOR_AUTO_DISBURSEMENT(1405, true, true),
  /**
   * Заявка в процессе авто-выплаты
   */
  AUTO_PAYOUT_IN_PROGRESS(1410, true, true),
  /**
   * Ошибка авто-выплаты
   */
  AUTO_PAYOUT_ERROR(1415, true, true),
  /**
   * Заявка готова к проведению выплаты вручную
   */
  OUTGOING_PAYMENT_IN_PROGRESS(1420, true, true),
  /**
   * Ошибка выплаты по заявке
   */
  OUTGOING_PAYMENT_ERROR(1440, true, true),
  /**
   * Выплата по заявке отменена
   */
  OUTGOING_PAYMENT_CANCELED(1460, false, true),
  /**
   * Заявка выплачена
   */
  OUTGOING_PAYMENT_SUCCEED(1480, false, true);


  public static List<CreditApplicationStatus> aliveStatuses = Stream.of(values())
      .filter(CreditApplicationStatus::isAlive).collect(Collectors.toList());

  public static final List<CreditApplicationStatus> editableStatuses = Stream.of(values())
          .filter(CreditApplicationStatus::isEditable).collect(Collectors.toList());

  private int code;
  private boolean alive;
  private boolean editable;

  public static CreditApplicationStatus valueOf(int code) {
    for (CreditApplicationStatus status : values()) {
      if (status.code == code) {
        return status;
      }
    }
    return null;
  }

  public boolean in(CreditApplicationStatus... statuses) {
    for (CreditApplicationStatus status : statuses) {
      if (this == status) {
        return true;
      }
    }
    return false;
  }
}
