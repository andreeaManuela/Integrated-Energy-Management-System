package com.example.sd_frateanandreea_backend2.repositories;

import com.example.sd_frateanandreea_backend2.entities.Device;
import com.example.sd_frateanandreea_backend2.entities.DeviceClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Device d WHERE d.id_client.id = :id_client ")
    void deleteDevicesById_client(@Param("id_client") UUID id_client);

    @Query("SELECT d FROM Device d WHERE d.id_client.id = :id_client")
    List<Device> findDeviceByIdClient(@Param("id_client") UUID id_client);

}
