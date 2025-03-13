package spribe.spribetechtask.redis_scheduler;

import static spribe.spribetechtask.model.OrderStatus.CANCELED;
import static spribe.spribetechtask.model.OrderStatus.PENDING;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import spribe.spribetechtask.repository.BookingOrderRepository;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisListener implements MessageListener {
  private static final String KEY_PREFIX = "order:expire:";
  private static final String EMPTY_STRING = "";
  private final BookingOrderRepository bookingOrderRepository;

  @Override
  public void onMessage(Message message, byte[] pattern) {
    String key = new String(message.getBody(), StandardCharsets.UTF_8);
    if (key.startsWith(KEY_PREFIX)) {
      String orderIdStr = key.replace(KEY_PREFIX, EMPTY_STRING);
      long orderId = Long.parseLong(orderIdStr);
      bookingOrderRepository
          .findById(orderId)
          .ifPresent(
              order -> {
                if (order.getStatus().equals(PENDING)) {
                  order.setStatus(CANCELED);
                  bookingOrderRepository.save(order);
                }
              });
    }
  }
}
