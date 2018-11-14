package com.ptp2.hueapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        this.service.retrieveAllData();
    }
}
