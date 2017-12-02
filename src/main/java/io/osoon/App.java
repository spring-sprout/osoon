package io.osoon;

import io.osoon.repository.queryresult.TopicView;
import io.osoon.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author whiteship
 */
@SpringBootApplication
@EntityScan(basePackageClasses = {User.class, TopicView.class})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
