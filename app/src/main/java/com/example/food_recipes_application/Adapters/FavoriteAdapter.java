package com.example.food_recipes_application.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_recipes_application.Database.MyDatabaseHelper;
import com.example.food_recipes_application.FavoritesActivity;
import com.example.food_recipes_application.Models.FavItem;
import com.example.food_recipes_application.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private ArrayList<FavItem> favItem;
    private Context context;
    private boolean image;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //
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

        //final FavItem favItm =  favItem.get(position);

        holder.textViewF.setText(favItem.get(position).getTitle());
        if (favItem.get(position).getIsLiked() == 1) {
            holder.like_button_cb.setChecked(true);
            holder.like_button_cb.setBackground(Drawable.createFromPath("@drawable/ic_love"));
        }else {
            holder.like_button_cb.setChecked(false);
            holder.like_button_cb.setBackground(Drawable.createFromPath("@drawable/ic_love_checked"));
        }

        holder.like_button_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        holder.like_button_cb.setChecked(false);
                        holder.like_button_cb.setBackground(Drawable.createFromPath("@drawable/ic_love_checked"));
                    } else {
                        holder.like_button_cb.setChecked(true);
                        holder.like_button_cb.setBackground(Drawable.createFromPath("@drawable/ic_love"));
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return favItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewF;
        CheckBox like_button_cb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewF = itemView. findViewById(R.id. textViewF);
            like_button_cb = itemView.findViewById(R.id.like_button_cb);

            //Add to favorite  btn
           /* like_button_cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    FavItem favItem = FavItem.get(position);
                    if(favItem.getFavStatus().equals(“0”)){
                        favItem.setFavStatus(“1”);
                        myDatabaseHelper.insertIntoTheDatabase(favItem.getTitle(),favItem.getImageResourse(),
                                favItem.getKey_id(), favItem.getFavStatus());
                        like_button_cb.setBackgroundResource(R.drawable.like_button_background);
                    }else  {
                        favItem.setFavStatus(“0”);
                        favDB.remove_fav(favItem.getKey_id());
                        like_button_cb.setBackgroundeResource)(R.drawable.ic_love);
                    }
                }

            }); }*/
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void createTableOnFirstStart() {
        // myDatabaseHelper.
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean( "firstStart", false);
        editor.apply();

    }
   /* private void readCrusorData(FavItem favItem, ViewHolder viewHolder){
        //Cursor cursor =
    }*/

}

