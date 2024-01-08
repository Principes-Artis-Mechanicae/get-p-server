package es.princip.getp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GetpServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GetpServerApplication.class, args);
    }
}
