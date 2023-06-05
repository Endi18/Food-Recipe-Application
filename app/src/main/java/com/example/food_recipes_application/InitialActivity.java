package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class InitialActivity extends AppCompatActivity {
    Boolean isLoggedIn;
    Button guestButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
        isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if(isLoggedIn) {
            SharedPreferences sharedPreferencesLogOut = getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesLogOut.edit();
            editor.putBoolean("isLoggedIn", false); // Set the login state as logged in
            editor.apply();
        }
    }

    public void goToLoginPage(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToSearchPage(View view) {
       Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}