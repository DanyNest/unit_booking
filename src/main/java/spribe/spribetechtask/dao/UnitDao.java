package spribe.spribetechtask.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import spribe.spribetechtask.model.AccommodationType;
import spribe.spribetechtask.model.Unit;
import spribe.spribetechtask.model.dto.request.CreateUnitRequest;
import spribe.spribetechtask.model.dto.request.UpdateUnitRequest;

/**
 * @Author danynest @CreateAt 11.03.25
 */
public interface UnitDao {
  @Transactional
  Unit createUnit(CreateUnitRequest createUnitRequest);

  @Transactional
  void deleteUnit(long unitId);

  @Transactional
  Unit updateUnit(UpdateUnitRequest updateUnitRequest);

  @Transactional(readOnly = true)
  Page<Unit> getAllUnitsByParams(
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

  @Transactional(readOnly = true)
  Page<Unit> getAllUnits(Pageable pageable);
}
