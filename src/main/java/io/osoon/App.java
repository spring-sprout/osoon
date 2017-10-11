package io.osoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author whiteship
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.moilago.server.sample.domain"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
