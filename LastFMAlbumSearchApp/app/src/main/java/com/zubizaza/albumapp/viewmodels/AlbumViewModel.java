package com.zubizaza.albumapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zubizaza.albumapp.data.AlbumRepository;
import com.zubizaza.albumapp.data.model.Album;

import java.util.Collections;
import java.util.List;

public class AlbumViewModel extends ViewModel {

    private AlbumRepository albumRepository;

    private MutableLiveData<Album> mAlbum = new MutableLiveData<>();
    private MutableLiveData<List<Album>> mAlbumList = new MutableLiveData<>();
    private MutableLiveData<String> statusMessage = new MutableLiveData<>();
    public MutableLiveData<String> progressBarState = new MutableLiveData<>();


    public AlbumViewModel(AlbumRepository albumRepository){
        this.albumRepository = albumRepository;
        subscribeToNetworkErrors();
    }


    void searchRepositoryForAlbums(String albumName){

       albumRepository.searchForAlbum(albumName).observeForever( albumList ->{
            if(albumList!= null && albumList.size() > 0) {
                Collections.sort(albumList);
                mAlbumList.postValue(albumList);
            }
            progressBarState.postValue("hide");
        });

    }

    void subscribeToSelectedAlbum(String albumId){
        albumRepository.getAlbumById(albumId).observeForever( album ->{
            mAlbum.postValue(album);
            progressBarState.postValue("hide");
        });
    }


    public MutableLiveData<List<Album>> getAlbumList() {
        return mAlbumList;
    }

    public MutableLiveData<Album> getAlbum(String albumId) {
        subscribeToSelectedAlbum(albumId);
        return mAlbum;
    }

    public MutableLiveData<Album> searchForAlbum(String albumName) {
        searchRepositoryForAlbums(albumName);
        return mAlbum;
    }

    public MutableLiveData<String> getStatusMessage() {
        return statusMessage;
    }

    private void subscribeToNetworkErrors(){
        albumRepository.getNetworkErrors().observeForever( error -> {
            statusMessage.postValue(error.toString());
        });
    }




}
