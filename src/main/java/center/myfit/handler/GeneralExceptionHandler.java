package center.myfit.handler;

import center.myfit.dto.ExceptionDto;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDto> handle(Exception e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.internalServerError()
        .body(new ExceptionDto(e.getMessage(), LocalDateTime.now()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionDto> handle(MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.badRequest().body(new ExceptionDto(e.getMessage(), LocalDateTime.now()));
  }
}
