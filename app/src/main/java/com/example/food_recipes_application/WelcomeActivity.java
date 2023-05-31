package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        int selectedItemId = getIntent().getIntExtra("selectedItemId", R.id.menu_home);

        BottomNavigationFragment fragment = new BottomNavigationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("selectedItemId", selectedItemId);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void emailTo(View view){
        startActivity( new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:info@foodtopia.al")));
    }
}