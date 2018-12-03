package com.ptp2.hueapp.layout.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ptp2.hueapp.R;
import com.ptp2.hueapp.volley.VolleyService;

import java.util.ArrayList;

public class Activity_settings extends AppCompatActivity {

    private TextView textView;
    private VolleyService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.service = VolleyService.getInstance(getApplicationContext());

        Cursor data = service.getDatabaseHelper().getData();
        textView = findViewById(R.id.settings_name);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }
        if(!listData.isEmpty())
        {
            textView.setText(listData.get(0));
        }
    }
}
