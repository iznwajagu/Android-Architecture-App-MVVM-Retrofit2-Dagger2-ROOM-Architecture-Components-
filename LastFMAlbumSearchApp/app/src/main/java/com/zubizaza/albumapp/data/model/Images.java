package com.zubizaza.albumapp.data.model;

public class Images {

    private String text;
    private String size;


    public Images(String text, String size) {
        this.text = text;
        this.size = size;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }





}
