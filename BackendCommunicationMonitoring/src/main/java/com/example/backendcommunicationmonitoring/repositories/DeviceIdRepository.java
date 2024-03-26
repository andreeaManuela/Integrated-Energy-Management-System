package com.example.backendcommunicationmonitoring.repositories;

import com.example.backendcommunicationmonitoring.entities.DeviceIdTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceIdRepository extends JpaRepository<DeviceIdTable, UUID> {
}
