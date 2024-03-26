package com.example.backend_user.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security/controller")
public class Controller {

    @GetMapping
    public ResponseEntity<String> demoSecurity(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }

}
