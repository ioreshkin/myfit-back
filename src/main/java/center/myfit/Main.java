package center.myfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/** Основной класс запуска приложения. */
@SpringBootApplication
@EnableConfigurationProperties
public class Main {

  /** Метод запуска приложения. */
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
