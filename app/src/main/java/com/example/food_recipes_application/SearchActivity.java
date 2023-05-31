package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    SearchView searchView;
    String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Search...");

        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false); // Get the login state

        if (isLoggedIn) {

            int selectedItemId = getIntent().getIntExtra("selectedItemId", R.id.menu_search);

            BottomNavigationFragment fragment = new BottomNavigationFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("selectedItemId", selectedItemId);
            fragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerSearchNav, fragment);
            transaction.commit();

        } else {
            // User is a guest, hide the Bottom Navigation Bar fragment if previously added
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerSearchNav);
            if (fragment != null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();
            }
        }

        searchView = findViewById(R.id.searchView);

        searchView.setOnClickListener(v -> searchView.setIconified(false));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchText = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                return false;
            }
        });
    }

    public void submitAndGoToRecipeSearchResult(View view){
        if(searchText.equals("")) {
            Toast.makeText(this, "Please write in the search bar", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(SearchActivity.this, RecipeSearchResultActivity.class);
            intent.putExtra("keyword", searchText);
            startActivity(intent);
        }
    }

    public void goBackToInitialActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}