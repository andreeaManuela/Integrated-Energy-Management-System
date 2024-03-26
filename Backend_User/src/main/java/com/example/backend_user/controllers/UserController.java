package com.example.backend_user.controllers;


import com.example.backend_user.auth.AuthenticationResponse;
import com.example.backend_user.auth.AuthenticationService;
import com.example.backend_user.auth.RegisterRequest;
import com.example.backend_user.dtos.UserDTO;
import com.example.backend_user.dtos.UserDetailsDTO;
import com.example.backend_user.services.UserService;
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
@RequestMapping(value = "/security/user")
public class UserController {

    private final UserService personService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService personService, AuthenticationService authenticationService) {
        this.personService = personService;
        this.authenticationService=authenticationService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDTO>> getPersons() {
        List<UserDTO> dtos = personService.findUsers();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UUID> insertProsumer(@Valid @RequestBody RegisterRequest registerRequest) {
        //UUID personID = personService.insert(personDTO);
        AuthenticationResponse response= authenticationService.register(registerRequest);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> getPerson(@PathVariable("id") UUID personId) {
        UserDTO dto = personService.findUserById(personId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") UUID id, @RequestBody UserDetailsDTO userDetailsDTO){
        personService.updateUserName(id, userDetailsDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") UUID id){
        try {
            personService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

