package com.example.food_recipes_application.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "FoodRecipes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Users";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @SuppressLint("Recycle")
    public int addUser(String username, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query("Users", new String[]{"ID"}, "Email=?", new String[]{email}, null, null, null);
            if (cursor.getCount() > 0) {
                Toast.makeText(context, "Email Already Exists!", Toast.LENGTH_SHORT).show();
                return 0;
            } else {
                ContentValues values = new ContentValues();
                values.put("Username", username);
                values.put("Email", email);
                values.put("Password", password);

                db.insertOrThrow("Users", null, values);
                Toast.makeText(context, "User added successfully!", Toast.LENGTH_SHORT).show();
                return 1;
            }

    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
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
            cursor = db.query(TABLE_NAME, new String[]{COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_USERNAME, COLUMN_ID},selection, selectionArgs, null, null, null);
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
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
