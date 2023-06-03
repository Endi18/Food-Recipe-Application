package com.example.food_recipes_application;

import static com.example.food_recipes_application.R.id.textViewF;
import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


//public class Favorites_Activity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_favorites);
//    }
//
//    public class FavoriteActivity extends AppCompatActivity {
//        private RecyclerView favoriteRecyclerView;
//        private RecipeAdapter recipeAdapter;
//        private List<Recipe> favoriteRecipes;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_favorites);
//
//            favoriteRecyclerView = findViewById(R.id.favoriteRecyclerView);
//            favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//            favoriteRecipes = getFavoriteRecipes(); // Replace with your own method to fetch favorite recipes
//
//            recipeAdapter = new RecipeAdapter(favoriteRecipes); // Create your own adapter class
//            favoriteRecyclerView.setAdapter(recipeAdapter);
//        }
//
//        // Replace this method with your own implementation to fetch favorite recipes
//        private List<Recipe> getFavoriteRecipes() {
//            // Fetch favorite recipes from a database or any other data source
//            // Return the list of favorite recipes
//            return new ArrayList<>(); // Return an empty list for now
//        }
//    }

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.example.food_recipes_application.Adapters.FavoriteAdapter;
import com.example.food_recipes_application.Database.MyDatabaseHelper;
import com.example.food_recipes_application.Listeners.RecipeClickListener;
import com.example.food_recipes_application.Models.FavItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class FavoritesActivity extends AppCompatActivity {





    private MyDatabaseHelper helper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.favorite_activity);

        helper = new MyDatabaseHelper(this);
        setContentView(R.layout.fav_fragment_recycler);
        RecyclerView recyclerView=findViewById(R.id.fav_recyclerView);
        recyclerView.setAdapter(new FavoriteAdapter(this, helper.getAllRecipes(), new RecipeClickListener() {
            @Override
            public void onRecipeClicked(String id) {

                startActivity(new Intent(FavoritesActivity.this, RecipeDetailsActivity.class)
                        .putExtra("id", id).putExtra("keyword", ""));
            }
        }));

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}