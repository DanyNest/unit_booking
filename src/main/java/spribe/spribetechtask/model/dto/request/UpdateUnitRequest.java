package spribe.spribetechtask.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import spribe.spribetechtask.model.AccommodationType;

import java.math.BigDecimal;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Data
public class UpdateUnitRequest {
  @NotEmpty(message = "Unit ID should be provided")
  private long unitId;

  private String name;
  private AccommodationType accommodationType;
  private Integer floorNumber;
  private BigDecimal price;
  private String description;
  private Integer roomsCount;
}
