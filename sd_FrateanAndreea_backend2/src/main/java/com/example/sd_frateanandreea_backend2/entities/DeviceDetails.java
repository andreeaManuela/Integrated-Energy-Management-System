package com.example.sd_frateanandreea_backend2.entities;

import java.util.UUID;

public class DeviceDetails {
    private UUID deviceId;
    private UUID clientId;
    private String maxConsumption;

    public DeviceDetails(UUID deviceId, UUID clientId, String maxConsumption) {
        this.deviceId = deviceId;
        this.clientId = clientId;
        this.maxConsumption = maxConsumption;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(String maxConsumption) {
        this.maxConsumption = maxConsumption;
    }
}
