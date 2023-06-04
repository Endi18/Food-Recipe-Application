package com.example.food_recipes_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                startActivity(new Intent(getActivity(), WelcomeActivity.class));
                return true;
            } else if (itemId == R.id.menu_search) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
                return true;
            } else if (itemId == R.id.menu_favorites) {
                startActivity(new Intent(getActivity(), FavoritesActivity.class));
                return true;
            } else if (itemId == R.id.menu_profile) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                return true;
            }

            return false;
        });
        return view;
    }
}
