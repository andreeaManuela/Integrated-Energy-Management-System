package com.example.sd_frateanandreea_backend2.dtos;

import com.example.sd_frateanandreea_backend2.entities.DeviceClient;

import java.util.UUID;

public class DeviceClientDetailsDTO {
    private DeviceClient id;

    public DeviceClientDetailsDTO(){

    }

    public DeviceClient getId() {
        return id;
    }

    public void setId(DeviceClient id) {
        this.id = id;
    }

}
