# SpringSensor Insights

## Overview
SpringSensor Insights is a Spring Boot application designed to process sensor data using Java Stream API, Maps, and scheduled tasks. It facilitates the management of sensor data stored in CSV files, identifies correct readings, and provides statistics on data accuracy.

## Objectives
- Utilize Java Stream API for efficient data processing.
- Employ Spring Task Scheduler to execute tasks at specific intervals.
- Implement filtering mechanisms to identify correct sensor readings.
- Compare performance between filtering methods using streams and direct database calls.
- Present statistics regarding correct sensor readings.

## System Description
The system manages sensor data stored in CSV files with three primary columns: sensorId, reading, and threshold. The threshold serves as a reference value for comparison with the actual sensor reading, helping to determine its significance or normalcy.

## Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Task
- Apache Commons CSV
- MySQL Connector Java (for MySQL database)

## Getting Started
1. Clone the repository: `git clone <repository-url>`
2. Navigate to the project directory: `cd SpringSensor-Insights/`
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`

## Usage
- Utilize the `SensorDataGenerator` class's main method to generate test data and save it to the `sensorData/new` directory.
- The application will autonomously process the data, persist it to the database, and move the files to the `sensorData/archived` directory.
- Cron jobs are configured to automatically filter correct readings and store them in the `sensorData/filtered` directory, along with displaying statistics on correct readings.

## Contributors
- [Ahmed Amine Romdhani](https://github.com/amine-rm)
