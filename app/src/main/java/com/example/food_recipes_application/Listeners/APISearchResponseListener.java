package com.example.food_recipes_application.Listeners;
import  com.example.food_recipes_application.Models.APISearchResponse;

public interface APISearchResponseListener {
    void didFetch (APISearchResponse response, String message);
    void didError (String message);
}
