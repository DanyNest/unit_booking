package spribe.spribetechtask.unit_tests.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static spribe.spribetechtask.util.UnitUtil.calculatePriceWithTax;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;
import spribe.spribetechtask.model.AccommodationType;
import spribe.spribetechtask.model.dto.UnitDto;
import spribe.spribetechtask.model.dto.request.CreateUnitRequest;
import spribe.spribetechtask.model.dto.request.PlaceAnOrderRequest;
import spribe.spribetechtask.model.dto.request.UpdateUnitRequest;

/**
 * @Author danynest @CreateAt 12.03.25
 */
@UtilityClass
public class TestUtilComponents {
  public static final int ROOMS_TEST_COUNT_CREATE = 1;
  public static final int ROOMS_TEST_COUNT_UPDATE = 2;
  public static final int FLOOR_TEST_NUMBER_CREATE = 1;
  public static final int FLOOR_TEST_NUMBER_UPDATE = 2;
  public static final BigDecimal PRICE_BEFORE_TAX_CREATED = BigDecimal.ONE;
  public static final BigDecimal PRICE_BEFORE_TAX_UPDATED = BigDecimal.TEN;
  public static final String DESCRIPTION_TEST_CREATE = "createTestDescription";
  public static final String DESCRIPTION_TEST_UPDATE = "updateTestDescription";
  public static final String UNIT_TEST_NAME_CREATE = "createUnitTestName";
  public static final String UNIT_TEST_NAME_UPDATE = "updateUnitTestName";
  public static final LocalDateTime BOOKING_TEST_DATE_FROM = LocalDateTime.of(2025, 1, 1, 0, 0);
  public static final LocalDateTime BOOKING_TEST_DATE_TO = LocalDateTime.of(2025, 1, 31, 0, 0);

  public static UpdateUnitRequest getUpdateUnitRequest(
      long unitId,
      String description,
      BigDecimal price,
      int floorNumber,
      int roomsCount,
      String name,
      AccommodationType accommodationType) {
    UpdateUnitRequest updateUnitRequest = new UpdateUnitRequest();
    updateUnitRequest.setUnitId(unitId);
    updateUnitRequest.setAccommodationType(accommodationType);
    updateUnitRequest.setDescription(description);
    updateUnitRequest.setPrice(price);
    updateUnitRequest.setFloorNumber(floorNumber);
    updateUnitRequest.setRoomsCount(roomsCount);
    updateUnitRequest.setName(name);
    return updateUnitRequest;
  }

  public static CreateUnitRequest getCreateUnitRequest(
      String name,
      String description,
      BigDecimal price,
      int roomsCount,
      int floorNumber,
      AccommodationType accommodationType) {
    CreateUnitRequest createUnitRequest = new CreateUnitRequest();
    createUnitRequest.setName(name);
    createUnitRequest.setDescription(description);
    createUnitRequest.setRoomsCount(roomsCount);
    createUnitRequest.setAccommodationType(accommodationType);
    createUnitRequest.setFloorNumber(floorNumber);
    createUnitRequest.setPrice(price);
    return createUnitRequest;
  }

  public static PlaceAnOrderRequest getPlaceAnOrderRequest(
      long unitId, LocalDateTime from, LocalDateTime to) {
    PlaceAnOrderRequest placeAnOrderRequest = new PlaceAnOrderRequest();
    placeAnOrderRequest.setFromDate(from);
    placeAnOrderRequest.setToDate(to);
    placeAnOrderRequest.setUnitId(unitId);
    return placeAnOrderRequest;
  }

  public static void checkCreatedUnit(UnitDto savedUnit, CreateUnitRequest createUnitRequest) {
    assertNotNull(savedUnit);
    assertEquals(calculatePriceWithTax(createUnitRequest.getPrice()), savedUnit.getPrice());
    assertEquals(createUnitRequest.getName(), savedUnit.getName());
    assertEquals(createUnitRequest.getDescription(), savedUnit.getDescription());
    assertEquals(createUnitRequest.getRoomsCount(), savedUnit.getRoomsCount());
    assertEquals(createUnitRequest.getAccommodationType(), savedUnit.getAccommodationType());
  }

  public static void checkUpdatedUnit(UnitDto savedUnit, UpdateUnitRequest updateUnitRequest) {
    assertNotNull(savedUnit);
    assertEquals(calculatePriceWithTax(updateUnitRequest.getPrice()), savedUnit.getPrice());
    assertEquals(updateUnitRequest.getName(), savedUnit.getName());
    assertEquals(updateUnitRequest.getDescription(), savedUnit.getDescription());
    assertEquals(updateUnitRequest.getRoomsCount(), savedUnit.getRoomsCount());
    assertEquals(updateUnitRequest.getAccommodationType(), savedUnit.getAccommodationType());
  }
}
