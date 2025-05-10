package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "topics",
        uniqueConstraints = @UniqueConstraint(columnNames = "normalized_name")
)
public class TopicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(unique = true)
    private String name;

    @Column(name = "normalized_name", nullable = false, unique = true)
    private String normalizedName;

    @NotNull
    @Size(max = 250)
    private String description;

    @PrePersist
    @PreUpdate
    private void normalizeName() {
        // trim() enlève les espaces avant/après ; toLowerCase() pour insensibilité à la casse
        this.normalizedName = name == null ? null : name.trim().toLowerCase();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public void setNormalizedName(String normalizedName) {
        this.normalizedName = normalizedName;
    }
}
