package com.ptp2.hueapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ptp2.hueapp.model.Light;

public class Activity_detailed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Intent intent = getIntent();
        Light light = (Light) intent.getSerializableExtra("LIGHT");

        TextView saturation = findViewById(R.id.txt_sat);
        TextView hue = findViewById(R.id.txt_hue);
        TextView brightness = findViewById(R.id.txt_bri);
        TextView state = findViewById(R.id.txt_state);

        saturation.setText(String.valueOf(light.getSaturation()));
        hue.setText(String.valueOf(light.getHue()));
        brightness.setText(String.valueOf(light.getBrightness()));
        state.setText(String.valueOf(light.isTurnedOn()));


    }
}
