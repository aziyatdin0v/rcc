package io.student.rococo.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "artist")
public class ArtistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, length = 2000)
    private String biography;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    @OneToMany(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "artist")
    private Set<PaintingEntity> paintings = new HashSet<>();

    public void addPaintings(PaintingEntity... paintings) {
        for (PaintingEntity painting : paintings) {
            this.paintings.add(painting);
            painting.setArtist(this);
        }
    }

    public void removePaintings(PaintingEntity painting) {
        this.paintings.remove(painting);
        painting.setArtist(null);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass():o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass():this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ArtistEntity that = (ArtistEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode():getClass().hashCode();
    }
}
