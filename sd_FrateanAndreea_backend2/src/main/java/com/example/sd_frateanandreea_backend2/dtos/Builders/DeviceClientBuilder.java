package com.example.sd_frateanandreea_backend2.dtos.Builders;

import com.example.sd_frateanandreea_backend2.dtos.DeviceClientDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceClientDetailsDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceDetailsDTO;
import com.example.sd_frateanandreea_backend2.entities.Device;
import com.example.sd_frateanandreea_backend2.entities.DeviceClient;

public class DeviceClientBuilder {

    private DeviceClientBuilder(){

    }

    public static DeviceClientDTO toDeviceClientDTO(DeviceClient device){
        return new DeviceClientDTO(device.getId());
    }

    public static DeviceClient toEntity(DeviceClientDetailsDTO deviceDetailsDTO){
        return new DeviceClient(); }


    public static DeviceClient toEntityId(DeviceClientDetailsDTO deviceDetailsDTO){
        return new DeviceClient(deviceDetailsDTO.getId().getId()); }


    public static DeviceClient toEntityDTO(DeviceClientDTO deviceClientDTO){
        return new DeviceClient();
    }

}
