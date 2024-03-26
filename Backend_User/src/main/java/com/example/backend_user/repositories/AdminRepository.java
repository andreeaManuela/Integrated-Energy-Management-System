package com.example.backend_user.repositories;

import com.example.backend_user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<User, UUID> {
    @Query(value = "DELETE FROM User p " +
            "WHERE p.id = :id "  )
    Optional<User> deleteUser(@Param("id") UUID id);

}
