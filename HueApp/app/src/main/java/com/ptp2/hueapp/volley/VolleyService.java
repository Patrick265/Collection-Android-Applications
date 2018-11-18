package com.ptp2.hueapp.volley;

import android.app.Activity;
import android.content.Context;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ptp2.hueapp.R;
import com.ptp2.hueapp.layout.fragment.allLights_fragment;
import com.ptp2.hueapp.model.Light;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class VolleyService {

    private static VolleyService service;

    private Context context;
    private String requestResponse;
    private List<Light> lights;

    private final String url;
    private final int portNumber;
    private String username;
    private boolean linked;
    private List<Fragment> fragments;
    private boolean succes = false;

    private VolleyService(Context context) {
        this.lights = new ArrayList<>();
        this.context = context;
        this.portNumber = 80;
        this.url = "http://192.168.0.102" + "/api/";
        this.linked = false;
        this.username = null;
        this.fragments = new ArrayList<>();

    }


    public static VolleyService getInstance(Context context) {
        if(service == null) {
            service = new VolleyService(context);
        }
        return service;
    }

    public void pair(Activity activity, allLights_fragment fragment)
    {
        JSONObject body = new JSONObject();
        try {
            body.put("devicetype","PatrickTom");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(this.context);
        CustomJsonArrayRequest customJsonArrayRequest = new CustomJsonArrayRequest(Request.Method.POST, url, body, response -> {
            try {
                Button pairButton = activity.findViewById(R.id.main_par_button);
                String status = response.get(0).toString();
                if(status.contains("success"))
                {
                    username = response.getJSONObject(0).getJSONObject("success").getString("username");
                    linked = true;
                    pairButton.setText("Succesfully paired");
                    pairButton.setAlpha(0.5f);
                    pairButton.setEnabled(false);
                    retrieveAllData();
                }
                else
                {
                    pairButton.setText("Error pairing");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("WEW", error.getStackTrace().toString()));

        queue.add(customJsonArrayRequest);
    }

    public void doJsonObjectRequest(String requestUrl, JSONObject requestBody, int requestMethode) {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        if (requestBody == null) {
            CustomJsonObjectRequest customJsonObjectRequest = new CustomJsonObjectRequest(requestMethode, requestUrl, requestBody, response -> {
                for (int i = 1; i < response.length() + 1; i++) {
                    try {
                        JSONObject object = response.getJSONObject(String.valueOf(i)).getJSONObject("state");
                        Light light = new Light("Light", object.getBoolean("on"),
                                object.getInt("sat"),
                                object.getInt("bri"),
                                object.getInt("hue"),
                                i
                        );
                        this.lights.add(light);
                        Log.d("WEW", String.valueOf(lights.size()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("WEWFINALFRAG", String.valueOf(this.fragments.size()));
                allLights_fragment fragment = (allLights_fragment) this.fragments.get(0);
                fragment.getLights().clear();
                fragment.update(this.lights);
            }, error -> Log.d("WEW", error.getStackTrace().toString()));
            queue.add(customJsonObjectRequest);
        }
    }

    public boolean doJsonArrayRequest(String requestUrl, JSONObject requestBody, int requestMethode) {
        succes = false;
        RequestQueue queue = Volley.newRequestQueue(this.context);
        CustomJsonArrayRequest customJsonArrayRequest = new CustomJsonArrayRequest(requestMethode, requestUrl, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    if (response.get(0).toString().contains("success")) {
                        succes = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Log.d("WEW", error.getStackTrace().toString()));
        queue.add(customJsonArrayRequest);
        return succes;
    }

    public void retrieveAllData() {
        String url = this.url + this.username + "/lights";
        Log.i("DATA", url);
        this.doJsonObjectRequest(url,null, Request.Method.GET);
    }


    public void retrieveLightData(int index) {
        String url = this.url + this.username + "/lights/" + index;
        this.doJsonObjectRequest(url,null, Request.Method.GET);
    }

    public boolean turnOn(Light light, boolean on)
    {
        String url = this.url + this.username + "/lights/" + light.getIndex()  + "/state";
        JSONObject body = new JSONObject();
        try {
            body.put("on",on);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(this.doJsonArrayRequest(url,body, Request.Method.PUT))
        {
            return true;
        }
        return false;
    }

    public void changeColor(Light light, int saturation, int brightness, int value) {
        String url = this.url + this.username + "/lights/" + light.getIndex() + "/state";

        JSONObject object = new JSONObject();
        try {
            object.put("on", true);
            object.put("sat", saturation);
            object.put("bri", brightness);
            object.put("hue", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.doJsonObjectRequest(url,object, Request.Method.PUT);
        light.setSaturation(saturation);
        light.setBrightness(brightness);
        light.setHue(value);
    }

    public boolean isLinked() {
        return linked;
    }

    public String getUrl() {
        return url;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public List<Light> getLights() {
        return lights;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }
}
