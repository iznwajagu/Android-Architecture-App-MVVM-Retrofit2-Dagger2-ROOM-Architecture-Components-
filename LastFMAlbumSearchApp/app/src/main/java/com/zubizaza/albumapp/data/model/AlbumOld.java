package com.zubizaza.albumapp.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//@Entity(tableName = "albumdb", indices = {@Index(value = {"artist"}, unique = true)})
public class AlbumOld implements  Comparable<AlbumOld>{

   // @PrimaryKey
   // @NonNull
    private String userId;
    private String id;
    private String title;

    public AlbumOld(String userId, String id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int compareTo(AlbumOld album) {
        return this.title.toUpperCase().compareTo(album.getTitle().toUpperCase());
    }

}
