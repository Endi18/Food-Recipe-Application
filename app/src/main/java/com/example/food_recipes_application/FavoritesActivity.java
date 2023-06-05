package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import com.example.food_recipes_application.Adapters.FavoriteAdapter;
import com.example.food_recipes_application.Database.MyDatabaseHelper;
import com.example.food_recipes_application.Models.Recipe;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    public static ArrayList<Recipe> listRecipesFavorite =new ArrayList<>();
    public static Recipe currentSelectFavorite =new Recipe();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.favorite_activity);

        MyDatabaseHelper helper = new MyDatabaseHelper(this);
        setContentView(R.layout.fav_fragment_recycler);
        BottomNavigationFragment bottomNavigationFragment = new BottomNavigationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerFavorites, bottomNavigationFragment);
        transaction.commit();
        listRecipesFavorite.clear();
        listRecipesFavorite =new ArrayList<>();

        listRecipesFavorite.addAll(helper.getRecipeFavorite(Integer.parseInt(ProfileActivity.UserID)));
        RecyclerView recyclerView=findViewById(R.id.fav_recyclerView);
        recyclerView.setAdapter(new FavoriteAdapter(this, listRecipesFavorite, id -> startActivity(new Intent(FavoritesActivity.this, RecipeDetailsActivity.class)
                .putExtra("id", id).putExtra("keyword", "-1"))));

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}