package dev.pe.app.controllers.auth;

import dev.pe.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepo extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String email);
}
