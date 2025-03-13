package spribe.spribetechtask.unit_tests.redis;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import spribe.spribetechtask.generator.UnitRandomGenerator;
import spribe.spribetechtask.unit_tests.base_tests.BaseTest;

/**
 * @Author danynest @CreateAt 12.03.25
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class RedisCacheTest extends BaseTest {
  private static final String REDIS_TEST_KEY = "testKey";
  private static final String REDIS_TEST_VALUE = "testValue";
  @Autowired private RedisTemplate<String, String> redisTemplate;
  @MockitoBean
  private UnitRandomGenerator unitRandomGenerator;

  @Test
  public void testRedisCacheStorage() {
    redisTemplate.opsForValue().set(REDIS_TEST_KEY, REDIS_TEST_VALUE);
    String cachedValue = redisTemplate.opsForValue().get(REDIS_TEST_KEY);
    assertEquals(REDIS_TEST_VALUE, cachedValue);
  }

  @Test
  public void testRedisCacheEviction() {
    redisTemplate.opsForValue().set(REDIS_TEST_KEY, REDIS_TEST_VALUE);
    assertNotNull(redisTemplate.opsForValue().get(REDIS_TEST_KEY));
    redisTemplate.delete(REDIS_TEST_KEY);
    assertNull(redisTemplate.opsForValue().get(REDIS_TEST_KEY));
  }

  @Test
  public void testRedisCacheExpiration() throws InterruptedException {
    redisTemplate.opsForValue().set(REDIS_TEST_KEY, REDIS_TEST_VALUE, Duration.ofSeconds(2));
    assertNotNull(redisTemplate.opsForValue().get(REDIS_TEST_KEY));
    Thread.sleep(3000);
    assertNull(redisTemplate.opsForValue().get(REDIS_TEST_KEY));
  }
}
