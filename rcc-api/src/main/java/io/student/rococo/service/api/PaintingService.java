package io.student.rococo.service.api;

import io.student.rococo.model.PaintingJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaintingService {

    PaintingJson getPaintingById(String id);

    Page<PaintingJson> findPaintingByTitle(String title, Pageable pageable);

    Page<PaintingJson> findPaintingByAuthorId(String id, Pageable pageable);

    Page<PaintingJson> getAllPaintings(Pageable pageable);

    PaintingJson createPainting(PaintingJson painting);

    PaintingJson updatePainting(PaintingJson painting);
}
