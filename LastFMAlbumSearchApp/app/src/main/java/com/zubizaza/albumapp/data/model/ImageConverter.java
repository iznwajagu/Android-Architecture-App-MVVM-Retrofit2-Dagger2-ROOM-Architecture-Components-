package com.zubizaza.albumapp.data.model;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ImageConverter {

    static private Gson gson = new Gson();


    @TypeConverter
    public static List<Images> storedStringToImage(String data){

        if(data == null){
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Images>>(){}.getType();
        return gson.fromJson(data, listType);


    }

    @TypeConverter
    public static String imageToStoredString(List<Images> images){
        //Gson gson = new Gson();
        return gson.toJson(images);
    }





}
