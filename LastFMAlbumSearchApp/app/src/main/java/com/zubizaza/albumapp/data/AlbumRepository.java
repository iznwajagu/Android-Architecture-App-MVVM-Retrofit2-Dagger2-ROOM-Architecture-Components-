package com.zubizaza.albumapp.data;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.zubizaza.albumapp.api.GetAlbumData;
import com.zubizaza.albumapp.api.NetworkApi;
import com.zubizaza.albumapp.api.Results;
import com.zubizaza.albumapp.api.SearchResponse;
import com.zubizaza.albumapp.data.model.Album;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {

    private NetworkApi networkApi;
    private AlbumLocalCache albumLocalCache;
    private MutableLiveData networkErrors = new MutableLiveData<String>();

    @Inject
    public AlbumRepository(@NonNull NetworkApi networkApi, @NonNull AlbumLocalCache albumLocalCache){
        this.networkApi = networkApi;
        this.albumLocalCache = albumLocalCache;
    }

    void searchForAlbumsFromNetwork(String albumName){

        albumLocalCache.deletePreviousData();


        networkApi.fetchAlbums(albumName, DataConstants.API_KEY, DataConstants.FORMAT).enqueue(new Callback<SearchResponse>() {

            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response.isSuccessful()) {
                    SearchResponse searchResponse = response.body();
                    Results results = searchResponse != null ? searchResponse.getResults() : null;
                    GetAlbumData getAlbumData = results != null ?results.getAlbumData() : null;
                    List<Album> albums = getAlbumData != null ? getAlbumData.getAlbums() : null;
                    //Log.d("response", " album list size " + getAlbumData.getAlbums().size());
                    savedFetchedAlbums(albums != null? albums : Collections.EMPTY_LIST);
                }else{
                    networkErrors.postValue(response.errorBody()!=null? response.errorBody() : "Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                networkErrors.postValue(t.getMessage()!=null? t.getMessage() : "Something went wrong");
            }

        });

    }

    private void savedFetchedAlbums(List<Album> albums){
        albumLocalCache.insertNewAlbums(albums);
    }


    public LiveData<List<Album>> searchForAlbum(String albumName) {
        //fetch new set of albums from network
        searchForAlbumsFromNetwork(albumName);

        //subscribe to receive albums from local room database
        return albumLocalCache.fetchAlbumsFromLocalDatabase();
    }

    public LiveData<Album> getAlbumById(String albumId) {

        //subscribe to receive albums from local room database
        return albumLocalCache.getLocalAlbumById(albumId);


    }

    public MutableLiveData getNetworkErrors() {
        return networkErrors;
    }



}
