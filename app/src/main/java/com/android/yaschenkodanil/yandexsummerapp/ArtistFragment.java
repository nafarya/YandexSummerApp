package com.android.yaschenkodanil.yandexsummerapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by dan on 18.07.16.
 */
public class ArtistFragment extends Fragment {

    private TextView largeText;
    private ImageView imageView;
    private ImageButton imageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_scrolling, container, false);
        Bundle bundle = getArguments();
        String strtext = bundle.getString("bio");
        String strpic = bundle.getString("pic");
        largeText = (TextView) view.findViewById(R.id.large_text);
        imageView = (ImageView) view.findViewById(R.id.backdrop);
        Picasso.with(getActivity()).load(strpic).into((ImageView) view.findViewById(R.id.backdrop));
        largeText.setText(strtext);
        return view;
    }
}
