package spribe.spribetechtask.redis_scheduler;

import static java.lang.String.valueOf;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import spribe.spribetechtask.model.BookingOrder;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Service
@RequiredArgsConstructor
public class RedisOrderScheduler {
  @Value("${redis.status.timeout}")
  private int REDIS_ORDER_KEY_LIFETIME;
  @Value("${redis.order.key}")
  private String REDIS_ORDER_KEY;
  private final RedisTemplate<String, String> redisTemplate;

  public void scheduleOrderCancellation(BookingOrder order) {
    String key = REDIS_ORDER_KEY + order.getId();
    redisTemplate
        .opsForValue()
        .set(key, valueOf(order.getId()), Duration.ofSeconds(REDIS_ORDER_KEY_LIFETIME));
  }
}
