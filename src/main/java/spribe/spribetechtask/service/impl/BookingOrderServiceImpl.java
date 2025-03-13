package spribe.spribetechtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spribe.spribetechtask.dao.BookingOrderDao;
import spribe.spribetechtask.model.dto.BookingOrderDto;
import spribe.spribetechtask.model.dto.mapper.BookingOrderMapper;
import spribe.spribetechtask.model.dto.request.PlaceAnOrderRequest;
import spribe.spribetechtask.service.BookingOrderService;

import static spribe.spribetechtask.model.OrderStatus.CANCELED;
import static spribe.spribetechtask.model.OrderStatus.PAID;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Service
@RequiredArgsConstructor
public class BookingOrderServiceImpl implements BookingOrderService {
  private final BookingOrderDao bookingOrderDao;
  private final BookingOrderMapper bookingOrderMapper;

  @Override
  @CacheEvict(
      value = {"allOrders", "allByUnitId", "availableUnits"},
      allEntries = true)
  public BookingOrderDto placeOrder(PlaceAnOrderRequest placeAnOrderRequest) {
    return bookingOrderMapper.mapBookingOrderDto(
        bookingOrderDao.createBookingOrder(placeAnOrderRequest));
  }

  @Override
  @CacheEvict(
      value = {"allOrders", "allByUnitId", "availableUnits"},
      allEntries = true)
  public BookingOrderDto cancelOrder(long orderId) {
    return bookingOrderMapper.mapBookingOrderDto(
        bookingOrderDao.updateBookingOrderStatus(CANCELED, orderId));
  }

  @Override
  @CacheEvict(
      value = {"allOrders", "allByUnitId", "availableUnits"},
      allEntries = true)
  public BookingOrderDto payOrder(long orderId) {
    return bookingOrderMapper.mapBookingOrderDto(
        bookingOrderDao.updateBookingOrderStatus(PAID, orderId));
  }

  @Override
  @Cacheable(
      value = {"allOrders"},
      key = "#pageable.pageNumber + '-' + #pageable.pageSize")
  public Page<BookingOrderDto> getAllOfCreatedOrders(Pageable pageable) {
    return bookingOrderDao
        .getAllBookingOrders(pageable)
        .map(bookingOrderMapper::mapBookingOrderDto);
  }

  @Override
  @Cacheable(
      value = "allByUnitId",
      key = "#unitId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
  public Page<BookingOrderDto> findAllOrdersOfUnit(long unitId, Pageable pageable) {
    return bookingOrderDao
        .findAllByUnitId(unitId, pageable)
        .map(bookingOrderMapper::mapBookingOrderDto);
  }
}
