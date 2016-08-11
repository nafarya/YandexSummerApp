package com.android.yaschenkodanil.yandexsummerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.android.yaschenkodanil.yandexsummerapp.model.Artist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 21.07.16.
 */
public class ArtistDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {MySQLiteHelper.ARTIST_ID,
            MySQLiteHelper.ARTIST_DESCRIPTION, MySQLiteHelper.ARTIST_NAME, MySQLiteHelper.ARTIST_SMALL_IMAGE,
            MySQLiteHelper.ARTIST_BIG_IMAGE, MySQLiteHelper.ARTIST_TRACKS, MySQLiteHelper.ARTIST_ALBUMS,
            MySQLiteHelper.ARTIST_GENRES};

    public ArtistDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Artist createArtist(String id, String description, String name, String smallImage,
                               String bigImage, String tracks, String albums, String genres) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.ARTIST_DESCRIPTION, description);
        values.put(MySQLiteHelper.ARTIST_NAME, name);
        values.put(MySQLiteHelper.ARTIST_SMALL_IMAGE, smallImage);
        values.put(MySQLiteHelper.ARTIST_BIG_IMAGE, bigImage);
        values.put(MySQLiteHelper.ARTIST_TRACKS, tracks);
        values.put(MySQLiteHelper.ARTIST_ALBUMS, albums);
        values.put(MySQLiteHelper.ARTIST_GENRES, genres);
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
        List<Artist> comments = new ArrayList<>();

        Cursor artistCursor = database.query(MySQLiteHelper.TABLE_ARTISTS,
                allColumns, null, null, null, null, null);



        while (artistCursor.moveToNext()) {
            Artist artist = cursorToArtist(artistCursor);
            comments.add(artist);
        }
        artistCursor.close();
        return comments;
    }

    class ArtistCursor extends CursorWrapper {

        private final int ARTISTDESCRIPTION;
        private final int ARTISTNAME;
        private final int ARTISTSMALLIMAGE;
        private final int ARTISTBIGIMAGE;
        private final int ARTISTTRACKS;
        private final int ARTISTALBUMS;
        private final int ARTISTGENRES;
        private final int ARTISTID;

        public ArtistCursor(Cursor c) {
            super(c);
            ARTISTDESCRIPTION = c.getColumnIndex(MySQLiteHelper.ARTIST_DESCRIPTION);
            ARTISTNAME = c.getColumnIndex(MySQLiteHelper.ARTIST_NAME);
            ARTISTSMALLIMAGE = c.getColumnIndex(MySQLiteHelper.ARTIST_SMALL_IMAGE);
            ARTISTBIGIMAGE = c.getColumnIndex(MySQLiteHelper.ARTIST_BIG_IMAGE);
            ARTISTTRACKS = c.getColumnIndex(MySQLiteHelper.ARTIST_TRACKS);
            ARTISTALBUMS = c.getColumnIndex(MySQLiteHelper.ARTIST_ALBUMS);
            ARTISTGENRES = c.getColumnIndex(MySQLiteHelper.ARTIST_GENRES);
            ARTISTID = c.getColumnIndex(MySQLiteHelper.ARTIST_ID);
        }

        public String getDescription() {
            return getString(ARTISTDESCRIPTION);
        }

        public String getName() {
            return getString(ARTISTNAME);
        }

        public String getSmallImage() {
            return getString(ARTISTSMALLIMAGE);
        }

        public String getBigImage() {
            return getString(ARTISTBIGIMAGE);
        }

        public String getTracks() {
            return getString(ARTISTTRACKS);
        }

        public String getAlbums() {
            return getString(ARTISTALBUMS);
        }

        public String getGenres() {
            return getString(ARTISTGENRES);
        }

        public Long getId() {
            return getLong(ARTISTID);
        }
    }

    private Artist cursorToArtist(Cursor cursor) {
        Artist artist = new Artist();
        artist.setId(cursor.getLong(0));
        artist.setDescription(cursor.getString(1));
        artist.setName(cursor.getString(2));
        artist.getCover().setSmallCoverImage(cursor.getString(3));
        artist.getCover().setBigCoverImage(cursor.getString(4));
        artist.setTracks(Long.parseLong(cursor.getString(5)));
        artist.setAlbums(Long.parseLong(cursor.getString(6)));
        artist.setGenresString(cursor.getString(7));
        return artist;
    }


}
