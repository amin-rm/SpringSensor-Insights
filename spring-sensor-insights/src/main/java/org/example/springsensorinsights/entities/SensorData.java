package org.example.springsensorinsights.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing sensor data.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sensordata")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sensorId;
    private double reading;
    private double threshold;
    private boolean readingIsCorrect;

    /**
     * Constructs a SensorData object with the given sensorId, reading, and threshold.
     *
     * @param sensorId  The ID of the sensor.
     * @param reading   The reading recorded by the sensor.
     * @param threshold The threshold value for comparison.
     */
    public SensorData(String sensorId, double reading, double threshold) {
        this.sensorId = sensorId;
        this.reading = reading;
        this.threshold = threshold;
    }
}

