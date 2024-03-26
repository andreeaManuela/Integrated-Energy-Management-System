package com.example.backendcommunicationmonitoring.dtos;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

public class DeviceIdTableDetailsDTO {
    private UUID device_id;
    @NotNull
    private String max_consump;

    public DeviceIdTableDetailsDTO(){

    }

    public DeviceIdTableDetailsDTO(String max_consump){
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
