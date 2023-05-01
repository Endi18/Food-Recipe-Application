package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_recipes_application.Database.MyDatabaseHelper;

public class SignUpActivity extends AppCompatActivity {

    EditText username, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);

    }

    public void clickSignUpButton(View view){
        if(TextUtils.isEmpty(username.getText().toString())){
            username.setError("Username is compulsory!");
            return;
        }

        if(TextUtils.isEmpty(email.getText().toString())){
            email.setError("Email is compulsory!");
            return;
        }

        if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("Password is compulsory!");
            return;
        }

        if(TextUtils.isEmpty(confirmPassword.getText().toString())){
            confirmPassword.setError("Confirmed Password is compulsory!");
            return;
        }

        if(password.getText().toString() != confirmPassword.getText().toString()) {
            confirmPassword.setError("Password does not match!");
            return;
        }

        int result = new MyDatabaseHelper(SignUpActivity.this)
                .addUser(username.getText().toString().trim(),
                        email.getText().toString().trim(), password.getText().toString().trim());

        if(result == 1){
            username.setText("");
            email.setText("");
            password.setText("");
            confirmPassword.setText("");
        }
    }

    public void clickAlreadyHaveAnAccount(View view){
        Intent goToLogInActivity = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(goToLogInActivity);
    }

}