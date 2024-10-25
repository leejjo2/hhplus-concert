package com.hhplusconcert.shared.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.logging.LogLevel;

@AllArgsConstructor
@Getter
public enum ErrorType implements IErrorType {
  INVALID_REQUEST(ErrorCode.BAD_REQUEST, "유효하지 않은 요청입니다.", LogLevel.WARN),
  ;

  private final ErrorCode code;
  private final String message;
  private final LogLevel logLevel;

  @AllArgsConstructor
  public enum Token implements IErrorType {

    TOKEN_NOT_CONTAINED(ErrorCode.BAD_REQUEST, "토큰이 존재하지 않습니다.", LogLevel.WARN),
    TOKEN_NOT_VALID(ErrorCode.BAD_REQUEST, "유효하지 않은 토큰입니다.", LogLevel.WARN),
    CLAIM_NOT_FOUND(ErrorCode.BAD_REQUEST, "클레임 정보를 찾을 수 없습니다..", LogLevel.WARN);

    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

    @Override
    public ErrorCode getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public LogLevel getLogLevel() {
      return logLevel;
    }
  }

  @AllArgsConstructor
  public enum WaitingQueue implements IErrorType {
    WAITING_QUEUE_NOT_FOUND(ErrorCode.NOT_FOUND, "대기열 정보를 찾을 수 없습니다.", LogLevel.WARN),
    WAITING_QUEUE_EXPIRED(ErrorCode.BAD_REQUEST, "대기열이 만료되었습니다.", LogLevel.WARN),
    WAITING_QUEUE_ALREADY_ACTIVATED(ErrorCode.BAD_REQUEST, "대기열이 이미 활성 상태입니다.", LogLevel.WARN),
    INVALID_STATUS(ErrorCode.BAD_REQUEST, "대기열 상태가 유효하지 않습니다.", LogLevel.WARN),
    INVALID_EXPIRED_AT(ErrorCode.BAD_REQUEST, "만료 시간이 유효하지 않습니다.", LogLevel.WARN),
    WAITING_QUEUE_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "대기열 ID는 null일 수 없습니다.",
            LogLevel.WARN),
    WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY(ErrorCode.BAD_REQUEST, "대기열 UUID는 비어 있을 수 없습니다.",
            LogLevel.WARN),
    WAITING_QUEUE_STATUS_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "대기열 상태는 null일 수 없습니다.",
            LogLevel.WARN),
    AVAILABLE_SLOTS_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "사용 가능한 슬롯은 null일 수 없습니다.",
            LogLevel.WARN),
    AVAILABLE_SLOTS_MUST_BE_POSITIVE(ErrorCode.BAD_REQUEST, "사용 가능한 슬롯은 0보다 커야 합니다.",
            LogLevel.WARN),
    WAITING_QUEUE_TOKEN_UUID_REQUIRED(ErrorCode.BAD_REQUEST, "대기열 토큰 UUID는 필수입니다.", LogLevel.WARN),
    ;

    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

    @Override
    public ErrorCode getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public LogLevel getLogLevel() {
      return logLevel;
    }
  }

  @AllArgsConstructor
  public enum Concert implements IErrorType {
    CONCERT_NOT_FOUND(ErrorCode.NOT_FOUND, "콘서트를 찾을 수 없습니다.", LogLevel.WARN),
    CONCERT_SCHEDULE_NOT_FOUND(ErrorCode.NOT_FOUND, "콘서트 스케줄을 찾을 수 없습니다.", LogLevel.WARN),
    CONCERT_SEAT_NOT_FOUND(ErrorCode.NOT_FOUND, "콘서트 좌석을 찾을 수 없습니다.", LogLevel.WARN),
    RESERVATION_NOT_FOUND(ErrorCode.NOT_FOUND, "예약을 찾을 수 없습니다.", LogLevel.WARN),
    INVALID_CONCERT_ID(ErrorCode.BAD_REQUEST, "콘서트 ID가 유효하지 않습니다.", LogLevel.WARN),
    CONCERT_SCHEDULE_NOT_RESERVABLE(ErrorCode.BAD_REQUEST, "콘서트 스케줄 예약이 불가능합니다.",
            LogLevel.WARN),
    CONCERT_SEAT_ALREADY_RESERVED(ErrorCode.BAD_REQUEST, "이미 예약된 좌석입니다.", LogLevel.WARN),
    CONCERT_SEAT_NOT_RESERVED(ErrorCode.BAD_REQUEST, "예약되지 않은 좌석입니다.", LogLevel.WARN),
    RESERVATION_ALREADY_PAID(ErrorCode.BAD_REQUEST, "이미 결제된 예약입니다.", LogLevel.WARN),
    RESERVATION_ALREADY_CANCELED(ErrorCode.BAD_REQUEST, "이미 취소된 예약입니다.", LogLevel.WARN),
    RESERVATION_USER_NOT_MATCHED(ErrorCode.BAD_REQUEST, "예약 사용자가 일치하지 않습니다.", LogLevel.WARN),
    CONCERT_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "콘서트 ID는 null일 수 없습니다.", LogLevel.WARN),
    CONCERT_SCHEDULE_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "콘서트 스케줄 ID는 null일 수 없습니다.",
            LogLevel.WARN),
    CONCERT_SEAT_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "콘서트 좌석 ID는 null일 수 없습니다.",
            LogLevel.WARN),
    RESERVATION_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "예약 ID는 null일 수 없습니다.", LogLevel.WARN),
    ;

    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

    @Override
    public ErrorCode getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public LogLevel getLogLevel() {
      return logLevel;
    }
  }

  @AllArgsConstructor
  public enum User implements IErrorType {
    USER_NOT_FOUND(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다.", LogLevel.WARN),
    WALLET_NOT_FOUND(ErrorCode.NOT_FOUND, "지갑을 찾을 수 없습니다.", LogLevel.WARN),
    WALLET_NOT_MATCH_USER(ErrorCode.BAD_REQUEST, "지갑이 사용자와 일치하지 않습니다.", LogLevel.WARN),
    INVALID_AMOUNT(ErrorCode.BAD_REQUEST, "유효하지 않은 충전 금액입니다.", LogLevel.WARN),
    EXCEED_LIMIT_AMOUNT(ErrorCode.BAD_REQUEST, "충전 금액이 한도를 초과했습니다.", LogLevel.WARN),
    NOT_ENOUGH_BALANCE(ErrorCode.BAD_REQUEST, "잔액이 부족합니다.", LogLevel.WARN),
    USER_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "사용자 ID는 null일 수 없습니다.", LogLevel.WARN),
    WALLET_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "지갑 ID는 null일 수 없습니다.", LogLevel.WARN),
    AMOUNT_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "금액은 null일 수 없습니다.", LogLevel.WARN),
    AMOUNT_MUST_BE_POSITIVE(ErrorCode.BAD_REQUEST, "금액은 0보다 커야 합니다.", LogLevel.WARN),
    ;

    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

    @Override
    public ErrorCode getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public LogLevel getLogLevel() {
      return logLevel;
    }
  }

  @AllArgsConstructor
  public enum Payment implements IErrorType {
    PAYMENT_NOT_FOUND(ErrorCode.NOT_FOUND, "결제 정보를 찾을 수 없습니다.", LogLevel.WARN),
    PAYMENT_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "결제 ID는 필수입니다.", LogLevel.WARN),
    ;

    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

    @Override
    public ErrorCode getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public LogLevel getLogLevel() {
      return logLevel;
    }
  }
}
