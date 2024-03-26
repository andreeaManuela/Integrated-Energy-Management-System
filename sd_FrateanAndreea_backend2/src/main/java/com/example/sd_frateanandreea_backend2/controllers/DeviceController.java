package com.example.sd_frateanandreea_backend2.controllers;

import com.example.sd_frateanandreea_backend2.dtos.DeviceClientDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceDetailsDTO;
import com.example.sd_frateanandreea_backend2.entities.Device;
import com.example.sd_frateanandreea_backend2.entities.DeviceClient;
import com.example.sd_frateanandreea_backend2.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value="/device")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService){
        this.deviceService=deviceService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<DeviceDTO>> getDevices(){
        List<DeviceDTO> dtos= deviceService.findUsers();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @PostMapping(value="/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UUID> insert(@PathVariable("id") UUID id, @RequestBody DeviceDetailsDTO deviceDetailsDTO){
        System.out.println("Received id: " + id);
        System.out.println("Received device details: " + deviceDetailsDTO);

        UUID deviceID= deviceService.insert(id, deviceDetailsDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DeviceDTO> updateUser(@PathVariable("id") UUID id, @RequestBody DeviceDetailsDTO userDetailsDTO){
        deviceService.updateDevice(id, userDetailsDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping(value="/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DeviceDTO> deleteUser(@PathVariable("id") UUID id){
        deviceService.deleteDevice(id);
        return ResponseEntity.ok().build();

    }


}
