package org.example.springsensorinsights.repositories;

import org.example.springsensorinsights.entities.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing sensor data from the database.
 */
@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    /**
     * Counts the number of sensor data records with the specified reading correctness.
     *
     * @param readingIsCorrect The correctness status of the reading.
     * @return The count of sensor data records with the specified correctness.
     */
    long countByReadingIsCorrect(boolean readingIsCorrect);
}

