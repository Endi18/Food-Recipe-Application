package com.example.food_recipes_application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food_recipes_application.Database.MyDatabaseHelper;

public class ProfileActivity extends AppCompatActivity {

    public  static String currentEmail="";
    private MyDatabaseHelper dbHelper;
    private EditText usernameTextView;
    private EditText emailTextView,passwordTextView;
    String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new MyDatabaseHelper(this);
        usernameTextView = findViewById(R.id.editTextText);
        emailTextView = findViewById(R.id.editTextText2);
        passwordTextView = findViewById(R.id.editTextTextPassword);

        findViewById(R.id.button2).setOnClickListener(v -> {

            String userName= usernameTextView.getText().toString();
            String email= emailTextView.getText().toString();
            String password= passwordTextView.getText().toString();
            if (userName.isEmpty()) {

                Toast.makeText(ProfileActivity.this, "Please provide your Name!", Toast.LENGTH_SHORT).show();

                return;
            } else if (password.isEmpty()) {
                Toast.makeText(ProfileActivity.this, "Please provide your password!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                Toast.makeText(ProfileActivity.this, "Please provide a valid email!", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean checkInsert = dbHelper.updateUser(userId,userName,email,password);
            if (checkInsert)
            {
                Toast.makeText(ProfileActivity.this, "Update User Data Successfully ", Toast.LENGTH_SHORT).show();
            currentEmail=email;
            }
            else
                Toast.makeText(ProfileActivity.this, "Update User Data Failed  !! Please Try Again" , Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.button4).setOnClickListener(v -> {
            startActivity(new Intent(this, InitialActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false); // Set the login state as logged in
            editor.apply();
        });

        findViewById(R.id.btnBack2).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
// Get the current logged-in user
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


        }
        cursor.close();

    }}