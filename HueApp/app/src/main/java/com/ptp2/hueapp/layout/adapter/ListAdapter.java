package com.ptp2.hueapp.layout.adapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptp2.hueapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter extends AppCompatActivity {


    private CircleImageView image;
    private ImageView background;
    private TextView lightName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_adapter);
    }

    private void initalise() {
        this.image = findViewById(R.id.adapter_circle_image);
        this.background = findViewById(R.id.adapter_color_light);
        this.lightName= findViewById(R.id.adapter_text_light);
    }
}
