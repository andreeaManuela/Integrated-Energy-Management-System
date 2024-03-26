package com.example.sd_frateanandreea_backend2.repositories;

import com.example.sd_frateanandreea_backend2.entities.Device;
import com.example.sd_frateanandreea_backend2.entities.DeviceClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IdClientRepository extends JpaRepository<DeviceClient, UUID> {
  //  List<Device> findByIdClient(DeviceClient device);
}
