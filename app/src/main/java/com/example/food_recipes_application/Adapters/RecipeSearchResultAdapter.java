package com.example.food_recipes_application.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_recipes_application.Listeners.RecipeClickListener;
import com.example.food_recipes_application.Models.Recipe;
import com.example.food_recipes_application.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeSearchResultAdapter extends RecyclerView.Adapter<RecipeSearchResultViewHolder>{

    Context context;
    List<Recipe> recipeList;
    RecipeClickListener listener;

    public RecipeSearchResultAdapter(Context context, List<Recipe> recipeList, RecipeClickListener listener) {
        this.context = context;
        this.recipeList = recipeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeSearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeSearchResultViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeSearchResultViewHolder holder, int position) {
         holder.dishName.setText(recipeList.get(position).title);
         Picasso.get().load(recipeList.get(position).image).into(holder.dishImage);
         holder.recipeCardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 listener.onRecipeClicked(String.valueOf(recipeList.get(holder.getAdapterPosition()).id));
             }
         });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}

class RecipeSearchResultViewHolder extends RecyclerView.ViewHolder {
    CardView recipeCardView;
    TextView dishName;
    ImageView dishImage;

    public RecipeSearchResultViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeCardView = itemView.findViewById(R.id.recipeCardView);
        dishName = itemView.findViewById(R.id.dishName);
        dishImage = itemView.findViewById(R.id.dishImage);
    }
}