package io.student.rococo.service.impl;


import io.student.rococo.data.entity.ArtistEntity;
import io.student.rococo.data.repository.ArtistRepository;
import io.student.rococo.exception.ResourceNotFoundException;
import io.student.rococo.model.ArtistJson;
import io.student.rococo.service.api.ArtistService;
import io.student.rococo.util.StringAsBytes;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistJson> getAllArtist(Pageable pageable) {
        return artistRepository.findAll(pageable)
                .map(ArtistJson::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistJson getArtistById(@Nonnull String id) {
        return artistRepository.findById(UUID.fromString(id))
                .map(ArtistJson::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Художник не найден по id: " + id));

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistJson> searchArtistByName(@Nonnull String name, Pageable pageable) {
        return artistRepository.findAllByNameContainsIgnoreCase(name, pageable)
                .map(ArtistJson::fromEntity);
    }

    @Override
    @Transactional
    public ArtistJson createArtist(ArtistJson artist) {
        return ArtistJson.fromEntity(artistRepository.save(artist.toEntity()));
    }

    @Override
    @Transactional
    public @Nonnull ArtistJson updateArtist(@Nonnull ArtistJson artist) {
        ArtistEntity artistEntity = artistRepository.findById(artist.id())
                .orElseThrow(() -> new ResourceNotFoundException("Художник не найден по id: " + artist.id()));
        artistEntity.setName(artist.name());
        artistEntity.setBiography(artist.biography());
        artistEntity.setPhoto(new StringAsBytes(artist.photo()).bytes());
        return ArtistJson.fromEntity(artistRepository.save(artistEntity));
    }

}