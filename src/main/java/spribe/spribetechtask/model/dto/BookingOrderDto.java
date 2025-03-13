package spribe.spribetechtask.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(name = "booking_start_date")
  private LocalDateTime bookedFrom;

  @Column(name = "booking_end_date")
  private LocalDateTime bookedTo;
}
