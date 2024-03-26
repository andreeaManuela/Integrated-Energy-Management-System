package com.example.backend_user.repositories;


import com.example.backend_user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByName(String name);

    @Query(value = "SELECT p " +
            "FROM User p " +
            "WHERE p.username = :username " +
            "AND p.password = :password  ")
    Optional<User> findSeniorsByName(@Param("username") String username, @Param("password") String password);



    Optional<User> findByUsername(String username);

}
