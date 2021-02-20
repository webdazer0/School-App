package com.miguel.app.schoolapp.model;

import android.provider.BaseColumns;

public class ListDB {

    ListDB() {}

    public static class Data implements BaseColumns {
        public static final String TABLE_NAME = "student";
        public static final String COL_NAME = "nome";
        public static final String COL_LASTNAME = "cognome";
        public static final String COL_DATE = "data";

    }

    public static final String SQL_CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + Data.TABLE_NAME +  " ( " +
                Data._ID + " INTEGER PRIMARY KEY, " +
                Data.COL_NAME + " VARCHAR(60), " +
                Data.COL_LASTNAME + " VARCHAR(60), " +
                Data.COL_DATE + " VARCHAR(60) " +
        ")";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + Data.TABLE_NAME;

}
