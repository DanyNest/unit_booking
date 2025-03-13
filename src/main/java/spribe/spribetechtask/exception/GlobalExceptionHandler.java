package spribe.spribetechtask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(exception = RuntimeException.class)
  public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }

  @ExceptionHandler(exception = UnitNotFoundException.class)
  public ResponseEntity<Object> handleUnitNotFoundException(UnitNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(exception = OrderNotFoundException.class)
  public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
