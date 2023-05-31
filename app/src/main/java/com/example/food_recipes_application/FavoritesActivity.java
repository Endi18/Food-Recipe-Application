package com.example.food_recipes_application;

import static com.example.food_recipes_application.R.id.textViewF;
import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.TextView;

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

    private TextView textView;
    private Context context;
    private ArrayList<FavItem> favItem = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_activity);

        /*textView = findViewById(R.id.textViewF);

        RecyclerView recyclerView = findViewById(R.id.fav_recyclerView);
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
    }

    /*public FavoritesActivity(Context context) {
        this.context = context;
    }

    private class FetchFavoritesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String apiKey = "aeb625c3f68d4365b5a01116e78a3663";
            String apiUrl = "https://api.spoonacular.com/recipes/random?apiKey=" + apiKey;

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray recipes = jsonObject.getJSONArray("recipes");

                    if (recipes.length() > 0) {
                        JSONObject recipe = recipes.getJSONObject(0);
                        String title = recipe.getString("title");
                        String instructions = recipe.getString("instructions");

                        String favoriteText = "Title: " + title + "\n\nInstructions: " + instructions;
                        textView.setText(favoriteText);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                textView.setText("Failed to fetch favorite");
            }
        }
    }*/
}