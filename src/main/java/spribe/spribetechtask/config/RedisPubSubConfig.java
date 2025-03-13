package spribe.spribetechtask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import spribe.spribetechtask.redis_scheduler.RedisListener;

/**
 * @Author danynest @CreateAt 11.03.25
 */
@Configuration
public class RedisPubSubConfig {
  @Value("${expired.redis.event}")
  private String EXPIRED_EVENT_REDIS_CHANNEL;

  @Bean
  public RedisMessageListenerContainer redisMessageListenerContainer(
      RedisConnectionFactory connectionFactory, RedisListener redisListener) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.addMessageListener(
        new MessageListenerAdapter(redisListener), new PatternTopic(EXPIRED_EVENT_REDIS_CHANNEL));
    return container;
  }
}
