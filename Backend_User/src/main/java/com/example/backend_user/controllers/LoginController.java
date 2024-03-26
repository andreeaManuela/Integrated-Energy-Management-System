package com.example.backend_user.controllers;

import com.example.backend_user.auth.AuthenticationRequest;
import com.example.backend_user.auth.AuthenticationResponse;
import com.example.backend_user.auth.AuthenticationService;
import com.example.backend_user.config.JwtService;
import com.example.backend_user.dtos.UserDTO;
import com.example.backend_user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value="/security/login")
@RequiredArgsConstructor
public class LoginController {

   // private AuthenticationService service;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping()
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO userDTO){
        try {
            // Autentificați utilizatorul
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );

            // Dacă autentificarea este reușită, generează un token JWT
            final UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            final String jwt = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(jwt)); // Trimite tokenul ca răspuns
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credențiale incorecte");
        }
    }

//    @PostMapping()
//    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
//        return ResponseEntity.ok(service.authenticate(request));
//    }

}

