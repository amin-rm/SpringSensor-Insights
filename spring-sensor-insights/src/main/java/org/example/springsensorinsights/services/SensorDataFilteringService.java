package org.example.springsensorinsights.services;

import org.example.springsensorinsights.entities.SensorData;
import org.example.springsensorinsights.repositories.SensorDataRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StopWatch;

/**
 * Service class responsible for filtering correct sensor readings and saving them to a CSV file using two methods:
 *  -- Java Stream
 *  -- Direct access to the database
 */
@Service
public class SensorDataFilteringService {

    @Value("SensorDataCsv/filtered")
    private String filteredDirectory;

    private final SensorDataRepository sensorDataRepository;

    /**
     * Constructs a SensorDataFilteringService with the specified SensorDataRepository.
     *
     * @param sensorDataRepository The repository for accessing sensor data.
     */
    public SensorDataFilteringService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    /**
     * Scheduled method to filter correct sensor readings and save them to a CSV file using the Stream method.
     * Runs at a fixed rate of 60,000 milliseconds (1 minute).
     */
    @Scheduled(initialDelay = 1000, fixedRate = 60000)
    public void filterCorrectReadingsAndSaveToCSV() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<SensorData> correctReadings = sensorDataRepository.findAll()
                .stream()
                .filter(this::isCorrectReading)
                .toList();;
        saveToCSV(correctReadings);

        stopWatch.stop();
        System.out.println("Processing readings using stream: " + stopWatch.getTotalTimeSeconds() + " seconds");
    }

    /**
     * Scheduled method to filter correct sensor readings and save them to a CSV file using the Stream method.
     * Runs at a fixed rate of 60,000 milliseconds (1 minute).
     */
    @Scheduled(initialDelay = 5000, fixedRate = 60000)
    public void filterCorrectReadingsAndSaveToCSV2() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        long correctReadings = sensorDataRepository.countByReadingIsCorrect(true);

        stopWatch.stop();
        System.out.println("Processing readings using direct access to the DB: " + stopWatch.getTotalTimeSeconds() + " seconds");
    }

    /**
     * Checks if a sensor reading is correct based on the threshold.
     *
     * @param sensorData The sensor data to check.
     * @return True if the reading is correct, false otherwise.
     */
    private boolean isCorrectReading(SensorData sensorData) {
        double threshold = sensorData.getThreshold();
        double reading = sensorData.getReading();
        return Math.abs(reading - threshold) <= 0.2 * threshold;
    }

    /**
     * Saves a list of sensor data to a CSV file in the filtered directory.
     *
     * @param sensorDataList The list of sensor data to save.
     */
    private void saveToCSV(List<SensorData> sensorDataList) {
        try {
            File filteredDirectoryFile = new File(filteredDirectory);
            if (!filteredDirectoryFile.exists()) {
                filteredDirectoryFile.mkdirs();
            }
            FileWriter csvWriter = new FileWriter(filteredDirectory + File.separator + "correct_readings.csv");
            csvWriter.append("sensorId,reading,threshold\n");
            for (SensorData sensorData : sensorDataList) {
                csvWriter.append(sensorData.getSensorId())
                        .append(",")
                        .append(String.valueOf(sensorData.getReading()))
                        .append(",")
                        .append(String.valueOf(sensorData.getThreshold()))
                        .append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }
}
