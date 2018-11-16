package com.ptp2.hueapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ptp2.hueapp.volley.VolleyService;

public class MainActivity extends AppCompatActivity {


    private VolleyService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalise();
    }

    public void initalise() {
        this.service = VolleyService.getInstance(getApplicationContext());

        Button mainButtonPair = findViewById(R.id.main_par_button);
        mainButtonPair.setOnClickListener(view -> {
            this.service.pair(this);
        });
       // this.service.retrieveAllData();
    }
}
