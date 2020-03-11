package com.zubizaza.albumapp.api;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Results implements Serializable {

    @SerializedName("albummatches")
    private GetAlbumData albumData;


    public Results(){
        this.albumData = new GetAlbumData();
    }


    public GetAlbumData getAlbumData(){
        return albumData;
    }



}
