package com.example.lab14_2_tabselector_music;

public class Song {
    private String id;
    private String name;
    private boolean isFavorite;

    public Song(String id, String name) {
        this.id = id;
        this.name = name;
        this.isFavorite = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void toggleFavorite() {
        this.isFavorite = !this.isFavorite;
    }
}