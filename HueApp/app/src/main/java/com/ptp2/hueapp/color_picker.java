package com.ptp2.hueapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;
import com.ptp2.hueapp.model.Light;
import com.ptp2.hueapp.util.VolleyCallback;
import com.ptp2.hueapp.volley.VolleyService;

import java.util.Timer;

public class color_picker extends AppCompatActivity {

    ColorPicker picker;
    VolleyService volleyService;
    int i;
    Timer timer;
    int hueVal;
    int oldHueVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        this.volleyService = VolleyService.getInstance(getApplicationContext());

        picker = (ColorPicker) findViewById(R.id.picker);

        Intent intent = getIntent();
        Light light = (Light) intent.getSerializableExtra("COLOR");
        i = light.getIndex();
        Light b = volleyService.getLights().get(i - 1);
        float hue = light.getHue() / 182;
        float[] hsv = new float[]{hue, 0.9f, 0.9f};
        picker.setColor(Color.HSVToColor(255, hsv));
        oldHueVal = (int) hsv[0] * 182;


        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                    int red = Color.red(color);
                    int green = Color.green(color);
                    int blue = Color.blue(color);
                    float[] hsv = new float[3];
                    Color.RGBToHSV(red, green, blue, hsv);
                    int hue = (int) (hsv[0] * 182);
                    if(Math.abs(hue - oldHueVal) > 1000)
                    {
                        oldHueVal = hue;
                        volleyService.changeColor(b, b.getSaturation(), b.getBrightness(), (int) (hsv[0] * 182), new VolleyCallback() {
                            @Override
                            public void onSucces() {
                                Log.d("WEW", "WOW");
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
}
