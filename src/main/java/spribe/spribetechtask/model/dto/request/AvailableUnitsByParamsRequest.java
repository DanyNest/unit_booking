package spribe.spribetechtask.model.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import spribe.spribetechtask.model.AccommodationType;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Data
public class AvailableUnitsByParamsRequest {
  private Integer unitId;
  private String name;
  private AccommodationType accommodationType;
  private Integer roomsCount;
  private Integer floorNumber;
  private BigDecimal priceFrom;
  private BigDecimal priceTo;

  @NotNull(message = "Start booking date should not be null")
  @DateTimeFormat(pattern = "yyyy-mm-dd")
  private LocalDateTime fromDate;

  @NotNull(message = "End booking date should not be null")
  @DateTimeFormat(pattern = "yyyy-mm-dd")
  private LocalDateTime toDate;
}
