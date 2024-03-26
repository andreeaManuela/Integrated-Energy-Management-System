package com.example.backendcommunicationmonitoring.dtos;

import com.example.backendcommunicationmonitoring.entities.DeviceIdTable;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

public class MeasurementDTO {
    private UUID id;
    private long timestamp;
    private DeviceIdTable device_id;
    private double measurement_value;

    public MeasurementDTO(){

    }

    public MeasurementDTO(UUID id, long timestamp, DeviceIdTable device_id, double measurement_value){
        this.id=id;
        this.timestamp=timestamp;
        this.device_id=device_id;
        this.measurement_value=measurement_value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public DeviceIdTable getDevice_id() {
        return device_id;
    }

    public void setDevice_id(DeviceIdTable device_id) {
        this.device_id = device_id;
    }

    public double getMeasurement_value() {
        return measurement_value;
    }

    public void setMeasurement_value(double measurement_value) {
        this.measurement_value = measurement_value;
    }
}
