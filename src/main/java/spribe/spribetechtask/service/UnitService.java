package spribe.spribetechtask.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spribe.spribetechtask.model.AccommodationType;
import spribe.spribetechtask.model.dto.UnitDto;
import spribe.spribetechtask.model.dto.request.CreateUnitRequest;
import spribe.spribetechtask.model.dto.request.UpdateUnitRequest;

/**
 * @Author danynest @CreateAt 06.03.25
 */
public interface UnitService {
  UnitDto createNewUnit(CreateUnitRequest createUnitRequest);

  void removeUnit(long unitId);

  UnitDto updateUnitInfo(UpdateUnitRequest updateUnitRequest);

  Page<UnitDto> getAllAvailableUnits(
      Long unitId,
      String name,
      AccommodationType accommodationType,
      Integer roomsCount,
      Integer floorNumber,
      BigDecimal priceFrom,
      BigDecimal priceTo,
      LocalDateTime fromDate,
      LocalDateTime toDate,
      Pageable pageable);

  Page<UnitDto> getAllUnits(Pageable pageable);
}
