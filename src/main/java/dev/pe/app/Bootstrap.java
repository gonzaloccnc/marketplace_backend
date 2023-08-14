package dev.pe.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class Bootstrap {

  public static void main(String[] args) {
    SpringApplication.run(Bootstrap.class, args);
  }
}
