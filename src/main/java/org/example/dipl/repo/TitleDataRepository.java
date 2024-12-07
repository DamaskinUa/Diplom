package org.example.dipl.repo;

import org.example.dipl.model.TitleData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TitleDataRepository extends JpaRepository<TitleData, Long>, JpaSpecificationExecutor<TitleData> {
    List<TitleData> findByGenres_IdGenre(Long genreId);
    List<TitleData> findByNameTitleContainingIgnoreCase(String searchQuery);
}


