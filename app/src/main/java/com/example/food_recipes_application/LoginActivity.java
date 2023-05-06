package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void authenticateUser(View view) {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        //IMPLEMENT AUTH AFTER DB CONNECTION
    }
}