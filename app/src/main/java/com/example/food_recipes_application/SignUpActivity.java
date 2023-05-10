package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.food_recipes_application.Database.MyDatabaseHelper;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    EditText username, email, password, confirmPassword;
    private boolean passwordVisible = false;
    private Drawable visibilityOnIcon, visibilityOffIcon, securityIcon;
    private Typeface typeface;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);

        visibilityOnIcon = getDrawable(R.drawable.ic_visibility_on);
        visibilityOffIcon = getDrawable(R.drawable.ic_visibility_off);
        securityIcon = getDrawable(R.drawable.ic_security_icon);

        typeface = password.getTypeface();


        password.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Hide the password
            passwordVisible = false;
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password.setCompoundDrawablesRelativeWithIntrinsicBounds(securityIcon, null, visibilityOffIcon, null);
        } else {
            // Show the password
            passwordVisible = true;
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            password.setCompoundDrawablesRelativeWithIntrinsicBounds(securityIcon, null, visibilityOnIcon, null);
        }
        password.setTypeface(typeface);
    }

    public void clickSignUpButton(View view) {

        boolean confirmedInput = confirmInput();
        boolean result;

        if(confirmedInput) {
            try (MyDatabaseHelper db = new MyDatabaseHelper(SignUpActivity.this)) {
                result = db.addUser(username.getText().toString().trim(),
                        email.getText().toString().trim(), password.getText().toString().trim());
            }

            if (result) {
                username.setText("");
                email.setText("");
                password.setText("");
                confirmPassword.setText("");
                Toast.makeText(this, "User added successfully!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Email Already Exists!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean confirmInput() {
        return validateEmail() && validateUsername() && validatePassword() && validateConfirmPassword();
    }

    public void clickAlreadyHaveAnAccount(View view) {
        Intent goToLogInActivity = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(goToLogInActivity);
    }

    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Field cannot be empty!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = username.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            username.setError("Field cannot be empty!");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            password.setError("Field cannot be empty!");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password too weak!");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String confirmPasswordInput = confirmPassword.getText().toString().trim();

        if (confirmPasswordInput.isEmpty()) {
            confirmPassword.setError("Field cannot be empty!");
            return false;
        } else if (!confirmPasswordInput.equals(password.getText().toString())) {
            confirmPassword.setError("Passwords does not match!");
            return false;
        } else {
            confirmPassword.setError(null);
            return true;
        }
    }

    public void passwordInformation(View view){
        Toast.makeText(this, "It must have at least 8 characters, 1 upper & lower case & 1 digit", Toast.LENGTH_LONG).show();
    }

    public void goBackToInitialActivity(View view){
        Intent intent = new Intent(this, InitialActivity.class);
        startActivity(intent);
    }
}