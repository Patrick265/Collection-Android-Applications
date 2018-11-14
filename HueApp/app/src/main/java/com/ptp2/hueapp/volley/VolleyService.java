package com.ptp2.hueapp.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ptp2.hueapp.model.Light;

public class VolleyService {

    private static VolleyService service;

    private Context context;
    private String requestResponse;

    private final String url;
    private final int portNumber;
    private String username;

    private VolleyService(Context context) {
        this.context = context;
        this.portNumber = 80;
        this.url = "https://localhost:" + portNumber + "/api/";
        this.username = "17203f23f6c3c4e527ab7a73acaf94a";
    }


    public static VolleyService getInstance(Context context) {
        if(service == null) {
            service = new VolleyService(context);
        }
        return service;
    }

    public void doRequest(String requestUrl, final String requestBody, int requestMethode) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        StringRequest stringRequest = new StringRequest(requestMethode, requestUrl, (String response) -> {
            Log.i("ResponseVolley", response);
            this.requestResponse = response;
        }, (VolleyError error) -> {
            Log.e("VOLLEYERROR", "ERROR WITH VOLLEY");
            error.printStackTrace();
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
    }

    public void retrieveAllData() {
        String url = this.url + this.username + "/lights";
        Log.i("DATA", url);
        this.doRequest(url,null, Request.Method.GET);
    }


    public void retrieveLigthData(int index) {
        String url = this.url + this.username + "/lights/" + index;
        this.doRequest(url,null, Request.Method.GET);
    }

    public void turnOn(Light light, boolean on)
    {
        String url = this.url + this.username + "/lights/" + light.getIndex() + "/state";
        String body = "{ \"on\":" + on + "}";
        this.doRequest(url,body, Request.Method.PUT);
        light.setTurnedOn(on);
    }

    public void changeColor(Light light, int saturation, int brightness, int value) {
        String url = this.url + this.username + "/lights/" + light.getIndex() + "/state";
        String body = "{\"on\":" + true + "," + "\"sat\":" + saturation + "," + "\"bri\": " + brightness + "," + "\"hue\":" + value + "}";

        light.setSaturation(saturation);
        light.setBrightness(brightness);
        light.setValue(value);
    }


    public String getUrl() {
        return url;
    }

    public int getPortNumber() {
        return portNumber;
    }
}
