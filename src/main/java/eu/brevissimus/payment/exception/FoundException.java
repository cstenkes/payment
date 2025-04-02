package eu.brevissimus.payment.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FoundException extends RuntimeException {
  private final ErrorCode errorCode;
  private final String message;

  public FoundException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
    this.message = message;
  }
}
