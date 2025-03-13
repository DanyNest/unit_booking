package spribe.spribetechtask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import spribe.spribetechtask.model.dto.BookingOrderDto;
import spribe.spribetechtask.model.dto.request.PlaceAnOrderRequest;
import spribe.spribetechtask.service.BookingOrderService;

/**
 * @Author danynest @CreateAt 11.03.25
 */

@Tag(name = "Orders Management", description = "Operations related to orders")
@RestController
@RequestMapping("/api/v1/book/order")
@RequiredArgsConstructor
public class BookingOrderController {
  private final BookingOrderService bookingOrderService;

  @Operation(summary = "Get all orders")
  @GetMapping("/get/all")
  public Page<BookingOrderDto> getAllOfAllBookingOrders(
      @RequestParam int pageNumber,
      @RequestParam int pageSize,
      @RequestParam String sortDirection,
      @RequestParam String sortProperty) {
    return bookingOrderService.getAllOfCreatedOrders(
        PageRequest.of(pageNumber, pageSize)
            .withSort(Sort.Direction.valueOf(sortDirection), sortProperty));
  }

  @Operation(summary = "Place a new order orders")
  @PostMapping("/place")
  public BookingOrderDto placeOrder(@Valid @RequestBody PlaceAnOrderRequest placeAnOrderRequest) {
    return bookingOrderService.placeOrder(placeAnOrderRequest);
  }

  @Operation(summary = "Cancel order by it's ID")
  @PutMapping("/cancel/{orderId}")
  public BookingOrderDto cancelOrder(@PathVariable int orderId) {
    return bookingOrderService.cancelOrder(orderId);
  }

  @Operation(summary = "Simulate payment order")
  @PutMapping("/payment/{orderId}")
  public BookingOrderDto makeAPayment(@PathVariable int orderId) {
    return bookingOrderService.payOrder(orderId);
  }

  @Operation(summary = "Get all orders by unit")
  @GetMapping("/get/by/unit/{unitId}")
  public Page<BookingOrderDto> getAllOrdersForUnit(
      @PathVariable int unitId,
      @RequestParam int pageNumber,
      @RequestParam int pageSize,
      @RequestParam String sortDirection,
      @RequestParam String sortProperty) {
    return bookingOrderService.findAllOrdersOfUnit(
        unitId,
        PageRequest.of(pageNumber, pageSize)
            .withSort(Sort.Direction.valueOf(sortDirection), sortProperty));
  }
}
