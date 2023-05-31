package com.example.food_recipes_application.Models;

import android.content.Context;

import java.util.ArrayList;

public class FavItem {
    private String itm_title;
    private String key_id;
    private int itm_image;

    public FavItem(ArrayList<FavItem> favItem, Context context) {
    }

    public FavItem(String itm_title, String key_id, int itm_image) {
        this.itm_title = itm_title;
        this.key_id = key_id;
        this.itm_image = itm_image;
    }

    public String getItm_title() {
        return itm_title;
    }

    public void setItm_title(String itm_title) {
        this.itm_title = itm_title;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public int getItm_image() {
        return itm_image;
    }

    public void setItm_image(int itm_image) {
        this.itm_image = itm_image;
    }
}
