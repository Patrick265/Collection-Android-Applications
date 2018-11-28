package com.ptp2.hueapp.layout.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import com.larswerkman.holocolorpicker.ColorPicker;
import com.ptp2.hueapp.R;
import com.ptp2.hueapp.data.LightData;
import com.ptp2.hueapp.layout.fragment.FragmentConfig;
import com.ptp2.hueapp.model.Light;
import com.ptp2.hueapp.util.ListUtil;
import com.ptp2.hueapp.util.VolleyCallback;
import com.ptp2.hueapp.volley.VolleyService;

import java.util.ArrayList;
import java.util.List;

public class Activity_detailed extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SeekBar hueBar;
    private SeekBar saturationBar;
    private SeekBar brightnessBar;
    private EditText lightName;
    private Switch lightSwitch;
    private ColorPicker picker;
    private int oldHueVal;


    private Light light;
    private VolleyService volleyService;
    private LightData lightData;

    private List<String> categories;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private final FragmentConfig manager = FragmentConfig.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        this.volleyService = VolleyService.getInstance(getApplicationContext());
        this.lightData = LightData.getInstance();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        Light lel = (Light) intent.getSerializableExtra("LIGHT");
        light = this.lightData.getUnAssignedLights().get(lel.getTrueIndex());

        initalise();
        initaliseSpinner();
    }


    private void initalise() {
        this.hueBar = findViewById(R.id.detailed_seekbar_hue);
        this.saturationBar = findViewById(R.id.detailed_seekbar_saturation);
        this.brightnessBar = findViewById(R.id.detailed_seekbar_brightness);
        this.lightName = findViewById(R.id.detailed_light_name);
        this.lightSwitch = findViewById(R.id.detailed_state_switch);
        this.picker = findViewById(R.id.picker);


        this.hueBar.setProgress(this.light.getHue() / 182);
        this.saturationBar.setProgress((int) (this.light.getSaturation() / 2.54));
        this.brightnessBar.setProgress((int) (this.light.getBrightness() / 2.54));

        float hue = light.getHue() / 182;
        float[] hsv = new float[]{hue, 0.9f, 0.9f};
        picker.setColor(Color.HSVToColor(255, hsv));
        oldHueVal = (int) hsv[0] * 182;

        picker.setShowOldCenterColor(false);


        this.lightName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                light.setName(charSequence.toString());
                manager.update();
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
            picker.setClickable(true);
            hueBar.setEnabled(true);
            saturationBar.setEnabled(true);
            brightnessBar.setEnabled(true);
        }
        else
        {
            picker.setClickable(false);
            hueBar.setEnabled(false);
            saturationBar.setEnabled(false);
            brightnessBar.setEnabled(false);
        }


        this.hueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float hue = light.getHue() / 182;
                float[] hsv = new float[]{hue, light.getSaturation()/255f, light.getBrightness()/255f};
                picker.setColor(Color.HSVToColor(255, hsv));
                volleyService.changeColor(light,light.getSaturation(),light.getBrightness(), i * 182, new VolleyCallback() {
                    @Override
                    public void onSucces() {
                        light.setHue(i * 182);
                        manager.update();
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
                float hue = light.getHue() / 182;
                float[] hsv = new float[]{hue, light.getSaturation()/255f, light.getBrightness()/255f};
                picker.setColor(Color.HSVToColor(255, hsv));
                volleyService.changeColor(light, (int)(i * 2.54), light.getBrightness(), light.getHue(), new VolleyCallback() {
                    @Override
                    public void onSucces() {
                        light.setSaturation((int)(i * 2.54));
                        manager.update();
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
                float hue = light.getHue() / 182;
                float[] hsv = new float[]{hue, light.getSaturation()/255f, light.getBrightness()/255f};
                picker.setColor(Color.HSVToColor(255, hsv));
                volleyService.changeColor(light, light.getSaturation(), (int)(i * 2.54), light.getHue(), new VolleyCallback() {
                    @Override
                    public void onSucces() {
                        getLight().setBrightness((int)(i * 2.54));
                        manager.update();
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
                    manager.update();
                    if(light.isTurnedOn())
                    {
                        hueBar.setEnabled(true);
                        saturationBar.setEnabled(true);
                        brightnessBar.setEnabled(true);
                        picker.setEnabled(true);
                    }
                    else
                    {
                        hueBar.setEnabled(false);
                        saturationBar.setEnabled(false);
                        brightnessBar.setEnabled(false);
                        picker.setEnabled(false);
                    }
                }

                @Override
                public void onFail() {

                }
            });
        });


        picker.setOnColorSelectedListener(color -> {
                    if (picker.isEnabled()) {
                        int red = Color.red(color);
                        int green = Color.green(color);
                        int blue = Color.blue(color);
                        float[] hsv1 = new float[3];
                        Color.RGBToHSV(red, green, blue, hsv1);
                        int hue1 = (int) (hsv1[0] * 182);
                        if (Math.abs(hue1 - oldHueVal) > 10) {
                            oldHueVal = hue1;
                            volleyService.changeColor(light, light.getSaturation(), light.getBrightness(), (int) (hsv1[0] * 182), new VolleyCallback() {
                                @Override
                                public void onSucces() {
                                    light.setHue((int) hsv1[0] * 182);
                                    hueBar.setProgress(getLight().getHue() / 182);
                                    manager.update();

                                }

                                @Override
                                public void onFail() {

                                }
                            });
                        }
                    }
                }
        );
    }

    public void initaliseSpinner() {

        this.spinner = findViewById(R.id.detailed_spinner_category);
        this.categories = new ArrayList<>();
        this.categories.add(getApplication().getResources().getString(R.string.living_room));
        this.categories.add(getApplication().getResources().getString(R.string.bedroom));
        this.categories.add(getApplication().getResources().getString(R.string.kitchen));
        this.categories.add(getApplication().getResources().getString(R.string.unassigned));
        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.categories);
        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(this.adapter);
        this.spinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(getIndex(this.spinner, this.light.getCategory()));
    }

    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("LIGHT CATEGORY", this.light.getCategory());

        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        move(item);
        this.light.setCategory(item);
        this.manager.update();
        Log.i("LIGHT CATEGORY", this.light.getCategory());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void move(String newCategory)
    {
        String unassigned = getApplicationContext().getResources().getString(R.string.unassigned);
        String kitchen = getApplicationContext().getResources().getString(R.string.kitchen);
        String livingRoom = getApplicationContext().getResources().getString(R.string.living_room);
        String bedroom = getApplicationContext().getResources().getString(R.string.bedroom);


        if (!this.light.getCategory().equals(unassigned))
        {
            if (this.light.getCategory().equals(kitchen))
            {
                    if(newCategory.equals(livingRoom))
                    {
                        this.lightData.getLivingroomLights().add(this.light);
                        ListUtil.RemoveItem(this.lightData.getKitchenLights(), this.light.getCategory());
                    }
                    else if(newCategory.equals(bedroom))
                    {
                        this.lightData.getBedroomLights().add(this.light);
                        ListUtil.RemoveItem(this.lightData.getKitchenLights(), this.light.getCategory());
                    }
                    else if(newCategory.equals(unassigned))
                    {
                        ListUtil.RemoveItem(this.lightData.getKitchenLights(), this.light.getCategory());
                    }
            } else {
                if (this.light.getCategory().equals(bedroom)) {
                    if (newCategory.equals(livingRoom)) {
                        this.lightData.getLivingroomLights().add(this.light);
                        ListUtil.RemoveItem(this.lightData.getBedroomLights(), this.light.getCategory());
                    } else if (newCategory.equals(kitchen)) {
                        this.lightData.getKitchenLights().add(this.light);
                        ListUtil.RemoveItem(this.lightData.getBedroomLights(), this.light.getCategory());
                    }
                } else {
                    if (this.light.getCategory().equals(livingRoom)) {
                        if (newCategory.equals(bedroom)) {
                            this.lightData.getBedroomLights().add(this.light);
                            ListUtil.RemoveItem(this.lightData.getLivingroomLights(), this.light.getCategory());
                        } else if (newCategory.equals(kitchen)) {
                            this.lightData.getKitchenLights().add(this.light);
                            ListUtil.RemoveItem(this.lightData.getLivingroomLights(), this.light.getCategory());
                        }
                    }
                }


            }
        } else {
                if(newCategory.equals(bedroom))
                {
                    this.lightData.getBedroomLights().add(this.light);
                    ListUtil.RemoveItem(this.lightData.getLivingroomLights(), this.light.getCategory());
                }
                else if(newCategory.equals(livingRoom))
                {
                    this.lightData.getLivingroomLights().add(this.light);
                    ListUtil.RemoveItem(this.lightData.getLivingroomLights(), this.light.getCategory());
                }
                else if(newCategory.equals(kitchen))
                {
                    this.lightData.getKitchenLights().add(this.light);
                    ListUtil.RemoveItem(this.lightData.getLivingroomLights(), this.light.getCategory());
                }
            }
        }
    }
