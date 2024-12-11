package org.example.dipl.repo;

import org.example.dipl.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {
    List<Page> findByChapter_IdChapter(Long chapterId);
    void deleteAllByChapter_IdChapter(Long chapterId);
}
