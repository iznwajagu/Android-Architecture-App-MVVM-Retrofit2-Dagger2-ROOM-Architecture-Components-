package com.zubizaza.albumapp.data.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(tableName = "albumdb", indices = {@Index(value = {"mbid"}, unique = true)})
public class Album implements  Comparable<Album>{

    @PrimaryKey
    @NonNull
    private String mbid;
    private String artist;
    private String name;
    private String url;
    @TypeConverters(ImageConverter.class)
    private List<Images> image;
    private String streamable;

    public Album(String mbid, String artist, String name, String url, List<Images> image) {
        this.mbid = mbid;
        this.artist = artist;
        this.name = name;
        this.url = url;
        this.image = image;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Images> getImage() {
        return image;
    }

    public void setImage(List<Images> image) {
        this.image = image;
    }

    public String getStreamable() {
        return streamable;
    }

    public void setStreamable(String streamable) {
        this.streamable = streamable;
    }



    @Override
    public int compareTo(Album album) {
        return this.name.toUpperCase().compareTo(album.getName().toUpperCase());
    }

}
