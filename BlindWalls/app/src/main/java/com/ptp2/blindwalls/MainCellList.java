package com.ptp2.blindwalls;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptp2.blindwalls.model.BlindWall;
import com.ptp2.blindwalls.util.OnBlindWallClickListener;
import com.squareup.picasso.Picasso;


public class MainCellList extends RecyclerView.Adapter<BlindWallViewHolder> {


    private BlindWallsWrapper wrapper;
    private OnBlindWallClickListener listener;

    public MainCellList(BlindWallsWrapper wrapper, OnBlindWallClickListener listener) {
        this.wrapper = wrapper;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BlindWallViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_cell_list, viewGroup, false);

        return new BlindWallViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BlindWallViewHolder blindWallViewHolder, int i) {
        BlindWall wall = this.wrapper.getBlindWallList().get(i);
        blindWallViewHolder.getPhotographer().setText(wall.getPhotographer());
        blindWallViewHolder.getTitle().setText(wall.getTitle());
        Picasso.get().load("https://api.blindwalls.gallery/" + wall.getImagesUrls().get(0)).into(blindWallViewHolder.getThumbnail());
        blindWallViewHolder.bindWall(wall, this.listener);
    }

    @Override
    public int getItemCount() {
        return this.wrapper.getBlindWallList().size();
    }
}
