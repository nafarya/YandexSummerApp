package com.android.yaschenkodanil.yandexsummerapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.yaschenkodanil.yandexsummerapp.database.ArtistDataSource;
import com.android.yaschenkodanil.yandexsummerapp.model.Artist;
import com.android.yaschenkodanil.yandexsummerapp.parser.MyJsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danil on 25.04.16.
 */
public class ArtistsListActivity extends Fragment {
    private DownloadTask downloadTask;
    private ArtistAdapter adapter;
    private List<Artist> artists;
    private ArtistDataSource dataSource = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        artists = new ArrayList<>();
        View linearLayout = inflater.inflate(R.layout.activity_artists_list, container, false);
        RecyclerView recyclerView = (RecyclerView) linearLayout.findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ArtistAdapter(getActivity());

        dataSource = new ArtistDataSource(getActivity());
        dataSource.open();
        List<Artist> temporaryListOfArtist = dataSource.getAllArtists();
        if (temporaryListOfArtist.isEmpty()) {
            downloadTask = new DownloadTask();
            downloadTask.execute();
        } else {
            artists.addAll(temporaryListOfArtist);
            adapter.setItems(artists);
        }

        if (savedInstanceState == null) {
            dataSource.open();
            artists.addAll(dataSource.getAllArtists());
            dataSource.close();
        } else {
            artists = (List<Artist>) savedInstanceState.getSerializable("listArtist");
            adapter.setItems(artists);
        }

        recyclerView.setAdapter(adapter);
        return linearLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("listArtist", (Serializable) artists);
    }

    private enum Result {
        INPROGRESS, OK, NOARTIST, ERROR
    }

    private class DownloadTask extends AsyncTask<Void, Void, Result> {

        private Result result = Result.INPROGRESS;
        private final int NUMOFARTISTS = 317;

        @Override
        protected Result doInBackground(Void... params) {

            dataSource = new ArtistDataSource(getActivity());
            dataSource.open();
            try {
                MyJsonParser parser = new MyJsonParser();
                List<Artist> list = parser.parse();
                if (list.size() == NUMOFARTISTS) {
                    for (int i = 0; i < list.size(); i++) {
                        dataSource.createArtist(String.valueOf(list.get(i).getId()),
                                list.get(i).getDescription(),
                                list.get(i).getName(),
                                list.get(i).getCover().getSmallCoverImage(),
                                list.get(i).getCover().getBigCoverImage(),
                                String.valueOf(list.get(i).getTracks()),
                                String.valueOf(list.get(i).getAlbums()),
                                list.get(i).getGenres());
                    }
                    dataSource.close();
                }
                if (list.size() == 0) {
                    result = Result.NOARTIST;
                    return result;
                }
                artists.addAll(list);
            } catch (Exception e) {
                Log.i("ArtistListActivity", e.toString());
                return Result.ERROR;
            }
            result = Result.OK;
            return result;
        }

        @Override
        protected void onPostExecute(Result res) {
            result = res;
            updateUI();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            updateUI();
        }

        private void updateUI() {
            if (result == Result.OK) {
                adapter.setItems(artists);
            }
        }
    }
}
