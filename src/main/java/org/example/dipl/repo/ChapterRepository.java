package org.example.dipl.repo;

import org.example.dipl.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByTitle_IdTitle(Long titleId);
}
