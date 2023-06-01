package com.example.food_recipes_application.Models;

import android.content.Context;

import java.util.ArrayList;

public class FavItem {
    private String key_id;
    private String title;
    private int image;
    private int isLiked;


    public FavItem(ArrayList<FavItem> favItem, Context context) {
    }

    public FavItem(String title, String key_id, int image) {
        this.title = title;
        this.key_id = key_id;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String itm_title) {
        this.title = itm_title;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }
}
