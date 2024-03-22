package org.example.springsensorinsights.services;

import jakarta.annotation.Resource;
import org.example.springsensorinsights.entities.SensorData;
import org.example.springsensorinsights.repositories.SensorDataRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for processing sensor data files.
 */
@Service
public class SensorDataProcessingService {

    @Value("${csv.directory.new}")
    private String newDirectory;

    @Value("${csv.directory.archived}")
    private String archivedDirectory;

    @Resource
    private SensorDataRepository sensorDataRepository;

    /**
     * Scheduled method to process CSV files containing sensor data.
     * Runs initially after 5 seconds, then at a fixed rate of 65,000 milliseconds (65 seconds).
     */
    @Scheduled(initialDelay = 5000, fixedRate = 65000)
    public void processCSVFiles() {
        try {
            List<Path> csvFiles = getCSVFilesFromDirectory(newDirectory);
            for (Path csvFile : csvFiles) {
                List<SensorData> sensorDataList = readCSVFile(csvFile);
                calculateIsCorrect(sensorDataList); // Calculate correctness before saving
                saveToDatabase(sensorDataList);
                moveFileToArchivedDirectory(csvFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves CSV files from the specified directory.
     *
     * @param directory The directory containing CSV files.
     * @return A list of paths to CSV files.
     * @throws IOException If an I/O error occurs.
     */
    private List<Path> getCSVFilesFromDirectory(String directory) throws IOException {
        return Files.list(Paths.get(directory))
                .filter(path -> path.toString().toLowerCase().endsWith(".csv"))
                .collect(Collectors.toList());
    }

    /**
     * Reads sensor data from a CSV file.
     *
     * @param csvFile The path to the CSV file.
     * @return A list of SensorData objects parsed from the CSV file.
     * @throws IOException If an I/O error occurs.
     */
    private List<SensorData> readCSVFile(Path csvFile) throws IOException {
        List<String> lines = Files.readAllLines(csvFile);
        return lines.stream()
                .skip(1) // Skip header row
                .map(this::mapToSensorData)
                .collect(Collectors.toList());
    }

    /**
     * Maps a line from a CSV file to a SensorData object.
     *
     * @param line The line from the CSV file.
     * @return A SensorData object representing the data in the line.
     */
    private SensorData mapToSensorData(String line) {
        String[] parts = line.split(",");
        String sensorId = parts[0];
        double reading = Double.parseDouble(parts[1]);
        double threshold = Double.parseDouble(parts[2]);
        return new SensorData(sensorId, reading, threshold);
    }

    /**
     * Calculates whether sensor readings are correct based on a threshold.
     *
     * @param sensorDataList The list of sensor data to process.
     */
    private void calculateIsCorrect(List<SensorData> sensorDataList) {
        for (SensorData sensorData : sensorDataList) {
            double threshold = sensorData.getThreshold();
            double reading = sensorData.getReading();
            boolean isCorrect = Math.abs(reading - threshold) <= 0.2 * threshold;
            sensorData.setReadingIsCorrect(isCorrect);
        }
    }

    /**
     * Saves sensor data to the database.
     *
     * @param sensorDataList The list of sensor data to save.
     */
    private void saveToDatabase(List<SensorData> sensorDataList) {
        sensorDataRepository.saveAll(sensorDataList);
    }

    /**
     * Moves processed CSV files to the archived directory.
     *
     * @param csvFile The path to the CSV file to be archived.
     * @throws IOException If an I/O error occurs.
     */
    private void moveFileToArchivedDirectory(Path csvFile) throws IOException {
        String fileName = csvFile.getFileName().toString();
        Files.move(csvFile, Paths.get(archivedDirectory, fileName));
    }
}



