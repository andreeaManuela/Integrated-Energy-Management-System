package com.example.backend_user.controllers;


import com.example.backend_user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/id")
public class UserIdController {

    private final UserService personService;

    @Autowired
    public UserIdController(UserService personService) {
        this.personService = personService;
    }


    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UUID>> getIds() {
        List<UUID> ids = personService.findIds();
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }
}
