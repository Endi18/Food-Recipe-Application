package com.example.food_recipes_application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.food_recipes_application.Database.MyDatabaseHelper;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity {

    public  static String currentEmail="";

    public  static String UserID="";
    private MyDatabaseHelper dbHelper;
    private EditText usernameTextView, emailTextView,passwordTextView;
    String userId;
    private boolean passwordVisible = false;
    private boolean isUsernameEditable = false;
    private boolean isEmailEditable = false;
    private boolean isPasswordEditable = false;

    private TextView iconUsername, iconEmail, iconPassword;

    private Drawable visibilityOnIcon, visibilityOffIcon, pencil, pencil_diagonal;
    private Typeface typeface;
    String originalUsername, originalEmail, originalPassword;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    ".{8,}" +               //at least 8 characters
                    "$");


    @SuppressLint({"UseCompatLoadingForDrawables", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new MyDatabaseHelper(this);

        usernameTextView = findViewById(R.id.editTextProfileUsername);
        emailTextView = findViewById(R.id.editTextProfileEmail);
        passwordTextView = findViewById(R.id.editTextProfilePassword);

        iconUsername = findViewById(R.id.textViewEditUsername);
        iconEmail = findViewById(R.id.textViewEditEmail);
        iconPassword = findViewById(R.id.textViewEditPassword);


        visibilityOnIcon = getDrawable(R.drawable.ic_visibility_on);
        visibilityOffIcon = getDrawable(R.drawable.ic_visibility_off);

        pencil = getDrawable(R.drawable.ic_pencil);
        pencil_diagonal = getDrawable(R.drawable.ic_pencil_with_diagonal_line);

        typeface = passwordTextView.getTypeface();


        passwordTextView.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordTextView.getRight() - passwordTextView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });

        BottomNavigationFragment bottomNavigationFragment = new BottomNavigationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerProfile, bottomNavigationFragment);
        transaction.commit();

        findViewById(R.id.button2).setOnClickListener(v -> {

            String userName= usernameTextView.getText().toString();
            String email= emailTextView.getText().toString();
            String password= passwordTextView.getText().toString();

            if (userName.isEmpty()) {
                usernameTextView.setError("This field should not be empty");
                return;
            }
            if (password.isEmpty()) {
                passwordTextView.setError("This field should not be empty");
                return;
            }
            if (email.isEmpty()){
                emailTextView.setError("This field should not be empty");
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailTextView.setError("Provide a valid email!");
                return;
            }
            else if (!originalEmail.equals(email) && dbHelper.isEmailAlreadyExists(email)) {
                emailTextView.setError("This email is used by another user!");
                return;
            }

            if (!PASSWORD_PATTERN.matcher(password).matches()) {
                passwordTextView.setError("Password should contain at least one digit, upper and lower case!");
                return;
            }

            boolean isUpdated = false;


            if(!originalUsername.equals(userName)){
                if(dbHelper.updateUsername(userId, userName))
                    isUpdated = true;
            }

            if(!originalEmail.equals(email)){
                if(dbHelper.updateEmail(userId, email)) {
                    currentEmail = email;
                    isUpdated = true;
                }
            }

            if(!originalPassword.equals(password)){
                if(dbHelper.updatePassword(userId, password)){
                    isUpdated = true;
                }
            }

            if(!isUpdated) {
                Toast.makeText(ProfileActivity.this, "Nothing is changed", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ProfileActivity.this, "Info is updated" , Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }

        });
        findViewById(R.id.button4).setOnClickListener(v -> {
            startActivity(new Intent(this, InitialActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false); // Set the login state as logged in
            editor.apply();
        });

        findViewById(R.id.btnBack2).
                setOnClickListener(v -> finish());
        Cursor cursor = dbHelper.getUserData(currentEmail);

        int EMAIL_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_EMAIL);
        int PASSWORD_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_PASSWORD);
        int USER_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_USERNAME);
        int ID_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_ID);

        while (cursor.moveToNext()) {
            emailTextView.setText(cursor.getString(EMAIL_COLUMN_INDEX));
            passwordTextView.setText(cursor.getString(PASSWORD_COLUMN_INDEX));
            usernameTextView.setText(cursor.getString(USER_COLUMN_INDEX));
            userId = cursor.getString(ID_COLUMN_INDEX);

            originalUsername = cursor.getString(USER_COLUMN_INDEX);
            originalEmail = cursor.getString(EMAIL_COLUMN_INDEX);
            originalPassword = cursor.getString(PASSWORD_COLUMN_INDEX);
        }

        cursor.close();
    }
    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Hide the password
            passwordVisible = false;
            passwordTextView.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, visibilityOffIcon, null);
        } else {
            // Show the password
            passwordVisible = true;
            passwordTextView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passwordTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, visibilityOnIcon, null);
        }
        passwordTextView.setTypeface(typeface);
    }

    public void clickUsernamePencil(View view){
        if (isUsernameEditable) {
            isUsernameEditable = false;

            usernameTextView.setFocusable(false);
            usernameTextView.setCursorVisible(false);
            usernameTextView.setFocusableInTouchMode(false);
           usernameTextView.setInputType(InputType.TYPE_NULL);
            iconUsername.setCompoundDrawablesRelativeWithIntrinsicBounds(pencil, null, null, null);
        } else {
            isUsernameEditable = true;

            usernameTextView.setFocusable(true);
            usernameTextView.setFocusableInTouchMode(true);

            usernameTextView.setCursorVisible(true);

            usernameTextView.setInputType(InputType.TYPE_CLASS_TEXT);
            iconUsername.setCompoundDrawablesRelativeWithIntrinsicBounds(pencil_diagonal, null, null, null);
        }
        usernameTextView.setTypeface(typeface);
    }

    public void clickEmailPencil(View view){
        if (isEmailEditable) {
            isEmailEditable = false;
            emailTextView.setFocusable(false);
            emailTextView.setFocusableInTouchMode(false);
            emailTextView.setCursorVisible(false);
            emailTextView.setInputType(InputType.TYPE_NULL);
            iconEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(pencil, null, null, null);
        } else {
            isEmailEditable = true;
            emailTextView.setFocusable(true);
            emailTextView.setFocusableInTouchMode(true);
            emailTextView.setCursorVisible(true);
            emailTextView.setInputType(InputType.TYPE_CLASS_TEXT);
            iconEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(pencil_diagonal, null, null, null);
        }
        emailTextView.setTypeface(typeface);
    }

    public void clickPasswordPencil(View view){
        if (isPasswordEditable) {
            isPasswordEditable = false;
            passwordTextView.setFocusable(false);
            passwordTextView.setFocusableInTouchMode(false);
            passwordTextView.setCursorVisible(false);
            passwordTextView.setInputType(InputType.TYPE_CLASS_TEXT);
            iconPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(pencil, null, null, null);
        } else {
            isPasswordEditable = true;
            passwordTextView.setFocusable(true);
            passwordTextView.setFocusableInTouchMode(true);
            passwordTextView.setCursorVisible(true);
            passwordTextView.setInputType(InputType.TYPE_CLASS_TEXT);
            iconPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(pencil_diagonal, null, null, null);
        }
        passwordTextView.setTypeface(typeface);
    }

}