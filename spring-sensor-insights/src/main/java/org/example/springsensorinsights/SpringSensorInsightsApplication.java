package org.example.springsensorinsights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringSensorInsightsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSensorInsightsApplication.class, args);
	}

}
