package com.zubizaza.albumapp.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchResponse implements Serializable {

    @SerializedName("results")
    private Results results;

    public SearchResponse(){
        this.results = new Results();
    }

    public Results getResults(){
        return results;
    }


}
