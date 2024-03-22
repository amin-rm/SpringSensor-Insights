package org.example.springsensorinsights.services;

import jakarta.annotation.Resource;
import org.example.springsensorinsights.entities.SensorData;
import org.example.springsensorinsights.repositories.SensorDataRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorDataProcessingService {

    @Value("${csv.directory.new}")
    private String newDirectory;

    @Value("${csv.directory.archived}")
    private String archivedDirectory;

    @Resource
    private SensorDataRepository sensorDataRepository;

    @Scheduled(initialDelay = 5000, fixedRate = 65000) // Run after 5 seconds, then every 65 seconds
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

    private List<Path> getCSVFilesFromDirectory(String directory) throws IOException {
        return Files.list(Paths.get(directory))
                .filter(path -> path.toString().toLowerCase().endsWith(".csv"))
                .collect(Collectors.toList());
    }

    private List<SensorData> readCSVFile(Path csvFile) throws IOException {
        List<String> lines = Files.readAllLines(csvFile);
        return lines.stream()
                .skip(1) // Skip header row
                .map(this::mapToSensorData)
                .collect(Collectors.toList());
    }

    private SensorData mapToSensorData(String line) {
        String[] parts = line.split(",");
        String sensorId = parts[0];
        double reading = Double.parseDouble(parts[1]);
        double threshold = Double.parseDouble(parts[2]);
        return new SensorData(sensorId, reading, threshold);
    }

    private void calculateIsCorrect(List<SensorData> sensorDataList) {
        for (SensorData sensorData : sensorDataList) {
            double threshold = sensorData.getThreshold();
            double reading = sensorData.getReading();
            boolean isCorrect = Math.abs(reading - threshold) <= 0.2 * threshold;
            sensorData.setReadingIsCorrect(isCorrect);
        }
    }

    private void saveToDatabase(List<SensorData> sensorDataList) {
        sensorDataRepository.saveAll(sensorDataList);
    }

    private void moveFileToArchivedDirectory(Path csvFile) throws IOException {
        String fileName = csvFile.getFileName().toString();
        Files.move(csvFile, Paths.get(archivedDirectory, fileName));
    }
}


