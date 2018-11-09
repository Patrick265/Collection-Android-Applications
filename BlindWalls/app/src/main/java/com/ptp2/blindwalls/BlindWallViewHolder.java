package com.ptp2.blindwalls;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptp2.blindwalls.model.BlindWall;
import com.ptp2.blindwalls.util.OnBlindWallClickListener;

public class BlindWallViewHolder extends RecyclerView.ViewHolder {


    private TextView title;
    private TextView photographer;
    private ImageView thumbnail;


    public BlindWallViewHolder(View view) {
        super(view);
        this.title = view.findViewById(R.id.Cell_Title);
        this.photographer = view.findViewById(R.id.Cell_Author);
        this.thumbnail = view.findViewById(R.id.Cell_Image);
    }


    public void bindWall(final BlindWall wall, final OnBlindWallClickListener listener) {
        super.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onItemClick(wall);
            }
        });
    }


    public TextView getTitle() {
        return title;
    }

    public TextView getPhotographer() {
        return photographer;
    }

    public ImageView getThumbnail() {
        return thumbnail;
    }
}
