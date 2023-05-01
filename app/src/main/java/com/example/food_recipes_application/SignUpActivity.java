package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.editTxtUsername);
        email = findViewById(R.id.editTxtEmail);
        password = findViewById(R.id.editTxtPassword);
    }

    public void clickSignUpButton(View view){
    }

}