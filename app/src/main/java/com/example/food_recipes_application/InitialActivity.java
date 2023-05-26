package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.food_recipes_application.Models.Recipe;

public class InitialActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
    }

    public void goToLoginPage(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToSearchPage(View view) {
       Intent intent = new Intent(this, SearchActivity.class); //@@IMPLEMENT WHEN SEARCH IS DONE
        startActivity(intent);
    }
}