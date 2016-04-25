package com.android.yaschenkodanil.yandexsummerapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danil on 25.04.16.
 */
public class Artist {
    private long id ;
    private String name = "";
    private long tracks;
    private long albums;
    private String link = "";
    private String description = "";
    private List<String> genres;
    private Cover cover;


    public Artist() {
        List<String> genres = new ArrayList<>();
        this.cover = new Cover();
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }
    /*
    Здесь можно юзать stream`ы, но, так как 8 джаву не завезли и у меня нет времени искать либу пишем это
     */
    public String getGenres() {
        StringBuilder concatination = new StringBuilder();
        for(String s: genres) {
            concatination.append(s);
        }
        return concatination.toString();
    }

    public String getInfo() {
        return String.valueOf(albums) + " альбомов, " + String.valueOf(tracks) + " песен"  ;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTracks(long tracks) {
        this.tracks = tracks;
    }

    public void setAlbums(long albums) {
        this.albums = albums;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return this.id;
    }

    public long getTracks() {
        return this.tracks;
    }

    public long getAlbums() {
        return this.albums;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
