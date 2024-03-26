package com.example.sd_frateanandreea_backend2.dtos;

import java.util.UUID;

public class DeviceClientDTO {

    private UUID id;

    public DeviceClientDTO(){

    }

    public DeviceClientDTO(UUID id){
        this.id=id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


}
