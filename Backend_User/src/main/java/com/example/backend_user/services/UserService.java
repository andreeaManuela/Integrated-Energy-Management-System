package com.example.backend_user.services;

import com.example.backend_user.dtos.Builders.UserBuilder;
import com.example.backend_user.dtos.UserDTO;
import com.example.backend_user.dtos.UserDetailsDTO;
import com.example.backend_user.entities.User;
import com.example.backend_user.repositories.AdminRepository;
import com.example.backend_user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder ) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public List<UserDTO> findUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public List<UUID> findIds() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    public UserDTO findByUsernamAndPassword(UserDTO userDTO){
        Optional<User> userOptional= userRepository.findSeniorsByName(userDTO.getUsername(), userDTO.getPassword());
        if(!userOptional.isPresent()){
            LOGGER.error("User with this name was not found!");
            throw new ResourceNotFoundException(User.class.getSimpleName());

        }
        return UserBuilder.toUserDTO(userOptional.get());

    }

    public UserDTO findUserById(UUID id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDTO(prosumerOptional.get());
    }

    public String findRole(UUID id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return prosumerOptional.get().getRole();
    }

    public UUID insert(UserDetailsDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user = userRepository.save(user);
        LOGGER.debug("User with id {} was inserted in db", user.getId());
        return user.getId();
    }

    public void updateUserName(UUID id, UserDetailsDTO userDetailsDTO){
        Optional<User> optionalUser = userRepository.findById(id);
        //verific daca exista utilizatorul in baza de date
        if(!optionalUser.isPresent()){
            throw new EntityNotFoundException("User with ID:" + id + " not found!");
        }
        User user = optionalUser.get();
        if(StringUtils.hasText(userDetailsDTO.getName())){
            user.setName(userDetailsDTO.getName());
        }
        if(StringUtils.hasText(userDetailsDTO.getUsername())){
            user.setUsername(userDetailsDTO.getUsername());
        }
        if(StringUtils.hasText(userDetailsDTO.getPassword())){
            //user.setPassword(userDetailsDTO.getPassword());
            user.setPassword(passwordEncoder.encode(userDetailsDTO.getPassword()));
        }
        //salvez userul si il actualizez in baza de date
        User update= userRepository.save(user);
    }

    public void delete(UUID id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()){
            throw new EntityNotFoundException("User with ID:" + id + " not found!");
        }
        userRepository.deleteById(id);

    }

}
