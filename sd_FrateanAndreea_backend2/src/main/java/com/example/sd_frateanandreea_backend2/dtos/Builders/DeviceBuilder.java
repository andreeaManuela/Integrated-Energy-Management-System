package com.example.sd_frateanandreea_backend2.dtos.Builders;

import com.example.sd_frateanandreea_backend2.dtos.DeviceDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceDetailsDTO;
import com.example.sd_frateanandreea_backend2.entities.Device;

public class DeviceBuilder {

    private DeviceBuilder(){

    }

    public static DeviceDTO toDeviceDTO(Device device){
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(), device.getMax_consump(), device.getId_client().getId());
    }

    public static Device toEntity(DeviceDetailsDTO deviceDetailsDTO){
        return new Device(
                deviceDetailsDTO.getDescription(),
                deviceDetailsDTO.getAddress(),
                deviceDetailsDTO.getMax_consump(),
                deviceDetailsDTO.getId_client());
    }

}
