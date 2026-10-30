package io.student.rococo.service.impl;

import io.student.rococo.data.entity.ArtistEntity;
import io.student.rococo.data.entity.MuseumEntity;
import io.student.rococo.data.entity.PaintingEntity;
import io.student.rococo.data.repository.ArtistRepository;
import io.student.rococo.data.repository.MuseumRepository;
import io.student.rococo.data.repository.PaintingRepository;
import io.student.rococo.exception.ResourceNotFoundException;
import io.student.rococo.model.PaintingJson;
import io.student.rococo.service.api.PaintingService;
import io.student.rococo.util.StringAsBytes;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class PaintingImpl implements PaintingService {

    private final PaintingRepository paintingRepository;
    private final MuseumRepository museumRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public PaintingImpl(PaintingRepository paintingRepository, MuseumRepository museumRepository,
                        ArtistRepository artistRepository) {
        this.paintingRepository = paintingRepository;
        this.museumRepository = museumRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PaintingJson getPaintingById(@Nonnull String id) {
        return paintingRepository.findById(UUID.fromString(id))
                .map(PaintingJson::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Картина не найдена по id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaintingJson> findPaintingByTitle(@Nonnull String title, Pageable pageable) {
        return paintingRepository.findAllByTitleContainsIgnoreCase(title, pageable)
                .map(PaintingJson::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaintingJson> findPaintingByAuthorId(String id, Pageable pageable) {
        return paintingRepository.findAllByArtist(
                        artistRepository.findById(UUID.fromString(id))
                                .orElseThrow(() -> new ResourceNotFoundException("Художник не найден по id: " + id)), pageable)
                .map(PaintingJson::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaintingJson> getAllPaintings(Pageable pageable) {
        return paintingRepository.findAll(pageable)
                .map(PaintingJson::fromEntity);
    }

    @Override
    @Transactional
    public @Nonnull PaintingJson updatePainting(@Nonnull PaintingJson painting) {
        PaintingEntity paintingEntity = getPainting(painting.id());
        paintingEntity.setTitle(painting.title());
        paintingEntity.setDescription(painting.description());
        paintingEntity.setContent(new StringAsBytes(painting.content()).bytes());

        if (painting.museum() != null) {
            final UUID museumIdFromJson = painting.museum().id();
            MuseumEntity museumEntity = museumRepository.findById(museumIdFromJson)
                    .orElseThrow(() -> new ResourceNotFoundException("Музей не найден по id: " + museumIdFromJson));
            museumEntity.addPaintings(paintingEntity);

        }
        if (painting.artist() != null) {
            final UUID artistIdFromJson = painting.artist().id();
            ArtistEntity artistEntity = artistRepository.findById(artistIdFromJson)
                    .orElseThrow(() -> new ResourceNotFoundException("Художник не найден по id: " + artistIdFromJson));
            artistEntity.addPaintings(paintingEntity);

        }
        return PaintingJson.fromEntity(paintingRepository.save(paintingEntity));
    }

    @Override
    @Transactional
    public PaintingJson createPainting(PaintingJson painting) {
        PaintingEntity paintingEntity = painting.toEntity();
        paintingEntity.setArtist(artistRepository.findById(painting.artist().id())
                .orElseThrow(() -> new ResourceNotFoundException("Музей не найден по id: " + painting.artist().id())));
        if (painting.museum() != null && painting.museum().id() != null) {
            paintingEntity.setMuseum(museumRepository.findById(painting.museum().id())
                    .orElseThrow(() -> new ResourceNotFoundException("Художник не найден по id: " + painting.museum().id())));
        }
        return PaintingJson.fromEntity(paintingRepository.save(paintingEntity));
    }

    private @Nonnull PaintingEntity getPainting(@Nonnull UUID id) {
        return paintingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Картина не найдена по id: " + id)
        );
    }
}
