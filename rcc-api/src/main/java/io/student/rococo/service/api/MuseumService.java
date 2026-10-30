package io.student.rococo.service.api;

import io.student.rococo.model.MuseumJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MuseumService {
    MuseumJson getMuseumById(String id);

    Page<MuseumJson> getAllMuseums(Pageable pageable);

    Page<MuseumJson> searchMuseumByName(String title, Pageable pageable);

    MuseumJson createMuseum(MuseumJson museum);

    MuseumJson updateMuseum(MuseumJson museum);
}
