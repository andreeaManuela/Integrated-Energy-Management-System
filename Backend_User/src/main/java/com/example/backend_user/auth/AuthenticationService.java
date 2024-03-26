package com.example.backend_user.auth;

import com.example.backend_user.config.JwtService;
import com.example.backend_user.dtos.UserDetailsDTO;
import com.example.backend_user.entities.User;
import com.example.backend_user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        var user= User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void updateUser(UUID id, UserDetailsDTO userDetailsDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID:" + id + " not found!"));

        // Setează numele și username-ul dacă sunt furnizate și au text
        if(StringUtils.hasText(userDetailsDTO.getName())){
            user.setName(userDetailsDTO.getName());
        }
        if(StringUtils.hasText(userDetailsDTO.getUsername())){
            user.setUsername(userDetailsDTO.getUsername());
        }

        // Criptarea și setarea parolei noi dacă este furnizată
        if(StringUtils.hasText(userDetailsDTO.getPassword())){
            user.setPassword(passwordEncoder.encode(userDetailsDTO.getPassword()));
        }

        // Actualizează utilizatorul în baza de date
        userRepository.save(user);
    }


}
