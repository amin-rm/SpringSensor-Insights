package org.example.springsensorinsights;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Generates sample sensor data and saves it to a CSV file.
 */
public class SensorDataGenerator {

    /**
     * Generates sample sensor data and saves it to a CSV file.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        final String filePath = "SensordataCsv/new/sensor_data.csv";
        try {
            final FileWriter csvWriter = new FileWriter(filePath);
            csvWriter.append("sensorId,reading,threshold\n"); // Header row
            final Random random = new Random();
            final StringBuilder stringBuilder = new StringBuilder();
            String sensorId;
            Double reading = null;
            Double threshold = null;
            for (int i = 1; i <= 100; i++) {
                sensorId = "hash_" + i;
                reading = random.nextDouble() * 100; // 0.0 to 99.9
                threshold = reading + random.nextDouble() * 10; // Threshold slightly higher than reading
                stringBuilder.setLength(0); // Clear the StringBuilder
                stringBuilder.append(sensorId).append(",").append(reading).append(",").append(threshold).append("\n");
                csvWriter.append(stringBuilder);
            }
            csvWriter.flush();
            csvWriter.close();
            System.out.println("Sensor data generated and saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }
}

