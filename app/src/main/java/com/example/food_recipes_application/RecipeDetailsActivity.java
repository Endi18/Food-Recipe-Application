package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.food_recipes_application.Adapters.IngredientsAdapter;
import com.example.food_recipes_application.Adapters.InstructionsAdapter;
import com.example.food_recipes_application.Database.MyDatabaseHelper;
import com.example.food_recipes_application.Listeners.InstructionsListener;
import com.example.food_recipes_application.Listeners.RecipeDetailsListener;
import com.example.food_recipes_application.Models.InstructionsResponse;
import com.example.food_recipes_application.Models.Recipe;
import com.example.food_recipes_application.Models.RecipeDetailsResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    int id;
    String keyword;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary;
    ImageView like_button_cb_recipeCardView;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients, recycler_meal_instructions;
    APIRequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    InstructionsAdapter instructionsAdapter;
    MyDatabaseHelper helper;
    Recipe recipe;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
        isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        findViews();

        if (isLoggedIn) {
            BottomNavigationFragment bottomNavigationFragment = new BottomNavigationFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerRecipeDetails, bottomNavigationFragment);
            transaction.commit();
            recycler_meal_ingredients.setPadding(recycler_meal_ingredients.getPaddingLeft(), recycler_meal_ingredients.getPaddingTop(),
                    recycler_meal_ingredients.getPaddingLeft(), (int) (150 * getResources().getDisplayMetrics().density));
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerRecipeDetails);
            if (fragment != null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();
                recycler_meal_ingredients.setPadding(recycler_meal_ingredients.getPaddingLeft(), recycler_meal_ingredients.getPaddingTop(),
                        recycler_meal_ingredients.getPaddingLeft(), (int) (80 * getResources().getDisplayMetrics().density));
                like_button_cb_recipeCardView.setVisibility(View.GONE);
            }
        }


        helper=new MyDatabaseHelper(this);
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        keyword = getIntent().getStringExtra("keyword");
        manager = new APIRequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getInstructions(instructionsListener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();
    }

    private void findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients =  findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_instructions = findViewById(R.id.recycler_meal_instructions);
        like_button_cb_recipeCardView = findViewById(R.id.like_button_cb_recipeCardView);
    }

    void initRecipeHeart()
    {
        boolean isFav=false;
        int pos=0;
        for (int i = 0; i< FavoritesActivity.listRecipesFavorite.size(); i++)
        {
            if (FavoritesActivity.listRecipesFavorite.get(i).id==recipe.id)
            {
                isFav=true;

                pos=i;
            }

            if (i==FavoritesActivity.listRecipesFavorite.size()-1)
            {
                if (isFav)
                    like_button_cb_recipeCardView.setImageTintList(ColorStateList.valueOf(Color.RED));
                else
                    like_button_cb_recipeCardView.setImageTintList(ColorStateList.valueOf(Color.WHITE));

            }
        }


        boolean finalIsFav = isFav;
        int finalPos = pos;
        like_button_cb_recipeCardView.setOnClickListener(v -> {
            if (finalIsFav)
            {
                FavoritesActivity.listRecipesFavorite.remove(finalPos);
                FavoritesActivity.listRecipesFavorite.remove(recipe);

                Gson gson = new Gson();
                String newList= gson.toJson(FavoritesActivity.listRecipesFavorite);
                helper.saveRecipeFavorite(Integer.parseInt(ProfileActivity.UserID),newList);
                Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
                like_button_cb_recipeCardView.setImageTintList(ColorStateList.valueOf(Color.WHITE));

            }
            else
            {
                FavoritesActivity.listRecipesFavorite.add(recipe);

                Gson gson = new Gson();
                String newList= gson.toJson(FavoritesActivity.listRecipesFavorite);
                helper.saveRecipeFavorite(Integer.parseInt(ProfileActivity.UserID),newList);

                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                like_button_cb_recipeCardView.setImageTintList(ColorStateList.valueOf(Color.RED));
            }

        });

    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            Document doc = Jsoup.parse(response.summary);

            String plainText = doc.text();
            textView_meal_summary.setText(plainText);
            Picasso.get().load(response.image).into(imageView_meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this, response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);

            recipe=new Recipe();
            recipe.id=response.id;
            recipe.image=response.image;
            recipe.imageType=response.imageType;
            recipe.title=response.title;
            initRecipeHeart();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_LONG).show();
        }
    };

    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> response, String message) {
            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
            instructionsAdapter = new InstructionsAdapter(RecipeDetailsActivity.this, response);
            recycler_meal_instructions.setAdapter(instructionsAdapter);
        }

        @Override
        public void didError(String message) {
        }
    };

    public void goBackToSearchActivity(View view){

            finish();


    }
}