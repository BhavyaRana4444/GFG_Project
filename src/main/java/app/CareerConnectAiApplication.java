package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This tells Spring Boot where to find all your separate component folders
@SpringBootApplication(scanBasePackages = {"controller", "service", "repository", "policy", "ai"})
public class CareerConnectAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareerConnectAiApplication.class, args);
        System.out.println("Career Connect AI Backend is running!");
    }
}