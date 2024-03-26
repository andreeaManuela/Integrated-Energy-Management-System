package com.example.sd_frateanandreea_backend2.services;

import com.example.sd_frateanandreea_backend2.dtos.Builders.DeviceBuilder;
import com.example.sd_frateanandreea_backend2.dtos.Builders.DeviceClientBuilder;
import com.example.sd_frateanandreea_backend2.dtos.DeviceClientDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceClientDetailsDTO;
import com.example.sd_frateanandreea_backend2.entities.Device;
import com.example.sd_frateanandreea_backend2.entities.DeviceClient;
import com.example.sd_frateanandreea_backend2.repositories.DeviceRepository;
import com.example.sd_frateanandreea_backend2.repositories.IdClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

//Tabelul ID_CLIENT
@Service
public class IdClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdClientService.class);
    private final IdClientRepository idClientRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public IdClientService(IdClientRepository idClientRepository, DeviceRepository deviceRepository)
    {
        this.deviceRepository=deviceRepository;
        this.idClientRepository=idClientRepository;
    }

    public List<DeviceClientDTO> findIds(){
        List<DeviceClient> idList = idClientRepository.findAll();
        return idList.stream()
                .map(DeviceClientBuilder::toDeviceClientDTO)
                .collect(Collectors.toList());
    }

    public UUID insert(UUID id){
        DeviceClient user= new DeviceClient(id);
        user.setId(id);
        user= idClientRepository.save(user);
        return user.getId();
    }

    public void delete(UUID id){
        deviceRepository.deleteDevicesById_client(id);
        Optional<DeviceClient> optionalDeviceClient = idClientRepository.findById(id);
        if(optionalDeviceClient.isPresent()){
            idClientRepository.deleteById(id);
        }

    }
}
