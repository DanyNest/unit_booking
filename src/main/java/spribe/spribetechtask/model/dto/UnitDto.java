package spribe.spribetechtask.model.dto;

import lombok.Data;
import spribe.spribetechtask.model.AccommodationType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author danynest @CreateAt 10.03.25
 */
@Data
public class UnitDto implements Serializable {
  private long id;
  private AccommodationType accommodationType;
  private int roomsCount;
  private LocalDateTime createdAt;
  private BigDecimal price;
  private int floorNumber;
  private String name;
  private String description;
}
