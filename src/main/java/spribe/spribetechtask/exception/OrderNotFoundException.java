package spribe.spribetechtask.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@AllArgsConstructor
@Getter
public class OrderNotFoundException extends RuntimeException {
  String message;
}
