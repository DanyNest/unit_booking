package spribe.spribetechtask.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import spribe.spribetechtask.model.AccommodationType;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Data
public class CreateUnitRequest {
  @NotNull(message = "Unit name should not be null")
  private String name;

  @NotNull(message = "Accommodation type should not be null")
  private AccommodationType accommodationType;

  @NotNull(message = "Floor number should not be null")
  @Min(value = 1, message = "Floor can not be 0")
  private int floorNumber;

  @NotNull(message = "Rooms count should not be null")
  private int roomsCount;

  @NotNull(message = "Price should not be null")
  private BigDecimal price;

  @NotNull(message = "Description should not be null")
  private String description;
}
