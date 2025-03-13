package spribe.spribetechtask.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static spribe.spribetechtask.model.AccommodationType.FLAT;
import static spribe.spribetechtask.model.AccommodationType.HOME;
import static spribe.spribetechtask.model.OrderStatus.PENDING;
import static spribe.spribetechtask.unit_tests.util.TestUtilComponents.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import spribe.spribetechtask.model.AccommodationType;
import spribe.spribetechtask.model.Unit;
import spribe.spribetechtask.model.dto.BookingOrderDto;
import spribe.spribetechtask.model.dto.UnitDto;
import spribe.spribetechtask.model.dto.request.CreateUnitRequest;
import spribe.spribetechtask.model.dto.request.PlaceAnOrderRequest;
import spribe.spribetechtask.model.dto.request.UpdateUnitRequest;
import spribe.spribetechtask.repository.UnitRepository;
import spribe.spribetechtask.service.BookingOrderService;
import spribe.spribetechtask.service.UnitService;
import spribe.spribetechtask.unit_tests.containers.ContainersSetup;

/**
 * @Author danynest @CreateAt 12.03.25
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UnitTest extends ContainersSetup {

  private static UnitDto savedUnit;
  @Autowired private UnitService unitService;
  @Autowired private UnitRepository unitRepository;
  @Autowired private BookingOrderService bookingOrderService;

  private static Stream<Arguments> dynamicParams() {
    return Stream.of(
        Arguments.of(
            1L, null, null, null, null, null, null, BOOKING_TEST_DATE_FROM, BOOKING_TEST_DATE_TO),
        Arguments.of(
            null,
            "dynamic1",
            null,
            null,
            null,
            null,
            null,
            BOOKING_TEST_DATE_FROM,
            BOOKING_TEST_DATE_TO),
        Arguments.of(
            null, null, FLAT, null, null, null, null, BOOKING_TEST_DATE_FROM, BOOKING_TEST_DATE_TO),
        Arguments.of(
            null, null, null, 1, null, null, null, BOOKING_TEST_DATE_FROM, BOOKING_TEST_DATE_TO),
        Arguments.of(
            null, null, null, null, 2, null, null, BOOKING_TEST_DATE_FROM, BOOKING_TEST_DATE_TO),
        Arguments.of(
            null,
            null,
            null,
            null,
            null,
            BigDecimal.ZERO,
            null,
            BOOKING_TEST_DATE_FROM,
            BOOKING_TEST_DATE_TO),
        Arguments.of(
            null,
            null,
            null,
            null,
            null,
            null,
            BigDecimal.TEN,
            BOOKING_TEST_DATE_FROM,
            BOOKING_TEST_DATE_TO),
        Arguments.of(
            null,
            null,
            null,
            null,
            null,
            new BigDecimal("4.60"),
            new BigDecimal("4.60"),
            BOOKING_TEST_DATE_FROM,
            BOOKING_TEST_DATE_TO),
        Arguments.of(
            2L,
            "dynamic1",
            FLAT,
            1,
            2,
            new BigDecimal("4.60"),
            new BigDecimal("4.60"),
            BOOKING_TEST_DATE_FROM,
            BOOKING_TEST_DATE_TO));
  }

  @BeforeEach
  public void createUnitBeforeEachTest() {
    CreateUnitRequest createUnitRequest =
        getCreateUnitRequest(
            UNIT_TEST_NAME_CREATE,
            DESCRIPTION_TEST_CREATE,
            PRICE_BEFORE_TAX_CREATED,
            ROOMS_TEST_COUNT_CREATE,
            FLOOR_TEST_NUMBER_CREATE,
            HOME);
    savedUnit = createUnitDto(createUnitRequest);
    checkCreatedUnit(savedUnit, createUnitRequest);
  }

  @Test
  void checkGetAllUnits() {
    Page<UnitDto> allUnits = unitService.getAllUnits(PageRequest.of(0, 1));
    assertNotNull(allUnits);
    assertFalse(allUnits.getContent().isEmpty());
    assertEquals(1, allUnits.getContent().size());
  }

  @ParameterizedTest
  @Execution(ExecutionMode.SAME_THREAD)
  @MethodSource(value = "dynamicParams")
  void getAllAvailableByDynamicParams(
      Long unitId,
      String name,
      AccommodationType accommodationType,
      Integer roomsCount,
      Integer floorNumber,
      BigDecimal priceFrom,
      BigDecimal priceTo,
      LocalDateTime fromDate,
      LocalDateTime toDate) {
    CreateUnitRequest createUnitRequest =
        getCreateUnitRequest("dynamic1", "dynamic1", new BigDecimal(4), 1, 2, FLAT);
    UnitDto unitDto = createUnitDto(createUnitRequest);
    System.out.println("✅ Created unit: " + unitDto);
    Optional<UnitDto> allAvailableUnits =
        unitService
            .getAllAvailableUnits(
                unitId,
                name,
                accommodationType,
                roomsCount,
                floorNumber,
                priceFrom,
                priceTo,
                fromDate,
                toDate,
                PageRequest.of(0, 1))
            .stream()
            .findFirst();
    assertTrue(allAvailableUnits.isPresent());
    if (unitId != null) {
      assertEquals(allAvailableUnits.get().getId(), unitId);
    }
    if (accommodationType != null) {
      assertEquals(allAvailableUnits.get().getAccommodationType(), accommodationType);
    }
    if (name != null) {
      assertEquals(allAvailableUnits.get().getName(), name);
    }
    if (roomsCount != null) {
      assertEquals(allAvailableUnits.get().getRoomsCount(), roomsCount);
    }
    if (floorNumber != null) {
      assertEquals(allAvailableUnits.get().getFloorNumber(), floorNumber);
    }
    double unitDoublePrice = allAvailableUnits.get().getPrice().doubleValue();
    if (priceFrom != null) {
      double price = priceFrom.doubleValue();
      assertTrue(unitDoublePrice >= price);
    }
    if (priceTo != null) {
      double price = priceTo.doubleValue();
      assertTrue(unitDoublePrice <= price);
    }
  }

  @Test
  void testUpdateUnit() {
    UpdateUnitRequest updateUnitRequest =
        getUpdateUnitRequest(
            savedUnit.getId(),
            DESCRIPTION_TEST_UPDATE,
            PRICE_BEFORE_TAX_UPDATED,
            FLOOR_TEST_NUMBER_UPDATE,
            ROOMS_TEST_COUNT_UPDATE,
            UNIT_TEST_NAME_UPDATE,
            FLAT);
    UnitDto unitDto = unitService.updateUnitInfo(updateUnitRequest);
    checkUpdatedUnit(unitDto, updateUnitRequest);
  }

  @Test
  void testDeleteUnit() {
    unitService.removeUnit(savedUnit.getId());
    Optional<Unit> deletedUnit = unitRepository.findById(savedUnit.getId());
    assertTrue(deletedUnit.isEmpty());
  }

  @Test
  void checkUnitBeforeOrderAvailable() {
    checkAvailableUnit(savedUnit);
  }

  @Test
  void checkUnitAfterOrderUnAvailable() {
    checkAvailableUnit(savedUnit);
    PlaceAnOrderRequest placeAnOrderRequest =
        getPlaceAnOrderRequest(savedUnit.getId(), BOOKING_TEST_DATE_FROM, BOOKING_TEST_DATE_TO);
    BookingOrderDto bookingOrderDto = createBookingOrder(placeAnOrderRequest);
    checkCreatedPendingBookingOrder(bookingOrderDto, placeAnOrderRequest, savedUnit);
    Optional<UnitDto> availableUnitResult = getAvailableUnitResult(savedUnit);
    assertTrue(availableUnitResult.isEmpty());
  }

  private void checkAvailableUnit(UnitDto savedUnit) {
    Optional<UnitDto> availableUnitResult = getAvailableUnitResult(savedUnit);
    assertTrue(availableUnitResult.isPresent());
    assertEquals(savedUnit.getName(), availableUnitResult.get().getName());
    assertEquals(savedUnit.getDescription(), availableUnitResult.get().getDescription());
    assertEquals(savedUnit.getRoomsCount(), availableUnitResult.get().getRoomsCount());
    assertEquals(
        savedUnit.getAccommodationType(), availableUnitResult.get().getAccommodationType());
    assertEquals(savedUnit.getPrice(), availableUnitResult.get().getPrice());
    assertEquals(savedUnit.getFloorNumber(), availableUnitResult.get().getFloorNumber());
  }

  private Optional<UnitDto> getAvailableUnitResult(UnitDto savedUnit) {
    return unitService
        .getAllAvailableUnits(
            savedUnit.getId(),
            null,
            null,
            null,
            null,
            null,
            null,
            BOOKING_TEST_DATE_FROM,
            BOOKING_TEST_DATE_TO,
            PageRequest.of(0, 1))
        .stream()
        .findFirst();
  }

  private UnitDto createUnitDto(CreateUnitRequest createUnitRequest) {
    return unitService.createNewUnit(createUnitRequest);
  }

  private BookingOrderDto createBookingOrder(PlaceAnOrderRequest request) {
    return bookingOrderService.placeOrder(request);
  }

  public void checkCreatedPendingBookingOrder(
      BookingOrderDto bookingOrderDto, PlaceAnOrderRequest placeAnOrderRequest, UnitDto savedUnit) {
    assertNotNull(bookingOrderDto);
    assertEquals(placeAnOrderRequest.getFromDate(), bookingOrderDto.getBookedFrom());
    assertEquals(placeAnOrderRequest.getToDate(), bookingOrderDto.getBookedTo());
    assertEquals(PENDING, bookingOrderDto.getStatus());
    Optional<BookingOrderDto> orderByUnitId =
        bookingOrderService.findAllOrdersOfUnit(savedUnit.getId(), PageRequest.of(0, 1)).stream()
            .findFirst();
    assertTrue(orderByUnitId.isPresent());
    assertEquals(BOOKING_TEST_DATE_FROM, orderByUnitId.get().getBookedFrom());
    assertEquals(BOOKING_TEST_DATE_TO, orderByUnitId.get().getBookedTo());
    assertEquals(PENDING, orderByUnitId.get().getStatus());
  }
}
