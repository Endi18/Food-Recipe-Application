package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.food_recipes_application.Database.MyDatabaseHelper;

public class SignUpActivity extends AppCompatActivity {

    EditText username, email, password, confirmPassword;

    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);

        password.setOnTouchListener((v, event) -> {
            @SuppressLint("UseCompatLoadingForDrawables") Drawable visibilityOn = getDrawable(R.drawable.ic_visibility_on);
            @SuppressLint("UseCompatLoadingForDrawables") Drawable visibilityOff = getDrawable(R.drawable.ic_visibility_off);
            @SuppressLint("UseCompatLoadingForDrawables") Drawable securityIcon = getDrawable(R.drawable.ic_security_icon);

            int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (password.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                        password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        password.setCompoundDrawablesWithIntrinsicBounds(securityIcon, null, visibilityOff, null);
                    } else {
                        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        password.setCompoundDrawablesWithIntrinsicBounds(securityIcon, null, visibilityOn, null);
                    }
                    return true;
                }
            }
            return false;
        });

        confirmPassword.setOnTouchListener((v, event) -> {
            @SuppressLint("UseCompatLoadingForDrawables") Drawable visibilityOn = getDrawable(R.drawable.ic_visibility_on);
            @SuppressLint("UseCompatLoadingForDrawables") Drawable visibilityOff = getDrawable(R.drawable.ic_visibility_off);
            @SuppressLint("UseCompatLoadingForDrawables") Drawable securityIcon = getDrawable(R.drawable.ic_security_icon);

            int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (confirmPassword.getRight() - confirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (confirmPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                        confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        confirmPassword.setCompoundDrawablesWithIntrinsicBounds(securityIcon, null, visibilityOff, null);
                    } else {
                        confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        confirmPassword.setCompoundDrawablesWithIntrinsicBounds(securityIcon, null, visibilityOn, null);
                    }
                    return true;
                }
            }
            return false;
        });
    }

    public void clickSignUpButton(View view) {
        if (TextUtils.isEmpty(username.getText().toString())) {
            username.setError("Username is compulsory!");
            return;
        }

        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Email is compulsory!");
            return;
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Password is compulsory!");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
            confirmPassword.setError("Confirmed Password is compulsory!");
            return;
        }

        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            confirmPassword.setError("Password does not match!");
            return;
        }

        int result;

        try(MyDatabaseHelper db = new MyDatabaseHelper(SignUpActivity.this)) {
            result = db.addUser(username.getText().toString().trim(),
                            email.getText().toString().trim(), password.getText().toString().trim());
        }

        if (result == 1) {
            username.setText("");
            email.setText("");
            password.setText("");
            confirmPassword.setText("");
        }
    }

    public void clickAlreadyHaveAnAccount(View view) {
        Intent goToLogInActivity = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(goToLogInActivity);
    }
}