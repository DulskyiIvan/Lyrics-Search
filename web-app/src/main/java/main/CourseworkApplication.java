package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.geekhub.example"})
@EntityScan(basePackages = {"edu.geekhub.example"})
@EnableJpaRepositories({"edu.geekhub.example"})
public class CourseworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseworkApplication.class, args);
    }
}
