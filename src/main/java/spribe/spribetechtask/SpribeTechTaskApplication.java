package spribe.spribetechtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "spribe.spribetechtask")
@EnableJpaAuditing
public class SpribeTechTaskApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpribeTechTaskApplication.class, args);
  }

}
