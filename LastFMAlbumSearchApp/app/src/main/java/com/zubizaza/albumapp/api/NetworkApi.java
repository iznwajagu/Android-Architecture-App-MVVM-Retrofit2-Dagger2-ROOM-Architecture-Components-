package com.zubizaza.albumapp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkApi {

    @GET("?method=album.search")
    Call<SearchResponse> fetchAlbums(@Query("album")String albumName, @Query("api_key") String apiKey, @Query("format") String format);

}

