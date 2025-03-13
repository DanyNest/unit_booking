package spribe.spribetechtask.unit_tests.containers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

/**
 * @Author danynest @CreateAt 12.03.25
 */
@TestConfiguration
public class ContainerSetup {

  public static final PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
      .withReuse(true)
      .waitingFor(Wait.forListeningPort());

  public static final GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest"))
      .withExposedPorts(6379)
      .withReuse(true)
      .waitingFor(Wait.forListeningPort());

  static {
    postgresDB.start();
    redisContainer.start();
  }

  @Bean
  public PostgreSQLContainer<?> postgresDB() {
    return postgresDB;
  }

  @Bean
  public GenericContainer<?> redisContainer() {
    return redisContainer;
  }
}
