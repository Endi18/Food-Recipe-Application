package com.example.food_recipes_application.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_recipes_application.Database.MyDatabaseHelper;
import com.example.food_recipes_application.Models.FavItem;
import com.example.food_recipes_application.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private ArrayList<FavItem> favItem;
    private Context context;
    private MyDatabaseHelper myDatabaseHelper;

    public FavoriteAdapter(ArrayList<FavItem> favItem, Context context)  {
        this.favItem = favItem;
        this.context = context;
    }
    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        myDatabaseHelper = new MyDatabaseHelper(context);
        SharedPreferences prefs =  context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart){
            createTableOnFirstStart();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewF;
        Button favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewF = itemView. findViewById(R.id. textViewF);
            favBtn = itemView.findViewById(R.id. favBtn);
            //Add to favorite  btn
           /* favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    FavItem favItem = FavItem.get(position);
                    if(favItem.getFavStatus().equals(“0”)){
                        favItem.setFavStatus(“1”);
                        myDatabaseHelper.insertIntoTheDatabase(favItem.getTitle(),favItem.getImageResourse(),
                                favItem.getKey_id(), favItem.getFavStatus());
                        favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
                    }else  {
                        favItem.setFavStatus(“0”);
                        favDB.remove_fav(favItem.getKey_id());
                        favBtn.setBackgroundeResource)(R.drawable.ic_favorite_shadow_24dp);
                    }
                }

            }); }*/
        }
    }

    private void createTableOnFirstStart() {
       // myDatabaseHelper.
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean( "firstStart", false);
        editor.apply();

    }


}

