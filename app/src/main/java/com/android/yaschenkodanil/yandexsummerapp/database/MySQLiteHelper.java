package com.android.yaschenkodanil.yandexsummerapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by dan on 21.07.16.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_ARTISTS = "artists";
    public static final String ARTIST_ID = BaseColumns._ID;
    public static final String ARTIST_DESCRIPTION = "ARTIST_DESCRIPTION";
    public static final String ARTIST_NAME = "ARTIST_NAME";





    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_ARTISTS + " (id integer primary key not null, desc text, name text not null);";

    public MySQLiteHelper(Context context) {
        super(context, "artists", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS " + "artists");
        onCreate(db);
    }
}
