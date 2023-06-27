package com.example.sqlitexandroiddemo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "users";
    private static final int DB_VER = 1;
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE users(id VARCHAR(10) PRIMARY KEY, name TEXT, email TEXT, age INT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        onCreate(sqLiteDatabase);
    }

    public boolean registerUser (String mobNo, String name, String email, String age) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put("id", mobNo);
            contentValues.put("name", name);
            contentValues.put("email", email);
            contentValues.put("age", Integer.parseInt(age));
            sqLiteDatabase.insert("users", null, contentValues);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        finally {
            sqLiteDatabase.close();
        }
    }

    public String getUsers () {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        StringBuilder res = new StringBuilder();
        try {
            final Cursor cursor = sqLiteDatabase.query("users", null, null, null, null, null, null);
            if (cursor != null) {
                while(cursor.moveToNext()) {
                    res.append("[").append(
                            cursor.getString(cursor.getColumnIndexOrThrow("id")) + ", " +
                            cursor.getString(cursor.getColumnIndexOrThrow("name")) + ", " +
                            cursor.getString(cursor.getColumnIndexOrThrow("email")) + ", " +
                            cursor.getInt(cursor.getColumnIndexOrThrow("age"))
                    ).append("]").append("\n");
                }
                cursor.close();
            }
            return res.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            sqLiteDatabase.close();
        }
    }

    public void deleteUser(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int res = sqLiteDatabase.delete("users", "id = ?", new String[]{id});
        sqLiteDatabase.close();
    }

    public void update(String id, String name, String email, String age) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("age", Integer.parseInt(age));
        int res = sqLiteDatabase.update("users", contentValues, "id = ?", new String[]{id});
    }
}

