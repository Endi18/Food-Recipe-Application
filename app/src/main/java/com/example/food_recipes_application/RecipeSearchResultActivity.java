package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    String recipeSearchKeyword;


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
        recipeSearchKeyword = getIntent().getStringExtra("keyword");
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Recipes...");

        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            BottomNavigationFragment bottomNavigationFragment = new BottomNavigationFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerSearchResults, bottomNavigationFragment);
            transaction.commit();

        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerSearchResults);
            if (fragment != null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();
            }
        }

        manager = new APIRequestManager(this, recipeSearchKeyword);
        manager.getRecipesSearchResults(apiSearchResponseListener);
        progressDialog.show();
    }

    public void goBackToSearchPage(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private final RecipeClickListener recipeClickListener = id -> startActivity(new Intent(RecipeSearchResultActivity.this, RecipeDetailsActivity.class)
             .putExtra("id", id).putExtra("keyword", recipeSearchKeyword));
}