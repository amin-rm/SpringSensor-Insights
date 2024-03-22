package org.example.springsensorinsights.services;

import org.example.springsensorinsights.repositories.SensorDataRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for calculating statistics on sensor data.
 */
@Service
public class StatisticsCalculationService {

    private final SensorDataRepository sensorDataRepository;

    /**
     * Constructor to initialize the StatisticsCalculationService.
     *
     * @param sensorDataRepository The sensor data repository to access data.
     */
    public StatisticsCalculationService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    /**
     * Scheduled method to calculate statistics on sensor data.
     * Runs initially after 10 seconds, then at a fixed rate of 70,000 milliseconds (70 seconds).
     */
    @Scheduled(initialDelay = 10000, fixedRate = 70000)
    public void calculateStatistics() {
        long totalReadings = sensorDataRepository.count();
        long correctReadings = sensorDataRepository.countByReadingIsCorrect(true);

        double percentageCorrect = (double) correctReadings / totalReadings * 100;

        System.out.println("Percentage of correct readings: " + percentageCorrect + "%");
    }
}


