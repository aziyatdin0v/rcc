package io.student.rococo.data.repository;

import io.student.rococo.data.entity.ArtistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtistRepository extends JpaRepository<ArtistEntity, UUID> {
    Page<ArtistEntity> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
}
