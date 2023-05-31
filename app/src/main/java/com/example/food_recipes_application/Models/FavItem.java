package com.example.food_recipes_application.Models;

import android.content.Context;

import java.util.ArrayList;

public class FavItem {
    private String key_id;

    private String title;
    private int isLiked;

    @Override
    public String toString() {
        return GsonUtils.convertToJSON(this);
    }

    public FavItem(ArrayList<FavItem> favItem, Context context) {
    }

    public FavItem(String title, String key_id, int isLiked) {
        this.title = title;
        this.key_id = key_id;
        this.isLiked = isLiked;
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

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }
}
