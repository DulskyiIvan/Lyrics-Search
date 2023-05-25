package edu.geekhub.example.service.artist.model;

import java.util.UUID;

public class ArtistDto {

    private UUID id;
    private String name;
    private String description;

    public ArtistDto(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
        this.description = artist.getDescription();
    }

    public UUID getId() {
        return id;
    }

    public ArtistDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ArtistDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ArtistDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
