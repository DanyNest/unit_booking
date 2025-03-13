package spribe.spribetechtask.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spribe.spribetechtask.dao.UnitDao;
import spribe.spribetechtask.model.AccommodationType;
import spribe.spribetechtask.model.dto.UnitDto;
import spribe.spribetechtask.model.dto.mapper.UnitMapper;
import spribe.spribetechtask.model.dto.request.CreateUnitRequest;
import spribe.spribetechtask.model.dto.request.UpdateUnitRequest;
import spribe.spribetechtask.service.UnitService;

/**
 * @Author danynest @CreateAt 10.03.25
 */
@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {
  private final UnitDao unitDao;
  private final UnitMapper unitMapper;

  @Override
  @CacheEvict(
      value = {"units", "availableUnits"},
      allEntries = true)
  public UnitDto createNewUnit(CreateUnitRequest createUnitRequest) {
    return unitMapper.unitToUnitDTO(unitDao.createUnit(createUnitRequest));
  }

  @Override
  @CacheEvict(
      value = {"units", "availableUnits"},
      allEntries = true)
  public void removeUnit(long unitId) {
    unitDao.deleteUnit(unitId);
  }

  @Override
  @CacheEvict(
      value = {"units", "availableUnits"},
      allEntries = true)
  public UnitDto updateUnitInfo(UpdateUnitRequest updateUnitRequest) {
    return unitMapper.unitToUnitDTO(unitDao.updateUnit(updateUnitRequest));
  }

  @Override
  @Cacheable(
      value = "availableUnits",
      key =
          "T(java.util.Objects).hash(#unitId, #name, #accommodationType, #roomsCount, #floorNumber, #priceFrom, #priceTo, #fromDate, #toDate, #pageable.pageNumber, #pageable.pageSize)")
  public Page<UnitDto> getAllAvailableUnits(
      Long unitId,
      String name,
      AccommodationType accommodationType,
      Integer roomsCount,
      Integer floorNumber,
      BigDecimal priceFrom,
      BigDecimal priceTo,
      LocalDateTime fromDate,
      LocalDateTime toDate,
      Pageable pageable) {
    return unitDao
        .getAllUnitsByParams(
            unitId,
            name,
            accommodationType,
            roomsCount,
            floorNumber,
            priceFrom,
            priceTo,
            fromDate,
            toDate,
            pageable)
        .map(unitMapper::unitToUnitDTO);
  }

  @Cacheable(value = "units", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
  @Override
  public Page<UnitDto> getAllUnits(Pageable pageable) {
    return unitDao.getAllUnits(pageable).map(unitMapper::unitToUnitDTO);
  }
}
