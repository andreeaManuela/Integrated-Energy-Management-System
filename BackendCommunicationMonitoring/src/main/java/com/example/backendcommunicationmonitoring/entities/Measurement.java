package com.example.backendcommunicationmonitoring.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;
@Entity
public class Measurement implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name="timestamp", nullable = false)
    private long timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceIdTable device_id;

    @Column(name="measurement_value")
    private double measurement_value;

    public Measurement(){

    }

    public Measurement(long timestamp, DeviceIdTable device_id, double measurement_value){
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

    @Override
    public String toString() {
        return "Measurement{" +
                "timestamp='" + timestamp + '\'' +
                ", device_id=" + device_id +
                ", measurement_value=" + measurement_value +
                '}';
    }
}
