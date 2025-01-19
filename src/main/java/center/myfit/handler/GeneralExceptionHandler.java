package center.myfit.handler;

import center.myfit.starter.dto.ErrorDto;
import center.myfit.starter.exception.UnauthorizedException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Глобальный обработчик исключений. */
@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

  /** Обработчик всех необработанных исключений. */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorDto> handle(Exception e) {
    return getResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /** Обработчик исключения валидации. */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorDto> handle(MethodArgumentNotValidException e) {
    return getResponseEntity(e, HttpStatus.BAD_REQUEST);
  }

  /** Обработчик исключения пользователь не авторизаван. */
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<ErrorDto> handle(UnauthorizedException e) {
    return getResponseEntity(e, HttpStatus.FORBIDDEN);
  }

  private ResponseEntity<ErrorDto> getResponseEntity(Exception e, HttpStatus status) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(status.value())
        .body(new ErrorDto(e.getMessage(), status.value(), LocalDateTime.now()));
  }
}
