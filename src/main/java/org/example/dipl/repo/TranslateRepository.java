package org.example.dipl.repo;

import org.example.dipl.model.Translate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TranslateRepository extends JpaRepository<Translate, Long> {
    List<Translate> findAllByIdTranslateIn(Set<Long> idTranslate);
}
