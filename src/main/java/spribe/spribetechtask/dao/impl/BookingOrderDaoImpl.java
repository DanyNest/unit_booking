package spribe.spribetechtask.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import spribe.spribetechtask.dao.BookingOrderDao;
import spribe.spribetechtask.exception.OrderNotFoundException;
import spribe.spribetechtask.exception.UnitNotFoundException;
import spribe.spribetechtask.model.BookingOrder;
import spribe.spribetechtask.model.OrderStatus;
import spribe.spribetechtask.model.Unit;
import spribe.spribetechtask.model.dto.request.PlaceAnOrderRequest;
import spribe.spribetechtask.redis_scheduler.RedisOrderScheduler;
import spribe.spribetechtask.repository.BookingOrderRepository;
import spribe.spribetechtask.repository.UnitRepository;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Component
@RequiredArgsConstructor
public class BookingOrderDaoImpl implements BookingOrderDao {
  private final BookingOrderRepository bookingOrderRepository;
  private final UnitRepository unitRepository;
  private final RedisOrderScheduler redisOrderScheduler;

  @Override
  public BookingOrder createBookingOrder(PlaceAnOrderRequest request) {
    Unit unit =
        unitRepository
            .findById(request.getUnitId())
            .orElseThrow(
                () -> new UnitNotFoundException("Unit not found with id " + request.getUnitId()));
    BookingOrder bookingOrder = new BookingOrder();
    bookingOrder.setBookedTo(request.getToDate());
    bookingOrder.setBookedFrom(request.getFromDate());
    bookingOrder.setStatus(OrderStatus.PENDING);
    bookingOrder.setUnit(unit);
    BookingOrder createdOrder = bookingOrderRepository.save(bookingOrder);
    redisOrderScheduler.scheduleOrderCancellation(createdOrder);
    System.out.println("Scheduling cancellation for order: " + bookingOrder.getId());
    return createdOrder;
  }

  @Override
  public BookingOrder updateBookingOrderStatus(OrderStatus status, long orderId) {
    BookingOrder bookingOrder =
        bookingOrderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order not found by id " + orderId));
    bookingOrder.setStatus(status);
    return bookingOrderRepository.save(bookingOrder);
  }

  @Override
  public Page<BookingOrder> getAllBookingOrders(Pageable pageable) {
    return bookingOrderRepository.findAll(pageable);
  }

  @Override
  public Page<BookingOrder> findAllByUnitId(long unitId, Pageable pageable) {
    return bookingOrderRepository.findByUnitId(unitId, pageable);
  }
}
