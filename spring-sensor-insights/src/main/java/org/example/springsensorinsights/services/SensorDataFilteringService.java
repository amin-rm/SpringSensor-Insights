package org.example.springsensorinsights.services;

import org.example.springsensorinsights.entities.SensorData;
import org.example.springsensorinsights.repositories.SensorDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorDataFilteringService {

    @Value("${csv.directory.filtered}")
    private String filteredDirectory;

    private final SensorDataRepository sensorDataRepository;

    public SensorDataFilteringService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    public void filterCorrectReadingsAndSaveToCSV() {
        List<SensorData> allSensorData = sensorDataRepository.findAll();
        List<SensorData> correctReadings = allSensorData.stream()
                .filter(this::isCorrectReading)
                .collect(Collectors.toList());
        saveToCSV(correctReadings);
    }

    private boolean isCorrectReading(SensorData sensorData) {
        double threshold = sensorData.getThreshold();
        double reading = sensorData.getReading();
        return Math.abs(reading - threshold) <= 0.2 * threshold;
    }

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
