package com.ptp2.hueapp.layout.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptp2.hueapp.R;
import com.ptp2.hueapp.model.Light;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class allLights_fragment extends AppCompatActivity {

    private List<Light> lights;
    private LinearLayoutManager layoutManager;
    private RecyclerView list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lights_fragment);
    }

    private void initalise() {
        this.lights = new ArrayList<>();

        this.list = findViewById(R.id.all_lights_recycle);
        this.layoutManager = new LinearLayoutManager(this.getApplicationContext());
        this.list.setLayoutManager(this.layoutManager);


    }
}
