package org.example.springsensorinsights.services;

import org.example.springsensorinsights.repositories.SensorDataRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StatisticsCalculationService {

    private final SensorDataRepository sensorDataRepository;

    public StatisticsCalculationService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    @Scheduled(initialDelay = 10000, fixedRate = 70000) // Run after 10 seconds, then every 70 seconds
    public void calculateStatistics() {
        long totalReadings = sensorDataRepository.count();
        long correctReadings = sensorDataRepository.countByReadingIsCorrect(true);

        double percentageCorrect = (double) correctReadings / totalReadings * 100;

        System.out.println("Percentage of correct readings: " + percentageCorrect + "%");
    }
}

