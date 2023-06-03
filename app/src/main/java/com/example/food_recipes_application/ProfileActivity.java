package com.example.food_recipes_application;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food_recipes_application.Database.MyDatabaseHelper;

public class ProfileActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private TextView usernameTextView;
    private TextView emailTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new MyDatabaseHelper(this);
        usernameTextView = findViewById(R.id.editTextUsername);
        emailTextView = findViewById(R.id.editTextEmail);

        // Get the current logged-in user
        Cursor cursor = dbHelper.getCurrentUser();

        if (cursor != null && cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_USERNAME));
            String email = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_EMAIL));


            // Display the user data in the TextViews
            usernameTextView.setText(username);
            emailTextView.setText(email);

            cursor.close(); // Remember to close the cursor when you're done with it
        } else {
            // No logged-in user found
        }
    }
}