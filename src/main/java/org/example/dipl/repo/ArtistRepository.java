package org.example.dipl.repo;

import org.example.dipl.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findAllByIdArtistIn(Set<Long> idArtist);
}
