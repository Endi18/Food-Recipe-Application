package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.food_recipes_application.Database.MyDatabaseHelper;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    String userId;
    Button registerButton;
    boolean isDataCorrect = false;
    MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void authenticateUser(View view) {
       String userProvidedEmail = email.getText().toString();
       String userProvidedPassword = password.getText().toString();

       if (userProvidedEmail.isEmpty()) {
            email.setError("Please provide your email!");
            email.requestFocus();
            return;
        } else if (userProvidedPassword.isEmpty()) {
            password.setError("Please provide your password!");
            password.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(userProvidedEmail).matches()){
            email.setError("Please provide a valid email!");
            email.requestFocus();
            return;
        }

        databaseHelper = new MyDatabaseHelper(LoginActivity.this);
        Cursor cursor = databaseHelper.getUserData(email.getText().toString());

        int EMAIL_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_EMAIL);
        int PASSWORD_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_PASSWORD);
        int ID_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_ID);

        while (cursor.moveToNext()) {
            String userEmail = cursor.getString(EMAIL_COLUMN_INDEX);
            String userPassword = cursor.getString(PASSWORD_COLUMN_INDEX);
            userId = cursor.getString(ID_COLUMN_INDEX);

            if(Objects.equals(userEmail, userProvidedEmail) && Objects.equals(userPassword, userProvidedPassword)) {
                isDataCorrect = true;
                break;
            }
        }
        cursor.close();
        goToSearchActivity(this.getCurrentFocus());
    }

    public void goToSearchActivity(View view) {
        if(isDataCorrect) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("UserId", userId);
            startActivity(intent);
        }
        else {
            email.setError("Please review email!");
            password.setError("Please review password!");
            Toast toast = Toast.makeText(this, "Incorrect Email or Password!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
Info
    public void registerUser(View view) {
        registerButton = findViewById(R.id.register_button);
        registerButton.setTextColor(Color.WHITE);
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void goBackToInitialActivity(View view) {
        Intent intent = new Intent(this, InitialActivity.class);
        startActivity(intent);
    }
}