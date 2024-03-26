package com.example.sd_frateanandreea_backend2.dtos;

import com.example.sd_frateanandreea_backend2.entities.DeviceClient;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeviceDetailsDTO {
    private UUID id;
    @NotNull
    private String description;
    @NotNull
    private String address;
    @NotNull
    private String max_consump;
    @NotNull
    private DeviceClient id_client;

    public DeviceDetailsDTO(){

    }

    public DeviceDetailsDTO(String description, String address, String max_consump){
        this.description=description;
        this.address=address;
        this.max_consump=max_consump;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMax_consump() {
        return max_consump;
    }

    public void setMax_consump(String max_consump) {
        this.max_consump = max_consump;
    }

    public DeviceClient getId_client() {
        return id_client;
    }

    public void setId_client(DeviceClient id_client) {
        this.id_client = id_client;
    }

    @Override
    public String toString() {
        return "DeviceDetailsDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", max_consump='" + max_consump + '\'' +
                '}';
    }
}
