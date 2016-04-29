package com.android.yaschenkodanil.yandexsummerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.yaschenkodanil.yandexsummerapp.model.Artist;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ArtistInfoActivity extends AppCompatActivity {

    private static final String EXTRA_ARTIST = "artist";

    private Artist artist;
    private Context caller;

    private TextView largeText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Artist artist = (Artist) bundle.getSerializable(EXTRA_ARTIST);
        if (artist == null) {
            finish();
        }


        setTitle(artist.getName());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        largeText = (TextView) findViewById(R.id.large_text);
        largeText.setText(artist.getDescription());
        imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load(artist.getCover().getSmallCoverImage())
                .into((ImageView) findViewById(R.id.backdrop));

    }

    public static Intent getIntent(Context caller, Artist artist) {
        Intent intent = new Intent(caller, ArtistInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_ARTIST, artist);
        intent.putExtras(bundle);
        return intent;
    }
}
