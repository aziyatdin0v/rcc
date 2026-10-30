package io.student.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.student.rococo.data.entity.PaintingEntity;
import io.student.rococo.util.BytesAsString;
import io.student.rococo.util.StringAsBytes;
import jakarta.annotation.Nonnull;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaintingJson(UUID id, String title, String description, String content,
                           MuseumJson museum, ArtistJson artist) {

    public static @Nonnull PaintingJson fromEntity(@Nonnull PaintingEntity entity) {
        return new PaintingJson(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                new BytesAsString(entity.getContent()).string(),
                MuseumJson.fromEntity(entity.getMuseum()),
                ArtistJson.fromEntity(entity.getArtist())
        );
    }

    public @Nonnull PaintingEntity toEntity() {
        PaintingEntity entity = new PaintingEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setContent(new StringAsBytes(content).bytes());
        return entity;
    }
}
