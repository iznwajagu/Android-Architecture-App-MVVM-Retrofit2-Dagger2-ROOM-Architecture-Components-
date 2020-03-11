package com.zubizaza.albumapp.data;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.zubizaza.albumapp.data.model.Album;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class AlbumLocalCache {

    private AlbumDao albumDao;
    private ExecutorService iOExecutor;

    @Inject
    public AlbumLocalCache(@NonNull AlbumDao albumDao, @NonNull ExecutorService iOExecutor) {
        this.albumDao = albumDao;
        this.iOExecutor = iOExecutor;
    }


    void insertNewAlbums(List<Album> albums){
        iOExecutor.execute(() -> {
            albumDao.insert(albums);
        });
    }

    void  deletePreviousData(){
        iOExecutor.execute(() -> {
            albumDao.deleteAll();
        });
    }

    LiveData<List<Album>> fetchAlbumsFromLocalDatabase(){
        return albumDao.fetchAlbums();
    }

    LiveData<Album> getLocalAlbumById(String Id){
        return albumDao.getAlbumById(Id);
    }

}
