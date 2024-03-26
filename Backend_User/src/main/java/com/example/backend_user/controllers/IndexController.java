package com.example.backend_user.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class IndexController {

    @GetMapping(value = "/")
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>("Energy Management APP Service is running...", HttpStatus.OK);
    }
}
