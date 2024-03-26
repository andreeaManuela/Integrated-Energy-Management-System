package com.example.backendcommunicationmonitoring.controllers;

import com.example.backendcommunicationmonitoring.dtos.MeasurementDTO;
import com.example.backendcommunicationmonitoring.dtos.MeasurementDetailsDTO;
import com.example.backendcommunicationmonitoring.entities.Measurement;
import com.example.backendcommunicationmonitoring.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value="/measurement")
public class MeasurementController {
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService){
        this.measurementService=measurementService;
    }

    @GetMapping()
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MeasurementDTO>> getMeasures(){
        List<MeasurementDTO> dtos= measurementService.getMeasurements();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UUID> insert(@RequestBody Measurement detailsDTO){
        UUID measureID=measurementService.insert(detailsDTO);
        return new ResponseEntity<>(measureID, HttpStatus.CREATED);
    }

    @DeleteMapping(value="/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MeasurementDTO> deleteMeasure(@PathVariable("id") UUID id){
        measurementService.deleteMeasure(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MeasurementDTO> deleteAll(){
        measurementService.deleteAll();
        return ResponseEntity.ok().build();
    }


}
