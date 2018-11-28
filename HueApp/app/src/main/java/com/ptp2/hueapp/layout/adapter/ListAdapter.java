package com.ptp2.hueapp.layout.adapter;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptp2.hueapp.R;
import com.ptp2.hueapp.model.Light;
import com.ptp2.hueapp.util.ListOnItemClickListener;

import java.util.ArrayList;
import java.util.List;



public class ListAdapter extends RecyclerView.Adapter<LightViewHolder> {

    private ListOnItemClickListener listener;
    private List<Light> lights = new ArrayList<>();

    public ListAdapter(ListOnItemClickListener listener, List<Light> lights) {
        this.listener = listener;
        this.lights = lights;
    }

    @Override
    public LightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_adapter, parent, false);
        return new LightViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LightViewHolder holder, int position) {
        Light light = this.lights.get(position);

        float hue = light.getHue() / 182;
        float[] hsv = new float[]{hue, light.getSaturation() / 255f,light.getBrightness()/255f};
        holder.getBackground().setBackgroundColor(Color.HSVToColor(255,hsv));
        holder.getLightName().setText(light.getName());
        holder.bindLight(light, this.listener);
    }

    @Override
    public int getItemCount() {
        return lights.size();
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }
}
