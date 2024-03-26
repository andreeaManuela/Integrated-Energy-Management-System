package com.example.backendcommunicationmonitoring.repositories;

import com.example.backendcommunicationmonitoring.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {
    @Query("SELECT m FROM Measurement m WHERE m.device_id.device_id = :device_id")
    List<Measurement> getMeasurementByDevice_id(@Param("device_id") UUID device_id);

    @Query("SELECT m.max_consump FROM DeviceIdTable m WHERE m.device_id = :device_id")
    double getMaxConsump(@Param("device_id") UUID device_id);
}
