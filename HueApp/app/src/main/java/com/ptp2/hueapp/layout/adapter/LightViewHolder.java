package com.ptp2.hueapp.layout.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptp2.hueapp.util.ListOnItemClickListener;
import com.ptp2.hueapp.R;
import com.ptp2.hueapp.model.Light;

import de.hdodenhof.circleimageview.CircleImageView;

public class LightViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView image;
    private View background;
    private TextView lightName;


    public LightViewHolder(View view) {
        super(view);
        this.image = view.findViewById(R.id.adapter_circle_image);
        this.background = view.findViewById(R.id.adapter_background);
        this.lightName= view.findViewById(R.id.adapter_text_light);
    }


    public void bindLight(final Light light, final ListOnItemClickListener listener) {
        super.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onItemClick(light);
            }
        });
    }

    public View getBackground() {
        return background;
    }

    public CircleImageView getImage() {
        return image;
    }


    public TextView getLightName() {
        return lightName;
    }
}
