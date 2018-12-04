package com.ptp2.hueapp.volley;

import android.app.Activity;
import android.content.Context;

import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ptp2.hueapp.R;
import com.ptp2.hueapp.data.LightData;
import com.ptp2.hueapp.database.DatabaseHelper;
import com.ptp2.hueapp.layout.fragment.FragmentConfig;
import com.ptp2.hueapp.model.Light;
import com.ptp2.hueapp.util.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VolleyService {

    private static VolleyService service;

    private LightData lightData;
    private Context context;
    private String url;
    private final String ipAdress;
    private final int portNumber;
    private String username;
    private boolean linked;
    private RequestQueue queue;
    private FragmentConfig manager;
    private String requestResponse;
    private DatabaseHelper databaseHelper;

    private VolleyService(Context context) {
        this.context = context;
        this.ipAdress = "192.168.0.102";
        this.portNumber = 80;
        this.url = "http:/" + ipAdress + "/api/";
        this.linked = false;
        this.username = "iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB";
        this.manager = FragmentConfig.getInstance();
        this.queue = Volley.newRequestQueue(this.context);
        this.lightData = LightData.getInstance();
        this.databaseHelper = new DatabaseHelper(context);
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
        CustomJsonArrayRequest customJsonArrayRequest = new CustomJsonArrayRequest(Request.Method.POST, url, body, response -> {
            try {
                Button pairSimButton = activity.findViewById(R.id.main_par_button_simulator);
                String status = response.get(0).toString();
                if(status.contains("success"))
                {
                    username = response.getJSONObject(0).getJSONObject("success").getString("username");
                    linked = true;
                    pairSimButton.setText(context.getResources().getString(R.string.successpair));
                    pairSimButton.setAlpha(0.5f);
                    pairSimButton.setEnabled(false);

                    Button pairButton = activity.findViewById(R.id.main_par_button);
                    pairButton.setText(context.getResources().getString(R.string.successpair));
                    pairButton.setAlpha(0.5f);
                    pairButton.setEnabled(false);

                    retrieveAllData(activity, false);

                }
                else
                {
                    pairSimButton.setText(context.getResources().getString(R.string.errorpair));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("WEW", error.getStackTrace().toString()));

        queue.add(customJsonArrayRequest);
    }

    public void doJsonObjectRequest(String requestUrl, JSONObject requestBody, int requestMethode) {
        if (requestBody == null) {
            this.lightData.getUnAssignedLights().clear();
            this.lightData.getLivingroomLights().clear();
            this.lightData.getBedroomLights().clear();
            this.lightData.getKitchenLights().clear();
            this.manager.update();
            CustomJsonObjectRequest customJsonObjectRequest = new CustomJsonObjectRequest(requestMethode, requestUrl, requestBody, response -> {
                int z = 0;
                for (int i = 1; i < response.length() + 1; i++) {
                    try {

                        JSONObject object = response.getJSONObject(String.valueOf(i)).getJSONObject("state");
                        Light light = new Light("Light", object.getBoolean("on"),
                                object.getInt("sat"),
                                object.getInt("bri"),
                                object.getInt("hue"),
                                i,
                                z);
                            this.lightData.getUnAssignedLights().add(light);
                            this.manager.insertDataAllFragment();
                            z++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, error -> Log.d("WEW", error.getStackTrace().toString()));
            queue.add(customJsonObjectRequest);
        }
    }

    public void doJsonArrayRequest(String requestUrl, JSONObject requestBody, int requestMethode, VolleyCallback callback) {
        CustomJsonArrayRequest customJsonArrayRequest = new CustomJsonArrayRequest(requestMethode, requestUrl, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    if (response.get(0).toString().contains("success")) {
                        callback.onSucces();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Log.d("WEW", error.getStackTrace().toString()));

        queue.add(customJsonArrayRequest);
    }

    public void retrieveAllData(Activity activity,boolean school) {
        if(school)
        {
            this.url = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB/lights/";
        }
        else {
            this.url = this.url + this.username + "/lights/";
        }

        Log.i("DATA", url);
        if(this.username != null && this.username.equals("iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB")) {
            Button pairSimButton = activity.findViewById(R.id.main_par_button_simulator);
            pairSimButton.setText(context.getResources().getString(R.string.successpair));
            pairSimButton.setAlpha(0.5f);
            pairSimButton.setEnabled(false);

            Button pairButton = activity.findViewById(R.id.main_par_button);
            pairButton.setText(context.getResources().getString(R.string.successpair));
            pairButton.setAlpha(0.5f);
            pairButton.setEnabled(false);
        }
        this.doJsonObjectRequest(this.url,null, Request.Method.GET);

    }


    public void retrieveLightData(int index) {
        String url = this.url + this.username + "/lights/" + index;
        this.doJsonObjectRequest(url,null, Request.Method.GET);
    }

    public void turnOn(Light light, boolean on,VolleyCallback volleyCallback)
    {
        String url = this.url + light.getIndex()  + "/state";
        JSONObject body = new JSONObject();
        try {
            body.put("on",on);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.doJsonArrayRequest(url, body, Request.Method.PUT, volleyCallback);
    }

    public void changeColor(Light light, int saturation, int brightness, int value, VolleyCallback volleyCallback) {
        String url = this.url + light.getIndex() + "/state";

        JSONObject object = new JSONObject();
        try {
            object.put("on", true);
            object.put("sat", saturation);
            object.put("bri", brightness);
            object.put("hue", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.doJsonArrayRequest(url, object, Request.Method.PUT, volleyCallback);
    }

    public String getUrl() {
        return url;
    }

    public FragmentConfig getManager() {
        return manager;
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

}
