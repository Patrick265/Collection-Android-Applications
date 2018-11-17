package com.ptp2.hueapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.ptp2.hueapp.layout.adapter.PageAdapter;
import com.ptp2.hueapp.layout.fragment.allLights_fragment;
import com.ptp2.hueapp.volley.VolleyService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private VolleyService service;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    private allLights_fragment allFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalise();
    }

    public void initalise() {
        this.service = VolleyService.getInstance(getApplicationContext());
        this.allFragment = new allLights_fragment();
        this.service.getFragments().add(this.allFragment);



        this.tabLayout = findViewById(R.id.main_tab_layout);
        this.viewPager = findViewById(R.id.main_view_pager);
        this.pageAdapter = new PageAdapter(getSupportFragmentManager());
        this.pageAdapter.add(this.allFragment, "All Lights");

        this.viewPager.setAdapter(pageAdapter);
        this.tabLayout.setupWithViewPager(this.viewPager);

        Button mainButtonPair = findViewById(R.id.main_par_button);
        mainButtonPair.setOnClickListener(view -> {
            this.service.pair(this, this.allFragment);
        });
    }

}
