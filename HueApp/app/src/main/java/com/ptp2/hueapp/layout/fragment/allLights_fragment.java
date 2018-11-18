package com.ptp2.hueapp.layout.fragment;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptp2.hueapp.R;
import com.ptp2.hueapp.Activity_detailed;
import com.ptp2.hueapp.layout.adapter.ListAdapter;
import com.ptp2.hueapp.model.Light;
import com.ptp2.hueapp.util.ListOnItemClickListener;
import com.ptp2.hueapp.volley.VolleyService;

import java.util.ArrayList;
import java.util.List;

public class allLights_fragment extends Fragment implements ListOnItemClickListener{

    private List<Light> lights = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private RecyclerView list ;
    private ListAdapter adapter;
    private View view;
    private VolleyService service;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.service = VolleyService.getInstance(getContext());

        this.view = inflater.inflate(R.layout.activity_all_lights_fragment, container, false);
        this.list = view.findViewById(R.id.all_lights_recycle);
        this.list.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(view.getContext());
        this.layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.list.setLayoutManager(layoutManager);
        this.adapter = new ListAdapter(light -> {
            Intent intent = new Intent(view.getContext(), Activity_detailed.class);
            intent.putExtra("LIGHT",light);
            startActivity(intent);
        }, this.lights);
        list.setAdapter(adapter);
        return view;
    }

    public void update(List<Light> lights) {
        this.lights = lights;
        this.adapter.setLights(this.lights);
        this.adapter.notifyDataSetChanged();
        this.adapter.notifyItemInserted(this.lights.size() - 1);
    }

    public List<Light> getLights() {
        return lights;
    }

    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    public RecyclerView getList() {
        return list;
    }

    public ListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onItemClick(Light light) {
        Intent intent = new Intent(view.getContext(), Activity_detailed.class);
        intent.putExtra("LIGHT",light);
        startActivity(intent);
    }
}
