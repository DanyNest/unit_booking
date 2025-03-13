package spribe.spribetechtask.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import spribe.spribetechtask.model.BookingOrder;
import spribe.spribetechtask.model.OrderStatus;
import spribe.spribetechtask.model.dto.request.PlaceAnOrderRequest;

/**
 * @Author danynest @CreateAt 11.03.25
 */
public interface BookingOrderDao {
  @Transactional
  BookingOrder createBookingOrder(PlaceAnOrderRequest request);

  @Transactional
  BookingOrder updateBookingOrderStatus(OrderStatus request, long orderId);

  @Transactional(readOnly = true)
  Page<BookingOrder> getAllBookingOrders(Pageable pageable);

  @Transactional(readOnly = true)
  Page<BookingOrder> findAllByUnitId(long unitId, Pageable pageable);
}
