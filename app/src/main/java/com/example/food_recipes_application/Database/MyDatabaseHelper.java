package com.example.food_recipes_application.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.example.food_recipes_application.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {


    private final Context context;
    private static final String DATABASE_NAME = "FoodRecipes.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME_USERS = "Users";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";




    private static final String TABLE_NAME = "recipes";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_IMAGE_TYPE = "imageType";
    private static final String COLUMN_IS_FAVORITE = "isFavorite";



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USERS +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT);";
        db.execSQL(query);

        String createRecipeTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_IMAGE + " TEXT," +
                COLUMN_IMAGE_TYPE + " TEXT," +
                COLUMN_IS_FAVORITE + " INTEGER DEFAULT 0)";

        db.execSQL(createRecipeTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        String dropTableUSer = "DROP TABLE IF EXISTS " + TABLE_NAME_USERS;
        db.execSQL(dropTableQuery);
        db.execSQL(dropTableUSer);
        onCreate(db);
    }

    @SuppressLint("Recycle")
    public boolean addUser(String username, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query("Users", new String[]{"ID"}, "Email=?", new String[]{email}, null, null, null);
        if (cursor.getCount() > 0) {
            return false;
        } else {
            ContentValues values = new ContentValues();
            values.put("Username", username);
            values.put("Email", email);
            values.put("Password", password);

            db.insertOrThrow("Users", null, values);
            return true;
        }

    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME_USERS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getUserData(String email){
        // String query = "SELECT * FROM " + TABLE_NAME + "WHERE " + COLUMN_USERNAME + " = " + email;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_EMAIL + " =?";
        String[] selectionArgs = {email};
        Cursor cursor = null;
        if(db != null){
            cursor = db.query(TABLE_NAME_USERS, new String[]{COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_USERNAME, COLUMN_ID},selection, selectionArgs, null, null, null);
        }
        return cursor;
    }

//    public void updateUserData(String row_id, String title, String author, String pages){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_USERNAME, title);
//        cv.put(COLUMN_EMAIL, author);
//        cv.put(COLUMN_PASSWORD, pages);
//
//        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
//        if(result == -1){
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public void deleteOneUser(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_USERS, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_USERS);
    }



    public void saveRecipe(Recipe recipe) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, recipe.id);
        values.put(COLUMN_TITLE, recipe.title);
        values.put(COLUMN_IMAGE, recipe.image);
        values.put(COLUMN_IMAGE_TYPE, recipe.imageType);
        values.put(COLUMN_IS_FAVORITE, recipe.isFvrt ? 1 : 0);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    @SuppressLint("Range")
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                recipe.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                recipe.image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
                recipe.imageType = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_TYPE));
                recipe.isFvrt = (cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FAVORITE)) == 1);

                recipes.add(recipe);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recipes;
    }

    public void deleteRecipe(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public boolean isRecipeExists(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }
    public Cursor getCurrentUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.query(
                    TABLE_NAME_USERS,
                    new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_PASSWORD},
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }
        return cursor;
    }

    public boolean updateUser(String id,String username, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);


        Cursor cursor = db.rawQuery("Select * from Users where ID = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount()>0)
        {
            long result =   db.update("Users" ,values,"ID=?",new String[]{String.valueOf(id)});
            if (result==-1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public boolean updateUsername(String id, String username){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);

        Cursor cursor = db.rawQuery("Select * from Users where ID = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount()>0)
        {
            long result =   db.update("Users" ,values,"ID=?",new String[]{String.valueOf(id)});
            if (result==-1)
                return false;
            else
                return true;
        }
        else
            return false;
    }



    public boolean updateEmail(String id, String email){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);


        Cursor cursor = db.rawQuery("Select * from Users where ID = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount()>0)
        {
            long result =   db.update("Users" ,values,"ID=?",new String[]{String.valueOf(id)});
            if (result==-1)
                return false;
            else
                return true;
        }
        else
            return false;
    }


    public boolean updatePassword(String id, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, password);


        Cursor cursor = db.rawQuery("Select * from Users where ID = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount()>0)
        {
            long result =   db.update("Users" ,values,"ID=?",new String[]{String.valueOf(id)});
            if (result==-1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public boolean isEmailAlreadyExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(
                "users",
                null,
                "email = ?",
                new String[]{email},
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }
}
