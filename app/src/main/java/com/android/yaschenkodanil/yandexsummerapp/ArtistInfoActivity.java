package com.android.yaschenkodanil.yandexsummerapp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.android.yaschenkodanil.yandexsummerapp.database.ArtistDataSource;
import com.android.yaschenkodanil.yandexsummerapp.model.Artist;

public class ArtistInfoActivity extends FragmentActivity {

    private static final String EXTRA_ARTIST = "artist";
    private HeadSetReciever headSetReciever = new HeadSetReciever();
    private ArtistDataSource dataSource = new ArtistDataSource(this);

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headSetReciever, filter);

        IntentFilter intentFilterClickMusic = new IntentFilter(HeadSetReciever.MUSICBUTTON);
        registerReceiver(headSetReciever, intentFilterClickMusic);

        IntentFilter intentFilterClickRadio = new IntentFilter(HeadSetReciever.RADIOBUTTON);
        registerReceiver(headSetReciever, intentFilterClickRadio);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(headSetReciever);
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        ArtistsListActivity firstFragment = new ArtistsListActivity();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, firstFragment)
                .commit();

        findViewById(R.id.toolbarImageButtonMailId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                    .setType("text/plain")
                                    .putExtra(Intent.EXTRA_EMAIL, new String[]{"danyaschenko@gmail.com"})
                                    .putExtra(Intent.EXTRA_SUBJECT, "super-app")
                                    .putExtra(Intent.EXTRA_TEXT, "best eu"),
                            "Send email...")
                );
            }
        });

        findViewById(R.id.toolbarImageButtonInfoId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoFragment infoFragment = new InfoFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, infoFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    public static Intent getIntent(Context caller, Artist artist) {
        Intent intent = new Intent(caller, ArtistInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_ARTIST, artist);
        intent.putExtras(bundle);
        return intent;
    }
}
