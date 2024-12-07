package org.example.dipl.repo;

import org.example.dipl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginUser(String loginUser);
    Optional<User> findByEmail(String email);
}
