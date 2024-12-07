package org.example.dipl.repo;

import org.example.dipl.model.Marker;
import org.example.dipl.model.TitleData;
import org.example.dipl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Long> {
    boolean existsByUserAndTitle(User user, TitleData title);
    Optional<Marker> findByUserAndTitle(User user, TitleData title);
}
