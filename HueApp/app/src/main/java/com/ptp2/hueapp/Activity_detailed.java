package com.ptp2.hueapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.ptp2.hueapp.model.Light;
import com.ptp2.hueapp.util.VolleyCallback;
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
        Light lel = (Light) intent.getSerializableExtra("LIGHT");
        light = volleyService.getLights().get(lel.getIndex() - 1);
        initalise();
        initaliseSpinner();
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    private void initalise() {
        this.hueBar = findViewById(R.id.detailed_seekbar_hue);
        this.saturationBar = findViewById(R.id.detailed_seekbar_saturation);
        this.brightnessBar = findViewById(R.id.detailed_seekbar_brightness);
        this.lightName = findViewById(R.id.detailed_light_name);
        this.lightSwitch = findViewById(R.id.detailed_state_switch);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), color_picker.class);
                intent.putExtra("COLOR",light);
                startActivity(intent);
            }
        });

        this.hueBar.setProgress(this.light.getHue() / 182);
        this.saturationBar.setProgress((int) (this.light.getSaturation() / 2.55));
        this.brightnessBar.setProgress((int) (this.light.getBrightness() / 2.55));

        this.lightName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                light.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        if(this.light.getName() != null ) {
            this.lightName.setText(this.light.getName());
        } else {
            this.lightName.setText("");
        }

        if(light.isTurnedOn())
        {
            hueBar.setEnabled(true);
            saturationBar.setEnabled(true);
            brightnessBar.setEnabled(true);
        }
        else
        {
            hueBar.setEnabled(false);
            saturationBar.setEnabled(false);
            brightnessBar.setEnabled(false);
        }


        this.hueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                volleyService.changeColor(light,light.getSaturation(),light.getBrightness(), i * 182, new VolleyCallback() {
                    @Override
                    public void onSucces() {
                        getLight().setHue(i * 182);
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.saturationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                volleyService.changeColor(light, (int)(i * 2.55), light.getBrightness(), light.getHue(), new VolleyCallback() {
                    @Override
                    public void onSucces() {
                        getLight().setSaturation((int)(i * 2.55));
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                volleyService.changeColor(light, light.getSaturation(), (int)(i * 2.55), light.getHue(), new VolleyCallback() {
                    @Override
                    public void onSucces() {
                        getLight().setBrightness((int)(i * 2.55));
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.lightSwitch.setChecked(light.isTurnedOn());

        this.lightSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            light.setTurnedOn(b);
            volleyService.turnOn(light, b, new VolleyCallback() {
                @Override
                public void onSucces() {
                    if(light.isTurnedOn())
                    {
                        hueBar.setEnabled(true);
                        saturationBar.setEnabled(true);
                        brightnessBar.setEnabled(true);
                    }
                    else
                    {
                        hueBar.setEnabled(false);
                        saturationBar.setEnabled(false);
                        brightnessBar.setEnabled(false);
                    }
                }

                @Override
                public void onFail() {

                }
            });
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
