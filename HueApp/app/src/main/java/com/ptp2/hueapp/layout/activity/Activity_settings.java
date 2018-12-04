package com.ptp2.hueapp.layout.activity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.ptp2.hueapp.R;
import com.ptp2.hueapp.volley.VolleyService;

import java.util.ArrayList;

public class Activity_settings extends AppCompatActivity {

    private TextView textView;
    private VolleyService service;
    private String state;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.service = VolleyService.getInstance(getApplicationContext());
        this.imageView = findViewById(R.id.settingsImage);

        Cursor data = service.getDatabaseHelper().getData();
        textView = findViewById(R.id.settings_name);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }
        if(!listData.isEmpty())
        {
            String welcome = getApplication().getResources().getString(R.string.welcome);
            String info = getApplication().getResources().getString(R.string.editSettings);
            textView.setText(welcome + " " + listData.get(0) + ", " + info);
        }
        Switch themeSwitch = findViewById(R.id.settings_switch_theme);
        SharedPreferences preferences = getSharedPreferences("state", MODE_PRIVATE);
        String status = preferences.getString("stored","");
        if(status.equals("off"))
        {
            themeSwitch.setChecked(false);
            imageView.setImageResource(R.drawable.hueimage);
        }
        else
        {
            themeSwitch.setChecked(true);
            imageView.setImageResource(R.drawable.d);
        }

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences preferences = getSharedPreferences("state", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if(themeSwitch.isChecked())
                {
                    state = "on";
                    imageView.setImageResource(R.drawable.d);
                }
                else
                {
                    state = "off";
                    imageView.setImageResource(R.drawable.hueimage);
                }
                editor.putString("stored", state);
                editor.apply();
            }
        });
    }
}
