package spribe.spribetechtask.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spribe.spribetechtask.model.dto.BookingOrderDto;
import spribe.spribetechtask.model.dto.request.PlaceAnOrderRequest;

/**
 * @Author danynest @CreateAt 11.03.25
 */
public interface BookingOrderService {
  BookingOrderDto placeOrder(PlaceAnOrderRequest placeAnOrderRequest);

  BookingOrderDto cancelOrder(long orderId);

  BookingOrderDto payOrder(long orderId);

  Page<BookingOrderDto> getAllOfCreatedOrders(Pageable pageable);

  Page<BookingOrderDto> findAllOrdersOfUnit(long untd, Pageable pageable);
}
