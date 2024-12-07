package org.example.dipl.repo;

import org.example.dipl.model.Screenwriter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ScreenwriterRepository extends JpaRepository<Screenwriter, Long> {
    List<Screenwriter> findAllByIdScreenwriterIn(Set<Long> idScreenwriter);
}
