package spribe.spribetechtask.unit_tests.containers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @Author danynest @CreateAt 12.03.25
 */
@Testcontainers
public class ContainersSetup {
  @Container
  public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

  @Container
  static GenericContainer<?> redis = new GenericContainer<>("redis:latest").withExposedPorts(6379);

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.data.redis.host", redis::getHost);
    registry.add("spring.data.redis.port", () -> redis.getFirstMappedPort().intValue());
  }

  @BeforeAll
  public static void setup() {
    redis.start();
    postgres.start();
  }

  @AfterAll
  static void tearDown() {
    redis.stop();
    postgres.stop();
  }
}
