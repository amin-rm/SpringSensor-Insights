package org.example.springsensorinsights.repositories;

import org.example.springsensorinsights.entities.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    long countByReadingIsCorrect(boolean readingIsCorrect);
}
