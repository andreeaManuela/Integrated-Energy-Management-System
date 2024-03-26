package com.example.backendcommunicationmonitoring.controllers;

import com.example.backendcommunicationmonitoring.dtos.DeviceIdTableDTO;
import com.example.backendcommunicationmonitoring.services.DeviceIdTabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/deviceId_measure")
public class DeviceIdController {
    private final DeviceIdTabelService deviceIdTabelService;

    @Autowired
    public DeviceIdController(DeviceIdTabelService deviceIdTabelService){
        this.deviceIdTabelService=deviceIdTabelService;
    }

    @GetMapping()
   // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<DeviceIdTableDTO>> getDevices(){
        List<DeviceIdTableDTO> devices= deviceIdTabelService.findIds();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PostMapping()
  //  @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UUID> insert(@RequestBody DeviceIdTableDTO deviceIdTableDTO){
        UUID deviceId= deviceIdTabelService.insert(deviceIdTableDTO.getDevice_id(), deviceIdTableDTO.getMax_consump());
        return new ResponseEntity<>(deviceId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DeviceIdTableDTO> updateConsump(@PathVariable UUID id, @RequestBody DeviceIdTableDTO deviceIdTableDTO){
        deviceIdTabelService.updateDevice(id,deviceIdTableDTO.getMax_consump() );
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DeviceIdTableDTO> deleteDeviceId(@PathVariable UUID id){
        deviceIdTabelService.delete(id);
        return ResponseEntity.ok().build();
    }

}
