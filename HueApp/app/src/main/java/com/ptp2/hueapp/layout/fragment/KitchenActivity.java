package com.ptp2.hueapp.layout.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptp2.hueapp.data.LightData;
import com.ptp2.hueapp.layout.activity.Activity_detailed;
import com.ptp2.hueapp.R;
import com.ptp2.hueapp.layout.adapter.ListAdapter;
import com.ptp2.hueapp.model.Light;
import com.ptp2.hueapp.util.ListOnItemClickListener;
import com.ptp2.hueapp.volley.VolleyService;

import java.util.List;

public class KitchenActivity extends Fragment {

    private LightData lightData;
    private VolleyService service;
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_kitchen, container, false);
        this.lightData = LightData.getInstance();
        this.service = VolleyService.getInstance(getContext());

        this.recyclerView = view.findViewById(R.id.kitchen_recycle);
        this.adapter = new ListAdapter(light -> {
            Intent intent = new Intent(view.getContext(), Activity_detailed.class);
            intent.putExtra("LIGHT",light);
            startActivity(intent);
        }, this.lightData.getKitchenLights());

        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(this.layoutManager);

        return view;
    }

    public ListAdapter getAdapter() {
        return adapter;
    }

}
