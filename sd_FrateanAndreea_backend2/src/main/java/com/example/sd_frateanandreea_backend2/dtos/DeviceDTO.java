package com.example.sd_frateanandreea_backend2.dtos;

import com.example.sd_frateanandreea_backend2.entities.Device;
import com.example.sd_frateanandreea_backend2.entities.DeviceClient;

import java.util.UUID;

public class DeviceDTO {
    private UUID id;
    private String description;
    private String address;
    private String max_consump;
    //private DeviceClient id_client;
    private UUID id_client;

    public DeviceDTO(){

    }

    public DeviceDTO(UUID id, String description, String address, String max_consump, UUID id_client){
        this.id=id;
        this.description=description;
        this.address=address;
        this.max_consump=max_consump;
        this.id_client=id_client;
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

    public UUID getId_client() {
        return id_client;
    }

    public void setId_client(UUID id_client) {
        this.id_client = id_client;
    }
}
