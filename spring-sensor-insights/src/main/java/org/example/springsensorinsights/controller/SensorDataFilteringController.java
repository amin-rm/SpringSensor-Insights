package org.example.springsensorinsights.controller;


import org.example.springsensorinsights.services.SensorDataFilteringService;
import org.example.springsensorinsights.services.SensorDataProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensor-data")
public class SensorDataFilteringController {

    @Autowired
    private SensorDataFilteringService sensorDataFilteringService;

    @Autowired
    private SensorDataProcessingService sensorDataProcessingService;

    @PostMapping("/filter-correct-readings")
    public ResponseEntity<?> filterCorrectReadings() {
        sensorDataProcessingService.processCSVFiles();
        sensorDataFilteringService.filterCorrectReadingsAndSaveToCSV();
        return ResponseEntity.ok().build();
    }
}
