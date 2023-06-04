package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import com.example.food_recipes_application.Adapters.FavoriteAdapter;
import com.example.food_recipes_application.Database.MyDatabaseHelper;

public class FavoritesActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
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

        RecyclerView recyclerView=findViewById(R.id.fav_recyclerView);
        recyclerView.setAdapter(new FavoriteAdapter(this, helper.getAllRecipes(), id -> startActivity(new Intent(FavoritesActivity.this, RecipeDetailsActivity.class)
                .putExtra("id", id).putExtra("keyword", ""))));

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}