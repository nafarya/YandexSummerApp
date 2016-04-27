package com.android.yaschenkodanil.yandexsummerapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.yaschenkodanil.yandexsummerapp.model.Artist;
import com.android.yaschenkodanil.yandexsummerapp.parser.MyJsonParser;

import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danil on 25.04.16.
 */
public class ArtistsListActivity extends AppCompatActivity{
    private DownloadTask downloadTask;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Artist> artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_list);

//        IntentFilter filter = new IntentFilter(DataBaseInitService.UPDATE_IS_READY);
//        receiver = new BroadcastReceiver()
//        {
//            @Override
//            public void onReceive(Context context, Intent intent)
//            {
//                changeList();
//            }
//        };
//        registerReceiver(receiver, filter);
//
//        if (UPDATE_IN_PROGRESS) {
//            createDialogForUpdate();
//            updateDialogForUpdate(LAST_ANSWER_FROM_SERVICE);;
//        }
//
//        eventKeeper = EventKeeper.getInstance(getApplicationContext());
//        events = eventKeeper.getEasyEvents();
//

        artists = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            downloadTask = (DownloadTask) getLastCustomNonConfigurationInstance();
        }

        if (savedInstanceState == null) {
            downloadTask = new DownloadTask(this);
            downloadTask.execute();
        } else {
            downloadTask.attachActivity(this);
        }
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
