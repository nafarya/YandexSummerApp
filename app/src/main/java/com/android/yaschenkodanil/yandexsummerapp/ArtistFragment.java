package com.android.yaschenkodanil.yandexsummerapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportActionModeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.net.URL;

/**
 * Created by dan on 18.07.16.
 */
public class ArtistFragment extends Fragment {

    private TextView large_text;
    private ImageView imageView;
    private ImageButton imageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_scrolling, container, false);
        String strtext = getArguments().getString("bio");
        String strpic =getArguments().getString("pic");
        large_text = (TextView) view.findViewById(R.id.large_text);
        imageView = (ImageView) view.findViewById(R.id.backdrop);
        Picasso.with(getActivity()).load(strpic).into((ImageView) view.findViewById(R.id.backdrop));
        large_text.setText(strtext);
        return view;
    }
}
