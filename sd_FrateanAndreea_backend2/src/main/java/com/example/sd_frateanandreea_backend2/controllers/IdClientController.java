package com.example.sd_frateanandreea_backend2.controllers;

import com.example.sd_frateanandreea_backend2.dtos.DeviceClientDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceClientDetailsDTO;
import com.example.sd_frateanandreea_backend2.dtos.DeviceDTO;
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
//TABELUL DEVICE
@RestController
@CrossOrigin
@RequestMapping(value="/device_id")
public class IdClientController {
    private final DeviceService deviceService;

    @Autowired
    public IdClientController(DeviceService deviceService){
        this.deviceService=deviceService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<DeviceDTO>> getIds() {
        List<UUID> ids = deviceService.findDeviceIds();
        return new ResponseEntity(ids, HttpStatus.OK);
    }


}
