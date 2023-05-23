package com.example.food_recipes_application.Listeners;

import com.example.food_recipes_application.Models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String message);
    void didError(String message);
}
