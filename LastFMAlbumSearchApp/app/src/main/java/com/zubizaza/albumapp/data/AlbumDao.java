package com.zubizaza.albumapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zubizaza.albumapp.data.model.Album;

import java.util.List;

@Dao
public interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Album> album);

    @Query("SELECT mbid, artist, name, url, image, streamable FROM albumdb")
    LiveData<List<Album>> fetchAlbums();

    @Query("SELECT mbid, artist, name, url, image, streamable FROM albumdb WHERE mbid= :albumId")
    LiveData<Album> getAlbumById(String albumId);

    @Query("DELETE FROM albumdb")
    void deleteAll();

}
