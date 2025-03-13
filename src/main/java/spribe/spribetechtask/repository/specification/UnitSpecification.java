package spribe.spribetechtask.repository.specification;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import spribe.spribetechtask.model.AccommodationType;
import spribe.spribetechtask.model.BookingOrder;
import spribe.spribetechtask.model.Unit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static spribe.spribetechtask.model.OrderStatus.*;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Component
public class UnitSpecification {
  private static final String UNIT_TABLE_NAME = "unit";
  private static final String UNIT_ID_KEY = "id";
  private static final String UNIT_NAME_KEY = "name";
  private static final String UNIT_ACCOMMODATION_TYPE_KEY = "accommodationType";
  private static final String UNIT_PRICE_KEY = "price";
  private static final String UNIT_ROOMS_COUNT_KEY = "roomsCount";
  private static final String UNIT_FLOOR_NUM_KEY = "floorNumber";
  private static final String ORDER_STATUS_KEY = "status";
  private static final String ORDER_START_BOOKING_KEY = "bookedFrom";
  private static final String ORDER_END_BOOKING_KEY = "bookedTo";

  public static Specification<Unit> withBookingDataRange(
      LocalDateTime startDate, LocalDateTime endDate) {
    return (root, query, criteriaBuilder) -> {
      Subquery<Long> subquery = query.subquery(Long.class);
      Root<BookingOrder> bookingOrderRoot = subquery.from(BookingOrder.class);
      subquery.select(bookingOrderRoot.get(UNIT_TABLE_NAME).get(UNIT_ID_KEY));
      subquery.where(
          criteriaBuilder.and(
              criteriaBuilder.lessThanOrEqualTo(
                  bookingOrderRoot.get(ORDER_START_BOOKING_KEY), endDate),
              criteriaBuilder.greaterThanOrEqualTo(
                  bookingOrderRoot.get(ORDER_END_BOOKING_KEY), startDate),
              criteriaBuilder.notEqual(bookingOrderRoot.get(ORDER_STATUS_KEY), CANCELED)));
      return criteriaBuilder.not(root.get(UNIT_ID_KEY).in(subquery));
    };
  }

  public static Specification<Unit> withAccommodationType(AccommodationType type) {
    return (root, query, criteriaBuilder) ->
        type == null ? null : criteriaBuilder.equal(root.get(UNIT_ACCOMMODATION_TYPE_KEY), type);
  }

  public static Specification<Unit> withFloorNumber(Integer floorNumber) {
    return (root, query, criteriaBuilder) ->
        floorNumber == null
            ? null
            : criteriaBuilder.equal(root.get(UNIT_FLOOR_NUM_KEY), floorNumber);
  }

  public static Specification<Unit> withRoomsCount(Integer roomsCount) {
    return (root, query, criteriaBuilder) ->
        roomsCount == null
            ? null
            : criteriaBuilder.equal(root.get(UNIT_ROOMS_COUNT_KEY), roomsCount);
  }

  public static Specification<Unit> withMaxPrice(BigDecimal maxPrice) {
    return (root, query, criteriaBuilder) ->
        maxPrice == null
            ? null
            : criteriaBuilder.lessThanOrEqualTo(root.get(UNIT_PRICE_KEY), maxPrice);
  }

  public static Specification<Unit> withMinPrice(BigDecimal minPrice) {
    return (root, query, criteriaBuilder) ->
        minPrice == null
            ? null
            : criteriaBuilder.greaterThanOrEqualTo(root.get(UNIT_PRICE_KEY), minPrice);
  }

  public static Specification<Unit> withNameRegex(String name) {
    return (root, query, criteriaBuilder) -> {
      if (name == null || name.isEmpty()) {
        return null;
      }
      return criteriaBuilder.like(root.get(UNIT_NAME_KEY), "%" + name + "%");
    };
  }

  public static Specification<Unit> withUnitId(Long unitId) {
    return (root, query, criteriaBuilder) ->
        unitId == null ? null : criteriaBuilder.equal(root.get(UNIT_ID_KEY), unitId);
  }
}
