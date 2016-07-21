package com.android.yaschenkodanil.yandexsummerapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.yaschenkodanil.yandexsummerapp.database.ArtistDataSource;
import com.android.yaschenkodanil.yandexsummerapp.model.Artist;
import com.android.yaschenkodanil.yandexsummerapp.parser.MyJsonParser;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by danil on 25.04.16.
 */
public class ArtistsListActivity extends Fragment{
    private DownloadTask downloadTask;
    private RecyclerView mRecyclerView;
    private ArtistAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<Artist> artists;
    private final ArtistDataSource dataSource = new ArtistDataSource(getActivity());

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        artists = new ArrayList<>();
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.activity_artists_list, container, false);

        mRecyclerView = (RecyclerView) linearLayout.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ArtistAdapter(getActivity());


        if (artists.size() == 0) {
            downloadTask = new DownloadTask(this);
            downloadTask.execute();
            for (int i = 0; i < artists.size(); i++) {
                dataSource.createArtist(String.valueOf(artists.get(i).getId()), artists.get(i).getDescription(), artists.get(i).getName());
            }


            //List<Artist> listTestArtist = dataSource.getAllArtists();

        }

        if (savedInstanceState == null) {
            downloadTask = new DownloadTask(this);
            downloadTask.execute();
        } else {
            artists = (List<Artist>) savedInstanceState.getSerializable("listArtist");
            mAdapter.setItems(artists);
        }
        mRecyclerView.setAdapter(mAdapter);
        return linearLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("listArtist", (Serializable) artists);
    }


    public Object onRetainCustomNonConfigurationInstance() {
        return downloadTask;
    }

    public void onClick(View view) {

    }

    private enum Result {
        INPROGRESS, OK, NOARTIST, ERROR
    }

    private class DownloadTask extends AsyncTask<Void, Void, Result> {

        private ArtistsListActivity activity = null;
        private Artist artist = null;
        private Result result = Result.INPROGRESS;

        public DownloadTask(ArtistsListActivity activity) {
            this.activity = activity;
        }

        public void attachActivity(ArtistsListActivity activity) {
            this.activity = activity;
            publishProgress();
        }

        @Override
        protected Result doInBackground(Void... params) {
            Log.i("fxf", "Task started");
            try {

                MyJsonParser parser = new MyJsonParser();

                List<Artist> list = parser.parse();
                Log.i("zaza", "Artists parsed " + list.size());
                if (list == null) {
                    result = Result.ERROR;
                    return result;
                } else if (list.size() == 0) {
                    result = Result.NOARTIST;
                    return result;
                }
                artists.addAll(list);

            } catch (Exception e) {
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
                mAdapter.setItems(artists);
            }
        }

    }


}
