package com.example.backendcommunicationmonitoring.dtos.Builders;

import com.example.backendcommunicationmonitoring.dtos.DeviceIdTableDTO;
import com.example.backendcommunicationmonitoring.dtos.DeviceIdTableDetailsDTO;
import com.example.backendcommunicationmonitoring.entities.DeviceIdTable;

public class DeviceIdTableBuilder {

    private DeviceIdTableBuilder(){

    }


    public static DeviceIdTableDTO toDeviceIdTable(DeviceIdTable deviceIdTable){
        return new DeviceIdTableDTO(deviceIdTable.getId_device(), deviceIdTable.getMax_consump());
    }

    public static DeviceIdTable toEntity(DeviceIdTableDetailsDTO deviceIdTableDetailsDTO){
        return new DeviceIdTable(deviceIdTableDetailsDTO.getDevice_id(),deviceIdTableDetailsDTO.getMax_consump());
    }


}
