package spribe.spribetechtask.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import spribe.spribetechtask.model.OrderStatus;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Data
public class BookingOrderDto implements Serializable {
  private long id;
  private LocalDateTime creationDate;
  private OrderStatus status;
  private LocalDateTime bookedFrom;
  private LocalDateTime bookedTo;
}
