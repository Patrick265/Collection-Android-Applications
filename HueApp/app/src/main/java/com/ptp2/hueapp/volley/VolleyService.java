package com.ptp2.hueapp.volley;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ptp2.hueapp.R;
import com.ptp2.hueapp.model.Light;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolleyService {

    private static VolleyService service;

    private Context context;
    private String requestResponse;
    private List<Light> lights = new ArrayList<>();

    private final String url;
    private final int portNumber;
    private String username;
    private boolean linked;

    private VolleyService(Context context) {
        this.context = context;
        this.portNumber = 80;
        this.url = "http://192.168.0.102" + "/api/";
        this.linked = false;
        this.username = null;
    }


    public static VolleyService getInstance(Context context) {
        if(service == null) {
            service = new VolleyService(context);
        }
        return service;
    }

    public void pair(Activity activity)
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

    public void doJsonRequest(String requestUrl, JSONObject requestBody, int requestMethode) {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        if (requestBody == null) {
            CustomJsonObjectRequest customJsonObjectRequest = new CustomJsonObjectRequest(requestMethode, requestUrl, requestBody, response -> {
                for(int i = 0; i < response.length() + 1; i++)
                {
                    try {
                        JSONObject object = response.getJSONObject(String.valueOf(i)).getJSONObject("state");
                        Light light = new Light(
                                object.getBoolean("on"),
                                object.getInt("sat"),
                                object.getInt("bri"),
                                object.getInt("hue")
                        );
                        lights.add(light);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("WEW", String.valueOf(lights.size()));
            }, error -> Log.d("WEW", error.getStackTrace().toString()));
            queue.add(customJsonObjectRequest);
        }
        else
        {
            CustomJsonArrayRequest customJsonArrayRequest = new CustomJsonArrayRequest(requestMethode, requestUrl, requestBody, response -> {
                try {
                    if(response.get(0).toString().contains("success"))
                    {
                        Log.d("WEW","Toggled the light");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> Log.d("WEW", error.getStackTrace().toString()));
            queue.add(customJsonArrayRequest);
        }
        }

    public void retrieveAllData() {
        String url = this.url + this.username + "/lights";
        Log.i("DATA", url);
        this.doJsonRequest(url,null, Request.Method.GET);
    }


    public void retrieveLightData(int index) {
        String url = this.url + this.username + "/lights/" + index;
        this.doJsonRequest(url,null, Request.Method.GET);
    }

    public void turnOn(Light light, boolean on)
    {
        String url = this.url + this.username + "/lights/" + light.getIndex() + "/state";
        JSONObject body = new JSONObject();
        try {
            body.put("on",on);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.doJsonRequest(url,body, Request.Method.PUT);

        light.setTurnedOn(on);
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
        this.doJsonRequest(url,object, Request.Method.PUT);
        light.setSaturation(saturation);
        light.setBrightness(brightness);
        light.setValue(value);
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
}
