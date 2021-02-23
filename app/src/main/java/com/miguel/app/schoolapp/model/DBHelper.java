package com.miguel.app.schoolapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import com.miguel.app.schoolapp.R;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "school.db";
    public static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StudentDB.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(StudentDB.SQL_DROP_TABLE);
        onCreate(db);
    }

    public void cleanDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(StudentDB.SQL_DROP_TABLE);
        onCreate(db);
    }

    //    INSERT USER DATA
    public void insert(String name, String lastname, String date, String api_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudentDB.Data.COL_NAME, name);
        values.put(StudentDB.Data.COL_LASTNAME, lastname);
        values.put(StudentDB.Data.COL_DATE, date);
        values.put(StudentDB.Data.COL_API_ID, api_id);

        long lastId = db.insert(StudentDB.Data.TABLE_NAME, null, values);
        Log.i("MITO_TAG", "SQL INSERT TO | id: " + lastId);
    }

    public void update(int id, String name, String lastname, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudentDB.Data.COL_NAME, name);
        values.put(StudentDB.Data.COL_LASTNAME, lastname);
        values.put(StudentDB.Data.COL_DATE, date);

        long lastId = db.update(StudentDB.Data.TABLE_NAME, values, "" + StudentDB.Data._ID + "=?", new String[]{String.valueOf(id)});
        Log.i("MITO_TAG", "SQL UPDATE | id: " + lastId);
    }

    public Cursor select() {
        SQLiteDatabase db = this.getWritableDatabase();
        String customQuery = "SELECT * FROM " + StudentDB.Data.TABLE_NAME;
        Cursor cursor = db.rawQuery(customQuery, null);
        return cursor;
    }

    public Cursor selectById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String customQuery = "SELECT * FROM " + StudentDB.Data.TABLE_NAME + " WHERE " + StudentDB.Data._ID + "=" + id;
        Cursor cursor = db.rawQuery(customQuery, null);
        return cursor;
    }

    public String getApiID(int id) {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String customQuery = "SELECT " + StudentDB.Data.COL_API_ID + " FROM " + StudentDB.Data.TABLE_NAME + " WHERE " + StudentDB.Data._ID + "=" + id;
        Cursor cursor = db.rawQuery(customQuery, null);
        while (cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_API_ID));
        }
        cursor.close();
        return result;
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

//        String customQuery = "DELETE FROM " + StudentDB.Data.TABLE_NAME + " WHERE " + StudentDB.Data._ID + "=?";
//        boolean result = db.rawQuery(customQuery, new String[]{String.valueOf(id)}).moveToFirst();

        String customQuery = "DELETE FROM " + StudentDB.Data.TABLE_NAME + " WHERE " + StudentDB.Data._ID + "=?";
        long result = db.delete(StudentDB.Data.TABLE_NAME, "" + StudentDB.Data._ID + "=?", new String[]{String.valueOf(id)});
        Log.i("MITO_TAG", "SQL DELETE | result: " + result);
    }




///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    OLD OLD OLD OLD OLD OLD OLD OLD OLD OLD OLD OLD OLD OLD OLD
    //    INSERT USER DATA
    public void insert_OLD(String name, String lastname, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudentDB.Data.COL_NAME, name);
        values.put(StudentDB.Data.COL_LASTNAME, lastname);
        values.put(StudentDB.Data.COL_DATE, date);

        long lastId = db.insert(StudentDB.Data.TABLE_NAME, null, values);
        Log.i("MITO_TAG", "SQL INSERT TO | id: " + lastId);
    }

}
