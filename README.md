# SpringSensor Insights

## Overview
SpringSensor Insights is a Spring Boot application that focuses on processing sensor data using Java Stream API, Maps, and scheduled tasks. The application simulates receiving new sensor data stored in CSV files, processes the data to identify correct readings, and provides statistics on sensor data accuracy.

## Objectives
- Practice working with Java Stream API for data processing.
- Utilize Spring Task Scheduler for executing tasks at specific intervals.
- Implement filtering of sensor readings to identify correct values.
- Compare execution time of filtering methods using streams and direct database calls.
- Display statistics on correct sensor readings.

## System Description
The system simulates receiving sensor data stored in CSV files with three columns: sensorId, reading, and threshold. The threshold represents a reference value used for comparison with the sensor's actual reading to determine if it is considered significant or within normal operating range.

## Requirements
1. Create directories for storing sensor data files and archive processed files.
2. Implement a Spring cron job to read sensor data from CSV files, save it to the database, and archive the files.
3. Filter correct sensor readings using two different methods (using streams and direct database calls) and compare execution times.
4. Create a cron job to display statistics of correct readings in the console.
5. Use the provided `SensorDataGenerator` class to generate test data for the system.

## Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Task
- Apache Commons CSV
- H2 Database (for testing)
- MySQL Connector Java (for MySQL database)

## Getting Started
1. Clone the repository: `git clone <repository-url>`
2. Navigate to the project directory: `cd SpringSensor-Insights`
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`

## Usage
- Place CSV files with sensor data in the `sensorData/new` directory.
- The application will automatically process the data, save it to the database, and move the files to the `sensorData/archived` directory.
- Use the cron jobs to filter correct readings and display statistics on correct readings.

## Contributors
- [Ahmed Amine Romdhani](https://github.com/amine-rm)
