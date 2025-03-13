package spribe.spribetechtask.dao.impl;

import static spribe.spribetechtask.repository.specification.UnitSpecification.*;
import static spribe.spribetechtask.util.UnitUtil.calculatePriceWithTax;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import spribe.spribetechtask.dao.UnitDao;
import spribe.spribetechtask.exception.UnitNotFoundException;
import spribe.spribetechtask.model.AccommodationType;
import spribe.spribetechtask.model.Unit;
import spribe.spribetechtask.model.dto.request.CreateUnitRequest;
import spribe.spribetechtask.model.dto.request.UpdateUnitRequest;
import spribe.spribetechtask.repository.UnitRepository;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Component
@RequiredArgsConstructor
public class UnitDaoImpl implements UnitDao {
  private final UnitRepository unitRepository;

  @Override
  public Unit createUnit(CreateUnitRequest createUnitRequest) {
    Unit unit = new Unit();
    unit.setName(createUnitRequest.getName());
    unit.setDescription(createUnitRequest.getDescription());
    unit.setAccommodationType(createUnitRequest.getAccommodationType());
    unit.setFloorNumber(createUnitRequest.getFloorNumber());
    unit.setRoomsCount(createUnitRequest.getRoomsCount());
    unit.setPrice(calculatePriceWithTax(createUnitRequest.getPrice()));
    return unitRepository.save(unit);
  }

  @Override
  public void deleteUnit(long unitId) {
    Unit unit =
        unitRepository
            .findById(unitId)
            .orElseThrow(
                () -> new UnitNotFoundException("Unit has not been found by ID: " + unitId));
    unitRepository.delete(unit);
  }

  @Override
  public Unit updateUnit(UpdateUnitRequest updateUnitRequest) {
    Unit unit =
        unitRepository
            .findById(updateUnitRequest.getUnitId())
            .orElseThrow(
                () ->
                    new UnitNotFoundException(
                        "Unit has not been found by ID: " + updateUnitRequest.getUnitId()));
    if (updateUnitRequest.getAccommodationType() != null) {
      unit.setAccommodationType(updateUnitRequest.getAccommodationType());
    }
    if (updateUnitRequest.getDescription() != null) {
      unit.setDescription(updateUnitRequest.getDescription());
    }
    if (updateUnitRequest.getPrice() != null) {
      unit.setPrice(calculatePriceWithTax(updateUnitRequest.getPrice()));
    }
    if (updateUnitRequest.getName() != null) {
      unit.setName(updateUnitRequest.getName());
    }
    if (updateUnitRequest.getFloorNumber() != null) {
      unit.setFloorNumber(updateUnitRequest.getFloorNumber());
    }
    if (updateUnitRequest.getRoomsCount() != null) {
      unit.setRoomsCount(updateUnitRequest.getRoomsCount());
    }
    return unitRepository.save(unit);
  }

  @Override
  public Page<Unit> getAllUnitsByParams(
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
    Specification<Unit> specification = Specification.where(null);
    specification = specification.and(withBookingDataRange(fromDate, toDate));
    if (name != null) {
      specification = specification.and(withNameRegex(name));
    }
    if (unitId != null) {
      specification = specification.and(withUnitId(unitId));
    }
    if (accommodationType != null) {
      specification = specification.and(withAccommodationType(accommodationType));
    }
    if (floorNumber != null) {
      specification = specification.and(withFloorNumber(floorNumber));
    }
    if (priceTo != null) {
      specification = specification.and(withMaxPrice(priceTo));
    }
    if (priceFrom != null) {
      specification = specification.and(withMinPrice(priceFrom));
    }
    if (roomsCount != null) {
      specification = specification.and(withRoomsCount(roomsCount));
    }
    return unitRepository.findAll(specification, pageable);
  }

  @Override
  public Page<Unit> getAllUnits(Pageable pageable) {
    return unitRepository.findAll(pageable);
  }
}
