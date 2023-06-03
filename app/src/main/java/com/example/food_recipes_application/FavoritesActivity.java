package com.example.food_recipes_application;

import static com.example.food_recipes_application.R.id.textViewF;
import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.List;


public class FavoritesActivity extends AppCompatActivity {

    private TextView textView;
    private Context context;
    private ArrayList<FavItem> favItem = new ArrayList<>();

    private RecyclerView recyclerView;
    private TextView textViewF;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.favorite_activity);
        setContentView(R.layout.fav_fragment_recycler);

        textView = findViewById(R.id.textViewF);

      /*   RecyclerView recyclerView = findViewById(R.id.fav_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new FavItem(favItem,  context));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

       favItem.add(new favItem(R.drawable.img,   "Latte",  “0”,  ”0”));
        favItem.add(new favItem(R.drawable.img,   "Kapucino", “1”,  ”0”));
        favItem.add(new favItem(R.drawable.img,   "Raf", “2”, ”0”));
        favItem.add(new favItem(R.drawable.img, "Milk Shake" , “3”, 0”));

        return root;*/

        // Make an API request
       // new FetchFavoritesTask().execute(context.getString(R.string.apiKey));
        recyclerView = findViewById(R.id.fav_recyclerView);
        textViewF = findViewById(R.id.textViewF);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("pref", 0);
        // PreferenceManager preferenceManager = new PreferenceManager(sharedPreferences);


      //  fetchData();
   // }
    }


    /*private void fetchData() {
        List<FavItem> fvitm =

        if (fvitm != null && fvitm.size() > 0) {
            showNoFavtText(false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new FavoriteAdapter(fvitm, this,));
        } else {
            showNoFavtText(true);
        }

    }*/

    private void showNoFavtText(boolean show) {
        textViewF.setVisibility(show ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }



    // Define a static list to store the favorite items
    private static List<FavItem> favoriteItems = new ArrayList<>();

    // Add a favorite item to the list
    public static void addFavoriteItem(FavItem favItem) {
        favoriteItems.add(favItem);
    }

    // Get the list of favorite items
    public static List<FavItem> getFavoriteItems() {
        return favoriteItems;
    }

    // Clear the list of favorite items
    public static void clearFavoriteItems() {
        favoriteItems.clear();
    }

    private void fetchData() {
        List<FavItem> fvitm = getFavoriteItems(); // Fetch the favorite items

        if (fvitm != null && fvitm.size() > 0) {
            showNoFavtText(false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
         //   recyclerView.setAdapter(new FavoriteAdapter(fvitm, this));
        } else {
            showNoFavtText(true);
        }
    }

}