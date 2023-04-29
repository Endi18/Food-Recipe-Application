package com.example.food_recipes_application.Listeners;

import com.example.food_recipes_application.Models.APIRecipeInformationResponse;

public interface APIRecipeInformationResponseListener {
    void didFetch (APIRecipeInformationResponse response, String message);
    void didError (String message);
}
