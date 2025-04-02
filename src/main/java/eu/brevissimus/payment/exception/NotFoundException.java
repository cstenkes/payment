package eu.brevissimus.payment.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NotFoundException extends RuntimeException {
  private final ErrorCode errorCode;
  private final String message;

  public NotFoundException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
    this.message = message;
  }
}
