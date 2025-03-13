package spribe.spribetechtask.unit_tests.base_tests;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import spribe.spribetechtask.unit_tests.containers.ContainerSetup;

/**
 * @Author danynest @CreateAt 13.03.25
 */
@SpringBootTest
@Testcontainers
@Import(ContainerSetup.class)
public abstract class BaseTest {

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", ContainerSetup.postgresDB::getJdbcUrl);
    registry.add("spring.datasource.username", ContainerSetup.postgresDB::getUsername);
    registry.add("spring.datasource.password", ContainerSetup.postgresDB::getPassword);
    registry.add("spring.data.redis.host", ContainerSetup.redisContainer::getHost);
    registry.add("spring.data.redis.port", ContainerSetup.redisContainer::getFirstMappedPort);
    registry.add("spring.liquibase.enabled", () -> "false");
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
  }
  @BeforeAll
  static void setup() {
    System.out.println("PostgreSQL running on: " + ContainerSetup.postgresDB.getJdbcUrl());
    System.out.println("Redis running on: " + ContainerSetup.redisContainer.getHost() + ":" + ContainerSetup.redisContainer.getMappedPort(6379));
  }
}