package io.student.rococo.service.impl;

import io.student.rococo.data.entity.CountryEntity;
import io.student.rococo.data.entity.MuseumEntity;
import io.student.rococo.data.repository.CountryRepository;
import io.student.rococo.data.repository.MuseumRepository;
import io.student.rococo.exception.ResourceNotFoundException;
import io.student.rococo.model.MuseumJson;
import io.student.rococo.service.api.MuseumService;
import io.student.rococo.util.StringAsBytes;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MuseumServiceImpl implements MuseumService {

    private final MuseumRepository museumRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public MuseumServiceImpl(MuseumRepository museumRepository, CountryRepository countryRepository) {
        this.museumRepository = museumRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public MuseumJson getMuseumById(@Nonnull String id) {
        return museumRepository.findById(UUID.fromString(id))
                .map(MuseumJson::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Музей не найден по id: %s", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MuseumJson> getAllMuseums(Pageable pageable) {
        return museumRepository.findAll(pageable)
                .map(MuseumJson::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MuseumJson> searchMuseumByName(@Nonnull String title, Pageable pageable) {
        return museumRepository.findAllByTitleContainsIgnoreCase(title, pageable)
                .map(MuseumJson::fromEntity);
    }

    @Override
    @Transactional()
    public MuseumJson createMuseum(MuseumJson museum) {
        MuseumEntity museumEntity = museum.toEntity();
        museumEntity.setCountry(getCountry(museum));
        return MuseumJson.fromEntity(museumRepository.save(museumEntity));
    }

    @Override
    @Transactional()
    public @Nonnull MuseumJson updateMuseum(@Nonnull MuseumJson museum) {
        MuseumEntity museumEntity = museumRepository.findById(museum.id())
                .orElseThrow(() -> new ResourceNotFoundException("Музей не найден по id: " + museum.id()));
        museumEntity.setTitle(museum.title());
        museumEntity.setCity(museum.geo().city());
        museumEntity.setDescription(museum.description());
        museumEntity.setPhoto(new StringAsBytes(museum.photo()).bytes());
        museumEntity.setCountry(getCountry(museum));
        return MuseumJson.fromEntity(museumRepository.save(museumEntity));
    }

    private CountryEntity getCountry(MuseumJson museum) {
        UUID countryId = museum.geo().country().id();
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Страна не найдена по id: " + countryId));
    }
}