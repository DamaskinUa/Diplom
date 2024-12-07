package org.example.dipl.repo;

import org.example.dipl.model.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {
    Optional<RoleUser> findByNameRole(String roleName);
}
