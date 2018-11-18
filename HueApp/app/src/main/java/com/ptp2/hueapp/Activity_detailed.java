package com.ptp2.hueapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ptp2.hueapp.model.Light;
import com.ptp2.hueapp.volley.VolleyService;

public class Activity_detailed extends AppCompatActivity {

    Light light;
    VolleyService volleyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        this.volleyService = VolleyService.getInstance(getApplicationContext());


        Intent intent = getIntent();
        light = (Light) intent.getSerializableExtra("LIGHT");

        TextView saturation = findViewById(R.id.txt_sat);
        TextView hue = findViewById(R.id.txt_hue);
        TextView brightness = findViewById(R.id.txt_bri);
        TextView state = findViewById(R.id.txt_state);
        Switch aSwitch = findViewById(R.id.state_switch);

        saturation.setText(String.valueOf(light.getSaturation()));
        hue.setText(String.valueOf(light.getHue()));
        brightness.setText(String.valueOf(light.getBrightness()));
        state.setText(String.valueOf(light.isTurnedOn()));
        aSwitch.setChecked(light.isTurnedOn());

        aSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if(volleyService.turnOn(light,b))
            {
                light.setTurnedOn(b);
            }
        });


    }
}
