package com.example.backendcommunicationmonitoring.dtos;

import com.example.backendcommunicationmonitoring.entities.DeviceIdTable;

import java.util.UUID;

public class DeviceIdTableDTO {
    private UUID device_id;
    private String max_consump;

    public DeviceIdTableDTO(){

    }

    public DeviceIdTableDTO(UUID id, String max_consump){
        this.device_id=id;
        this.max_consump=max_consump;
    }

    public UUID getDevice_id() {
        return device_id;
    }

    public void setDevice_id(UUID device_id) {
        this.device_id = device_id;
    }

    public String getMax_consump() {
        return max_consump;
    }

    public void setMax_consump(String max_consump) {
        this.max_consump = max_consump;
    }
}
