package spribe.spribetechtask.model.dto.mapper;

import org.mapstruct.Mapper;
import spribe.spribetechtask.model.BookingOrder;
import spribe.spribetechtask.model.dto.BookingOrderDto;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Mapper(componentModel = "spring")
public interface BookingOrderMapper {
  BookingOrderDto mapBookingOrderDto(BookingOrder bookingOrder);
}
