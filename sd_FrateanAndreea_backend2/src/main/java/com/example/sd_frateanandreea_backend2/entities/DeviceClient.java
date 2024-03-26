package com.example.sd_frateanandreea_backend2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class DeviceClient implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_client", columnDefinition = "BINARY(16)")
    private UUID id;

    public DeviceClient(){

    }

    public DeviceClient(UUID id){
        this.id=id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


}
