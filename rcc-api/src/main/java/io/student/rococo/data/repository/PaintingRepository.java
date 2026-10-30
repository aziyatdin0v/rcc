package io.student.rococo.data.repository;

import io.student.rococo.data.entity.ArtistEntity;
import io.student.rococo.data.entity.PaintingEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaintingRepository extends JpaRepository<PaintingEntity, UUID> {
    @NonNull
    Page<PaintingEntity> findAllByTitleContainsIgnoreCase(String title, Pageable pageable);

    @NonNull
    Page<PaintingEntity> findAllByArtist(ArtistEntity artistEntity, Pageable pageable);
}
