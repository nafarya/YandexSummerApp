package com.android.yaschenkodanil.yandexsummerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.android.yaschenkodanil.yandexsummerapp.model.Artist;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 21.07.16.
 */
public class ArtistDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = new String[]{MySQLiteHelper.ARTIST_ID,
            MySQLiteHelper.ARTIST_DESCRIPTION, MySQLiteHelper.ARTIST_NAME};

    public ArtistDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Artist createArtist(String id, String description, String name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.ARTIST_DESCRIPTION, description);
        values.put(MySQLiteHelper.ARTIST_NAME, name);
        long insertId = database.insert(MySQLiteHelper.TABLE_ARTISTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ARTISTS,
                allColumns, MySQLiteHelper.ARTIST_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Artist artist= cursorToArtist(cursor);
        cursor.close();
        return artist;
    }

    public List<Artist> getAllArtists() {
        List<Artist> comments = new ArrayList<Artist>();


        Cursor cursor = database.query(MySQLiteHelper.TABLE_ARTISTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Artist artist = cursorToArtist(cursor);
            comments.add(artist);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Artist cursorToArtist(Cursor cursor) {
        Artist artist = new Artist();
        artist.setId(cursor.getLong(0));
        artist.setDescription(cursor.getString(1));
        artist.setName(cursor.getString(2));
        return artist;
    }


}
