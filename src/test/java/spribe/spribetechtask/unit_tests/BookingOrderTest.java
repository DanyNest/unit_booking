package spribe.spribetechtask.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static spribe.spribetechtask.model.AccommodationType.APARTMENTS;
import static spribe.spribetechtask.model.OrderStatus.CANCELED;
import static spribe.spribetechtask.model.OrderStatus.PENDING;
import static spribe.spribetechtask.unit_tests.util.TestUtilComponents.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import spribe.spribetechtask.model.BookingOrder;
import spribe.spribetechtask.model.dto.BookingOrderDto;
import spribe.spribetechtask.model.dto.UnitDto;
import spribe.spribetechtask.model.dto.request.CreateUnitRequest;
import spribe.spribetechtask.model.dto.request.PlaceAnOrderRequest;
import spribe.spribetechtask.repository.BookingOrderRepository;
import spribe.spribetechtask.service.BookingOrderService;
import spribe.spribetechtask.service.UnitService;
import spribe.spribetechtask.unit_tests.containers.ContainersSetup;

/**
 * @Author danynest @CreateAt 12.03.25
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookingOrderTest extends ContainersSetup {
  @Autowired private BookingOrderService bookingOrderService;
  @Autowired private UnitService unitService;
  @Autowired private BookingOrderRepository bookingOrderRepository;
  private UnitDto savedUnit;

  @BeforeEach
  void setUp() {
    CreateUnitRequest createUnitRequest =
        getCreateUnitRequest(
            UNIT_TEST_NAME_CREATE,
            DESCRIPTION_TEST_CREATE,
            PRICE_BEFORE_TAX_CREATED,
            ROOMS_TEST_COUNT_CREATE,
            FLOOR_TEST_NUMBER_CREATE,
            APARTMENTS);
    savedUnit = createUnitDto(createUnitRequest);
    checkCreatedUnit(savedUnit, createUnitRequest);
  }

  @Test
  void placeNewOrderTest() {
    PlaceAnOrderRequest placeAnOrderRequest =
        getPlaceAnOrderRequest(savedUnit.getId(), BOOKING_TEST_DATE_FROM, BOOKING_TEST_DATE_TO);
    BookingOrderDto bookingOrderDto = createBookingOrder(placeAnOrderRequest);
    checkCreatedPendingBookingOrder(bookingOrderDto, placeAnOrderRequest, savedUnit);
  }

  @Test
  void cancelOrderTest() {
    PlaceAnOrderRequest placeAnOrderRequest =
        getPlaceAnOrderRequest(savedUnit.getId(), BOOKING_TEST_DATE_FROM, BOOKING_TEST_DATE_TO);
    BookingOrderDto bookingOrderDto = createBookingOrder(placeAnOrderRequest);
    checkCreatedPendingBookingOrder(bookingOrderDto, placeAnOrderRequest, savedUnit);
    cancelOrder(bookingOrderDto);
    checkCanceledOrder(bookingOrderDto);
  }

  private UnitDto createUnitDto(CreateUnitRequest createUnitRequest) {
    return unitService.createNewUnit(createUnitRequest);
  }

  private void checkCreatedPendingBookingOrder(
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

  private BookingOrderDto createBookingOrder(PlaceAnOrderRequest request) {
    return bookingOrderService.placeOrder(request);
  }

  private void cancelOrder(BookingOrderDto bookingOrderDto) {
    bookingOrderService.cancelOrder(bookingOrderDto.getId());
  }

  private void checkCanceledOrder(BookingOrderDto bookingOrderDto) {
    Optional<BookingOrder> canceledOrder = bookingOrderRepository.findById(bookingOrderDto.getId());
    assertTrue(canceledOrder.isPresent());
    assertEquals(BOOKING_TEST_DATE_FROM, canceledOrder.get().getBookedFrom());
    assertEquals(BOOKING_TEST_DATE_TO, canceledOrder.get().getBookedTo());
    assertEquals(CANCELED, canceledOrder.get().getStatus());
  }
}
