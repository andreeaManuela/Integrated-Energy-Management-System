package com.example.sd_frateanandreea_backend2.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Device implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name="id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "max_consump", nullable = false)
    private String max_consump;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client", nullable = false)
    private DeviceClient id_client;

    public Device(){

    }

    public Device(String description, String address, String max_consump, DeviceClient id_client){
        this.description=description;
        this.address=address;
        this.max_consump=max_consump;
        this.id_client= id_client;
    }
/*
    public Device(String description, String address, String max_consump, UUID id_client){
        DeviceClient newDevice= new DeviceClient();
        newDevice.setId(id_client);
        this.description=description;
        this.address=address;
        this.max_consump=max_consump;
        this.id_client= newDevice;
    }*/

    public Device(String description, String address, String max_consump){
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
        return "Device{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", max_consump='" + max_consump + '\'' +
                '}';
    }
}
