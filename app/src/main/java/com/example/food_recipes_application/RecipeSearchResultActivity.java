package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_recipes_application.Adapters.RecipeSearchResultAdapter;
import com.example.food_recipes_application.Listeners.APISearchResponseListener;
import com.example.food_recipes_application.Listeners.RecipeClickListener;
import com.example.food_recipes_application.Models.APISearchResponse;

public class RecipeSearchResultActivity extends AppCompatActivity {

    APIRequestManager manager;
    ProgressDialog progressDialog;
    RecipeSearchResultAdapter adapter;
    RecyclerView recyclerView;
    TextView recipesResultNumber;
    String recipeSearchKeyword = "pi"; //@@SET FOR TESTING PURPOSES WILL BE REMOVED


    final APISearchResponseListener apiSearchResponseListener = new APISearchResponseListener() {
        @Override
        public void didFetch(APISearchResponse response, String message) {
            progressDialog.dismiss();

            recipesResultNumber = findViewById(R.id.recipesResultNumber);
            recipesResultNumber.setText(String.format("Recipes found: %d", response.results.size()));

            recyclerView = findViewById(R.id.recipeRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(RecipeSearchResultActivity.this, 1));

            adapter = new RecipeSearchResultAdapter(RecipeSearchResultActivity.this, response.results, recipeClickListener);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void didError(String message) {
            Toast toast = Toast.makeText(RecipeSearchResultActivity.this, message, Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search_result);
        recipeSearchKeyword = getIntent().getStringExtra("detailsId"); //@@ADD INTENT KEY HERE FROM SEARCH ACTIVITY
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Recipes...");

        manager = new APIRequestManager(this, recipeSearchKeyword);
        manager.getRecipesSearchResults(apiSearchResponseListener);
        progressDialog.show();
    }

    public void goBackToSearchPage(View view) {
        //Intent intent = new Intent(this, class); //@@COMPLETE WHEN SEARCH ACTIVITY IS DONE
       // startActivity(intent);
    }

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
           startActivity(new Intent(RecipeSearchResultActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
          //  Toast.makeText(RecipeSearchResultActivity.this, id, Toast.LENGTH_SHORT).show();
        }
    };
}