package spribe.spribetechtask.model.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Data
public class PlaceAnOrderRequest {
  @NotNull(message = "Unit ID must be provided")
  long unitId;

  @NotNull(message = "Booking first date must be provided")
  LocalDateTime fromDate;

  @NotNull(message = "Booking last date must be provided")
  LocalDateTime toDate;
}
