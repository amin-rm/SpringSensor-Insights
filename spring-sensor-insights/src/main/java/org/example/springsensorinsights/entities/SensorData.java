package org.example.springsensorinsights.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public SensorData(String sensorId, double reading, double threshold) {
        this.sensorId = sensorId;
        this.reading = reading;
        this.threshold = threshold;
    }
}
