package com.ptp2.hueapp.layout.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.ptp2.hueapp.R;
import com.ptp2.hueapp.layout.adapter.PageAdapter;
import com.ptp2.hueapp.volley.VolleyService;
import com.ptp2.hueapp.layout.fragment.FragmentConfig;


public class MainActivity extends AppCompatActivity {


    private VolleyService service;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    private Button main;
    private Button mainSim;
    private FragmentConfig manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalise();

        this.viewPager.setOffscreenPageLimit(3);
    }

    public void initalise() {
        this.service = VolleyService.getInstance(getApplicationContext());
        this.manager = FragmentConfig.getInstance();

        this.tabLayout = findViewById(R.id.main_tab_layout);
        this.viewPager = findViewById(R.id.main_view_pager);
        this.pageAdapter = new PageAdapter(getSupportFragmentManager());
        this.pageAdapter.add(this.manager.getAllFragment(), getApplicationContext().getResources().getString(R.string.all_lights));
        this.pageAdapter.add(this.manager.getKitchenFragment(), getApplicationContext().getResources().getString(R.string.kitchen));
        this.pageAdapter.add(this.manager.getBedroomFragment(), getApplicationContext().getResources().getString(R.string.bedroom));
        this.pageAdapter.add(this.manager.getLivingRoomFragment(), getApplicationContext().getResources().getString(R.string.living_room));

        this.viewPager.setAdapter(pageAdapter);
        this.tabLayout.setupWithViewPager(this.viewPager);

        this.mainSim = findViewById(R.id.main_par_button_simulator);
        mainSim.setOnClickListener(view -> {
            this.service.pair(this);


        });

        this.main = findViewById(R.id.main_par_button);
        this.main.setOnClickListener(view -> {
            this.service.retrieveAllData(this, true);
        });

    }

}
