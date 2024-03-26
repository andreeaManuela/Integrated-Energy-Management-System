package com.example.backendcommunicationmonitoring.dtos.Builders;

import com.example.backendcommunicationmonitoring.dtos.MeasurementDTO;
import com.example.backendcommunicationmonitoring.dtos.MeasurementDetailsDTO;
import com.example.backendcommunicationmonitoring.entities.Measurement;

public class MeasurementBuilder {
    private MeasurementBuilder(){

    }

    public static MeasurementDTO toMeasurementDTO(Measurement measurement){
        return new MeasurementDTO(measurement.getId(), measurement.getTimestamp(), measurement.getDevice_id(), measurement.getMeasurement_value());
    }

    public static Measurement toEntity(MeasurementDetailsDTO measurementDetailsDTO){
        return new Measurement(
                measurementDetailsDTO.getTimestamp(),
                measurementDetailsDTO.getDevice_id(),
                measurementDetailsDTO.getMeasurement_value()
        );
    }


}
