package eu.brevissimus.payment.exception;

import eu.brevissimus.payment.model.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
class RestExceptionHandler {

  //private final TimeService timeService;

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDto notFoundExceptionHandler(NotFoundException ex) {
    log.error("Error occurred: {}", ex);
    return new ErrorDto(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()); //timeService.now()
  }

  @ExceptionHandler(FoundException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorDto foundExceptionHandler(FoundException ex) {
    log.error("Error occurred: {}", ex);
    return new ErrorDto(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()); //timeService.now()
  }


}
