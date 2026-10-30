package io.student.rococo.service.api;

import io.student.rococo.model.ArtistJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtistService {

    ArtistJson getArtistById(String id);

    Page<ArtistJson> searchArtistByName(String name, Pageable pageable);

    Page<ArtistJson> getAllArtist(Pageable pageable);

    ArtistJson createArtist(ArtistJson artist);

    ArtistJson updateArtist(ArtistJson artist);
}
