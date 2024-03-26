package com.example.backendcommunicationmonitoring.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class DeviceIdTable implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @Column(name="device_id", columnDefinition = "BINARY(16)")
    private UUID device_id;

    @Column(name = "max_consump", nullable = false)
    private String max_consump;

    public DeviceIdTable(){

    }

    public DeviceIdTable(UUID device_id){
        this.device_id=device_id;
    }

    public DeviceIdTable(UUID device_id, String max_consump){
        this.device_id=device_id;
        this.max_consump=max_consump;
    }

    public UUID getId_device() {
        return device_id;
    }

    public void setId_device(UUID id_device) {
        this.device_id = id_device;
    }

    public String getMax_consump() {
        return max_consump;
    }

    public void setMax_consump(String max_consump) {
        this.max_consump = max_consump;
    }
}
