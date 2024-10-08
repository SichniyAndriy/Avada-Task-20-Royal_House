package edu.avada.course.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter @Setter @NoArgsConstructor // Lombok
@Entity @Table(name = "new_buildings")
public class NewBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_buildings_gen")
    @SequenceGenerator(name = "new_buildings_gen", sequenceName = "new_buildings_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "title")
    private String title;

    @Embedded
    private Location location;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private NewBuildStatus status;

    @Embedded
    private Description description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @PrimaryKeyJoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(name = "panorama_path", length = 1024)
    private String panoramaPath;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Banner.class,
            mappedBy = "newBuilding", orphanRemoval = true)
    private List<Banner> banners;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Infographic.class,
            mappedBy = "newBuilding", orphanRemoval = true)
    private List<Infographic> infographics;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(targetEntity = Unit.class, mappedBy = "newBuilding")
    private List<Unit> units;

    public enum NewBuildStatus {
        OFF,
        ACTIVE
    }

    @Getter @NoArgsConstructor
    @Embeddable
    public static class Location {
        @Column(name = "latitude")
        private Double latitude;

        @Column(name = "longitude")
        private Double longitude;

        private Location(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public static Location of(String latitudeStr, String longitudeStr) {
            double latitude = Double.parseDouble(latitudeStr.replace(',', '.'));
            double longitude = Double.parseDouble(longitudeStr.replace(',', '.'));
            return new Location(latitude, longitude);
        }
    }

    @Getter @Setter @NoArgsConstructor
    @Embeddable
    public static class Description {
        @Column(name="about_desc", length = 1024)
        private String about;

        @Column(name="location_desc", length = 1024)
        private String location;

        @Column(name="infrastructure_desc", length = 1024)
        private String infrastructure;

        @Column(name="flats_desc", length = 1024)
        private String flats;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        NewBuilding that = (NewBuilding) object;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
