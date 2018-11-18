package com.ptp2.hueapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.ptp2.hueapp.model.Light;
import com.ptp2.hueapp.volley.VolleyService;

import java.util.ArrayList;
import java.util.List;

public class Activity_detailed extends AppCompatActivity {

    private SeekBar hueBar;
    private SeekBar saturationBar;
    private SeekBar brightnessBar;
    private EditText lightName;
    private Switch lightSwitch;

    private Light light;
    private VolleyService volleyService;

    private List<String> categories;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        this.volleyService = VolleyService.getInstance(getApplicationContext());


        Intent intent = getIntent();
        light = (Light) intent.getSerializableExtra("LIGHT");
        initalise();
        initaliseSpinner();
    }

    private void initalise() {
        this.hueBar = findViewById(R.id.detailed_seekbar_hue);
        this.saturationBar = findViewById(R.id.detailed_seekbar_saturation);
        this.brightnessBar = findViewById(R.id.detailed_seekbar_brightness);
        this.lightName = findViewById(R.id.detailed_light_name);
        this.lightSwitch = findViewById(R.id.detailed_state_switch);

        this.hueBar.setProgress(this.light.getHue() / 182);
        this.saturationBar.setProgress((int) (this.light.getSaturation() / 2.55));
        this.brightnessBar.setProgress((int) (this.light.getBrightness() / 2.55));

        if(this.light.getName() != null ) {
            this.lightName.setText(this.light.getName());
        } else {
            this.lightName.setText("");
        }


        this.lightSwitch.setChecked(light.isTurnedOn());

        this.lightSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if(volleyService.turnOn(light,b))
            {
                light.setTurnedOn(b);
            }
        });
    }

    public void initaliseSpinner() {

        this.spinner = findViewById(R.id.detailed_spinner_category);
        this.categories = new ArrayList<>();
        this.categories.add("Living room");
        this.categories.add("Kitchen");
        this.categories.add("Bedroom");
        this.categories.add("unassigned");
        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.categories);
        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(this.adapter);

    }
}
