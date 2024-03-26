package com.example.sd_frateanandreea_backend2.services;

import com.example.sd_frateanandreea_backend2.RabbitMQ.DeviceEventListener;
import com.example.sd_frateanandreea_backend2.dtos.Builders.DeviceBuilder;
import com.example.sd_frateanandreea_backend2.dtos.DeviceClientDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceDetailsDTO;
import com.example.sd_frateanandreea_backend2.entities.Device;
import com.example.sd_frateanandreea_backend2.entities.DeviceClient;
import com.example.sd_frateanandreea_backend2.repositories.DeviceRepository;
import com.example.sd_frateanandreea_backend2.repositories.IdClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
//TABELUL DEVICE
@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository userRepository;
    private final IdClientRepository idClientRepository;
    private DeviceEventListener deviceEventListener;

    @Autowired
    public DeviceService(DeviceRepository userRepository, IdClientRepository idClientRepository, DeviceEventListener deviceEventListener) {
        this.userRepository = userRepository;
        this.idClientRepository=idClientRepository;
        this.deviceEventListener=deviceEventListener;
    }

    public List<DeviceDTO> findUsers() {
        List<Device> userList = userRepository.findAll();
        return userList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public List<UUID> findDeviceIds(){
        List<Device> deviceList= userRepository.findAll();
        return deviceList.stream()
                .map(Device::getId)
                .collect(Collectors.toList());
    }

    public List<Device> findDeviceByIdClient(UUID idClient) {
        List<Device> lista= userRepository.findDeviceByIdClient(idClient);
        return lista;

    }

    public UUID insert(UUID idUser, DeviceDetailsDTO deviceDTO) {
        DeviceClient deviceClient;
        Optional<DeviceClient> optionalDeviceClient = idClientRepository.findById(idUser);
        System.out.println("IdUser din Service:  " + idUser);
        System.out.println("DeviceDetails din Service: " + deviceDTO);

        if (optionalDeviceClient.isPresent()) {
            deviceClient = optionalDeviceClient.get();
            System.out.println("Device Client :  " + deviceClient);

        } else {
            throw new RuntimeException("Nu s-a găsit DeviceClient cu ID-ul: " + deviceDTO.getId_client().getId());
        }

        Device device= new Device(deviceDTO.getDescription(), deviceDTO.getAddress(), deviceDTO.getMax_consump(), deviceClient);

        Device devicesaved= userRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        System.out.println(device);
        deviceEventListener.sendMessageInserted("inserted", devicesaved.getId(), devicesaved.getMax_consump());
        return devicesaved.getId();

    }

    public void updateDevice(UUID id, DeviceDetailsDTO userDetailsDTO){
        Optional<Device> optionalUser = userRepository.findById(id);
        //verific daca exista utilizatorul in baza de date
        if(!optionalUser.isPresent()){
            throw new EntityNotFoundException("Device with ID:" + id + " not found!");
        }
        Device user = optionalUser.get();
        if(StringUtils.hasText(userDetailsDTO.getDescription())){
            user.setDescription(userDetailsDTO.getDescription());
        }
        if(StringUtils.hasText(userDetailsDTO.getAddress())){
            user.setAddress(userDetailsDTO.getAddress());
        }
        if(StringUtils.hasText(userDetailsDTO.getMax_consump())){
            user.setMax_consump(userDetailsDTO.getMax_consump());
            String max_consump=userDetailsDTO.getMax_consump();
            deviceEventListener.sendMessageUpdate("update", id, max_consump);
        }
        //salvez userul si il actualizez in baza de date
        Device update= userRepository.save(user);
    }

    public void deleteIdClientDevice(UUID id){
        userRepository.deleteDevicesById_client(id);
        Optional<DeviceClient> optionalDeviceClient = idClientRepository.findById(id);
        if(optionalDeviceClient.isPresent()){
            idClientRepository.deleteById(id);
        }

    }

    public void deleteDevice(UUID id){
        deviceEventListener.sendMessageDelete("deleted", id);
        userRepository.deleteById(id);
    }
}
