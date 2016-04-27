package com.android.yaschenkodanil.yandexsummerapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.yaschenkodanil.yandexsummerapp.model.Artist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danil on 25.04.16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Artist> mDataset;
    private Context context;

    public RecyclerViewAdapter(Context context) {
        mDataset = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        // change layout's params. here

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void setItems(List<Artist> items) {
        if (mDataset != null) {
            int x = mDataset.size();
            mDataset.clear();
            notifyItemRangeRemoved(0, x);
        }
        if (items != null && mDataset != null) {
            mDataset.addAll(items);
            notifyItemRangeInserted(0, items.size());
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mDataset.get(position).getName());
        holder.genres.setText(mDataset.get(position).getGenres());
        holder.info.setText(mDataset.get(position).getInfo());
        holder.artistId.setText(String.valueOf(mDataset.get(position).getId()));
//        holder.art.setText(String.valueOf(mDataset.get(position).getId()));
        final String cover = mDataset.get(position).getCover().getSmallCoverImage();
        if (cover != null && !cover.isEmpty()) {
            Picasso.with(context).load(cover).into(holder.image);
        } else {
            Log.d("azaza", mDataset.get(position).toString());
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView genres;
        public TextView info;
        public TextView artistId;
        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.recycler_item_name);
            genres = (TextView) v.findViewById(R.id.recycler_item_genres);
            image = (ImageView) v.findViewById(R.id.recycler_item_image);
            info  = (TextView) v.findViewById(R.id.recycler_item_additional_info);
            artistId  = (TextView) v.findViewById(R.id.artist_id);
        }
    }
}