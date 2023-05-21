package com.example.food_recipes_application.Listeners;

import com.example.food_recipes_application.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch (RecipeDetailsResponse response, String message);
    void didError (String message);
}
