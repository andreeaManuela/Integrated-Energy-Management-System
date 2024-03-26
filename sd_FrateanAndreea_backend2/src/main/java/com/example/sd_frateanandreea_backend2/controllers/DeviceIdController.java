package com.example.sd_frateanandreea_backend2.controllers;

import com.example.sd_frateanandreea_backend2.dtos.DeviceClientDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceClientDetailsDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceDetailsDTO;
import com.example.sd_frateanandreea_backend2.entities.Device;
import com.example.sd_frateanandreea_backend2.services.DeviceService;
import com.example.sd_frateanandreea_backend2.services.IdClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

//TABELUL Device Client
@RestController
@CrossOrigin
@RequestMapping(value="/client_id")
public class DeviceIdController {
    private final IdClientService deviceService;
    private final DeviceService device;

    @Autowired
    public DeviceIdController(IdClientService deviceService, DeviceService device){
        this.device=device;
        this.deviceService=deviceService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<DeviceClientDTO>> getIds() {
        List<DeviceClientDTO> ids = deviceService.findIds();
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UUID> insert(@PathVariable UUID id){
        UUID deviceID= deviceService.insert(id);
        return new ResponseEntity<>(deviceID, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<List<Device>> getDevicesById(@PathVariable UUID id){
        List<Device> dtos= device.findDeviceByIdClient(id);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DeviceDTO> deleteUser(@PathVariable("id") UUID id){
        deviceService.delete(id);
        return ResponseEntity.ok().build();
    }
}
