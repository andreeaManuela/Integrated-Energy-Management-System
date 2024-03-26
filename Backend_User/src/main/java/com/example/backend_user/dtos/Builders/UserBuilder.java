package com.example.backend_user.dtos.Builders;


import com.example.backend_user.dtos.UserDTO;
import com.example.backend_user.dtos.UserDetailsDTO;
import com.example.backend_user.entities.User;

public class UserBuilder {

    private UserBuilder() {
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public static UserDetailsDTO toUserDetailsDTO(User user) {
        return new UserDetailsDTO(user.getId(), user.getName(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public static User toEntity(UserDetailsDTO userDetailsDTO) {
        return new User( userDetailsDTO.getName(),
                userDetailsDTO.getUsername(),
                userDetailsDTO.getPassword(),
                userDetailsDTO.getRole());
    }
}
