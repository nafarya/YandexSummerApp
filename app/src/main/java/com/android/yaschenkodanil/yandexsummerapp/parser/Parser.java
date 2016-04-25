package com.android.yaschenkodanil.yandexsummerapp.parser;

import android.util.JsonReader;
import android.util.Log;

import com.android.yaschenkodanil.yandexsummerapp.model.Artist;
import com.android.yaschenkodanil.yandexsummerapp.model.Cover;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniil on 25.04.16.
 */
public class Parser {

    public Parser () {
    }

    public List<Artist> parse() throws IOException, URISyntaxException {
        List<Artist> list = new ArrayList<>();
        int countedEvents = 0, add = 0;
        try {
            URL url = new URL("https://download.cdn.yandex.net/mobilization-2016/artists.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            list.addAll(getAllEvents(reader));
            if (inputStream != null) {
                inputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
        } catch (Exception e) {

            Log.d("LOL", "ERROR IN PARSER" + e);
            throw e;
        }
        return list;
    }

    private List<Artist> getAllEvents(JsonReader reader) throws IOException, URISyntaxException {
        List<Artist> list = new ArrayList<>();
        Artist artist;
//        reader.beginObject();
        reader.beginArray();

        while (reader.hasNext()) {
            artist = getNextArtist(reader);
            list.add(artist);

        }
        reader.endArray();
        return list;
    }

    private Artist getNextArtist(JsonReader reader) throws IOException, URISyntaxException {
        Artist artist = new Artist();
        reader.beginObject();
        while (reader.hasNext()) {
            String term = reader.nextName();
            if (term.equals("id")) {
                artist.setId(reader.nextLong());
            } else if (term.equals("tracks")) {
                artist.setTracks(reader.nextLong());
            } else if (term.equals("albums")) {
                artist.setAlbums(reader.nextLong());
            } else if (term.equals(("link"))) {
                artist.setLink(reader.nextString());
            } else if (term.equals("description")) {
                artist.setDescription(reader.nextString());
            } else if (term.equals("name")) {
                artist.setName(reader.nextString());
            } else if (term.equals("genres")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    artist.addGenre(reader.nextString());
                }
                reader.endArray();
            } else if (term.equals("cover")) {
                reader.beginObject();
                while (reader.hasNext()) {
                    if (reader.nextName().equals("small")) {
                        artist.getCover().setSmallCoverImage(reader.nextString());
                    } else {
                        artist.getCover().setBigCoverImage(reader.nextString());
                    }
                }
                reader.endObject();
            }

        }
        reader.endObject();
        return artist;
    }
}

