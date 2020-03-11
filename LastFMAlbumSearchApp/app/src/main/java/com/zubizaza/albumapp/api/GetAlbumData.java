package com.zubizaza.albumapp.api;

import com.google.gson.annotations.SerializedName;
import com.zubizaza.albumapp.data.model.Album;

import java.io.Serializable;
import java.util.ArrayList;

public class GetAlbumData implements Serializable {

    @SerializedName("album")
    private ArrayList<Album> albums;


    public GetAlbumData(){
        this.albums = new ArrayList<>();
    }


    public ArrayList<Album> getAlbums(){
        return albums;
    }



}
