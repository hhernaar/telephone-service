package com.hhernaar.telephone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("com.hhernaar.telephone**")
@PropertySource(value = {"classpath:rabbit-queue.properties"})
public class TelephoneServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(TelephoneServiceApplication.class, args);
  }
}
