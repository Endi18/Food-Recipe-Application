package com.example.food_recipes_application.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_recipes_application.Database.MyDatabaseHelper;
import com.example.food_recipes_application.Listeners.RecipeClickListener;
import com.example.food_recipes_application.Models.Recipe;
import com.example.food_recipes_application.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder>{

    Context context;
    List<Recipe> recipeList;
    RecipeClickListener listener;

    MyDatabaseHelper helper;

    public FavoriteAdapter(Context context, List<Recipe> recipeList, RecipeClickListener listener) {
        this.context = context;
        this.recipeList = recipeList;
        this.listener = listener;
        this.helper=new MyDatabaseHelper(context);
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

        Recipe recipe=recipeList.get(position);
         holder.dishName.setText(recipe.title);
         Picasso.get().load(recipe.image).into(holder.dishImage);
         holder.recipeCardView.setOnClickListener(v -> listener.onRecipeClicked(String.valueOf(recipeList.get(holder.getAdapterPosition()).id)));

         if(helper.doesRecipeExists(recipe.id))
         {
          holder.imgFvrt.setImageTintList(ColorStateList.valueOf(Color.RED));
         }
         else
         {
             holder.imgFvrt.setImageTintList(ColorStateList.valueOf(Color.WHITE));
         }

         holder.imgFvrt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(helper.doesRecipeExists(recipe.id))
                 {
                     helper.deleteRecipe(recipe.id);
                     recipeList.remove(holder.getAbsoluteAdapterPosition());
                     notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                 }
                 else
                 {
                     helper.saveRecipe(recipe);
                     holder.imgFvrt.setImageTintList(ColorStateList.valueOf(Color.RED));
                 }
             }
         });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}

class FavoriteViewHolder extends RecyclerView.ViewHolder {
    CardView recipeCardView;
    TextView dishName;
    ImageView dishImage;
    ImageView imgFvrt;

    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeCardView = itemView.findViewById(R.id.recipeCardView);
        dishName = itemView.findViewById(R.id.dishName);
        dishImage = itemView.findViewById(R.id.dishImage);
        imgFvrt = itemView.findViewById(R.id.imgFvrt);
    }
}