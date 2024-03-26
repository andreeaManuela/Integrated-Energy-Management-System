package com.example.backendcommunicationmonitoring.services;

import com.example.backendcommunicationmonitoring.dtos.Builders.MeasurementBuilder;
import com.example.backendcommunicationmonitoring.dtos.MeasurementDTO;
import com.example.backendcommunicationmonitoring.dtos.MeasurementDetailsDTO;
import com.example.backendcommunicationmonitoring.entities.DeviceIdTable;
import com.example.backendcommunicationmonitoring.entities.Measurement;
import com.example.backendcommunicationmonitoring.repositories.DeviceIdRepository;
import com.example.backendcommunicationmonitoring.repositories.MeasurementRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class MeasurementService {
    private static final Logger LOGGER= LoggerFactory.getLogger(MeasurementService.class);
    private final MeasurementRepository measurementRepository;
    private final DeviceIdRepository deviceIdRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, DeviceIdRepository deviceIdRepository, SimpMessagingTemplate simpMessagingTemplate ){
        this.measurementRepository=measurementRepository;
        this.deviceIdRepository=deviceIdRepository;
        this.simpMessagingTemplate=simpMessagingTemplate;
    }

    public List<MeasurementDTO> getMeasurements(){
        List<Measurement> list= measurementRepository.findAll();
        return list.stream()
                .map(MeasurementBuilder::toMeasurementDTO)
                .collect(Collectors.toList());
    }

    public UUID insert( Measurement measurementDetailsDTO){
        Measurement savedMeasure= measurementRepository.save(measurementDetailsDTO);
        return savedMeasure.getId();

    }


    public double getLimitaDeviceId(UUID device_id){
        return measurementRepository.getMaxConsump(device_id);
    }

    public void notify(UUID deviceId, double suma, double limita){
        //String notificationMessage = String.format("Atenție: Suma consumului a depășit pragul permis.");

       // simpMessagingTemplate.convertAndSend("/topic/notifications", notificationMessage);
        String notificationMessage = String.format(
                "{\"message\": \"Atenție: Suma consumului pentru dispozitivul %s a depășit pragul permis. Suma: %f, Limita: %f\"}",
                deviceId.toString(), suma, limita);

        simpMessagingTemplate.convertAndSend("/topic/notifications", notificationMessage);
    }

    public void deleteMeasure(UUID id){
        measurementRepository.deleteById(id);
    }

    public void deleteAll(){
        measurementRepository.deleteAll();
    }




}
