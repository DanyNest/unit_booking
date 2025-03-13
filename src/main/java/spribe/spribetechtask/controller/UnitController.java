package spribe.spribetechtask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import spribe.spribetechtask.model.AccommodationType;
import spribe.spribetechtask.model.dto.UnitDto;
import spribe.spribetechtask.model.dto.request.CreateUnitRequest;
import spribe.spribetechtask.model.dto.request.UpdateUnitRequest;
import spribe.spribetechtask.service.UnitService;

/**
 * @Author danynest @CreateAt 11.03.25
 */

@Tag(name = "Unit Management", description = "Operations related to units")
@RestController
@RequestMapping("/api/v1/unit")
@RequiredArgsConstructor
public class UnitController {
  private final UnitService unitService;

  @Operation(summary = "Get all units")
  @GetMapping("/get/all")
  public Page<UnitDto> getAllUnits(
      @RequestParam int page,
      @RequestParam int size,
      @RequestParam String sortDirection,
      @RequestParam String sortProperty) {
    return unitService.getAllUnits(
        PageRequest.of(page, size).withSort(Sort.Direction.valueOf(sortDirection), sortProperty));
  }

  @Operation(summary = "Find all available units by dynamic params")
  @GetMapping("/find/available/by/params")
  public Page<UnitDto> getAllAvailableUnitsByParams(
      @RequestParam(required = false) Long unitId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) AccommodationType accommodationType,
      @RequestParam(required = false) Integer roomsCount,
      @RequestParam(required = false) Integer floorNumber,
      @RequestParam(required = false) BigDecimal priceFrom,
      @RequestParam(required = false) BigDecimal priceTo,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime fromDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime toDate,
      @RequestParam int pageSize,
      @RequestParam int pageNumber,
      @RequestParam String sortDirection,
      @RequestParam String sortByParameter) {
    return unitService.getAllAvailableUnits(
        unitId,
        name,
        accommodationType,
        roomsCount,
        floorNumber,
        priceFrom,
        priceTo,
        fromDate,
        toDate,
        PageRequest.of(pageNumber, pageSize)
            .withSort(Sort.Direction.valueOf(sortDirection), sortByParameter));
  }

  @Operation(summary = "Create a new unit")
  @PostMapping("/create")
  public UnitDto createUnit(@Valid @RequestBody CreateUnitRequest request) {
    return unitService.createNewUnit(request);
  }

  @Operation(summary = "Create unit info")
  @PutMapping("/update")
  public UnitDto updateUnit(@Valid @RequestBody UpdateUnitRequest request) {
    return unitService.updateUnitInfo(request);
  }

  @Operation(summary = "Delete unit by its id")
  @DeleteMapping("/delete/{unitId}")
  public void deleteUnit(@PathVariable int unitId) {
    unitService.removeUnit(unitId);
  }
}
