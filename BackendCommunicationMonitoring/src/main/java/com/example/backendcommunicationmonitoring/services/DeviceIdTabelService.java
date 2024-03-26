package com.example.backendcommunicationmonitoring.services;

import com.example.backendcommunicationmonitoring.dtos.Builders.DeviceIdTableBuilder;
import com.example.backendcommunicationmonitoring.dtos.DeviceIdTableDTO;
import com.example.backendcommunicationmonitoring.dtos.DeviceIdTableDetailsDTO;
import com.example.backendcommunicationmonitoring.entities.DeviceIdTable;
import com.example.backendcommunicationmonitoring.repositories.DeviceIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceIdTabelService {
    private static final Logger LOGGER= LoggerFactory.getLogger(DeviceIdTabelService.class);
    private final DeviceIdRepository deviceIdRepository;

    public DeviceIdTabelService(DeviceIdRepository deviceIdRepository){
        this.deviceIdRepository=deviceIdRepository;
    }

    public List<DeviceIdTableDTO> findIds(){
        List<DeviceIdTable> list= deviceIdRepository.findAll();
        return list.stream()
                .map(DeviceIdTableBuilder::toDeviceIdTable)
                .collect(Collectors.toList());
    }

    public UUID insert(UUID idDevice, String max_consump){
        DeviceIdTable newdevice=new DeviceIdTable(idDevice, max_consump);
        newdevice.setId_device(idDevice);
        newdevice.setMax_consump(max_consump);
        newdevice= deviceIdRepository.save(newdevice);
        return newdevice.getId_device();
    }

    public void updateDevice(UUID idDevice, String max_consump){
        Optional<DeviceIdTable> deviceIdTableOptional= deviceIdRepository.findById(idDevice);
        if(deviceIdTableOptional.isPresent()){
            DeviceIdTable existingDevice= deviceIdTableOptional.get();
            existingDevice.setMax_consump(max_consump);
            deviceIdRepository.save(existingDevice);
            LOGGER.info("Device updated with ID: {}", existingDevice.getId_device());
        }else {
            LOGGER.error("Device not found with ID: {}", idDevice);
        }
    }

    public void delete(UUID id){
        Optional<DeviceIdTable> deviceIdTableOptional= deviceIdRepository.findById(id);
        if(deviceIdTableOptional.isPresent()){
            deviceIdRepository.deleteById(id);
            LOGGER.info("Device deleted with ID: {}", id);
        } else {
            LOGGER.error("Device not found with ID: {}", id);
        }
    }


}
