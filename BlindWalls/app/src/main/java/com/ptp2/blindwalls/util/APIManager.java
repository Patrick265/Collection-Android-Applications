package com.ptp2.blindwalls.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ptp2.blindwalls.BlindWallsWrapper;
import com.ptp2.blindwalls.model.BlindWall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIManager {

    private Context context;
    private RequestQueue queue;
    private BlindWallsWrapper wrapper;
    private BlindWallListener listener;


    public APIManager(Context context, BlindWallListener listener) {
        this.context = context;
        this.queue = Volley.newRequestQueue(this.context);
        this.wrapper = BlindWallsWrapper.getInstance();
        this.listener = listener;
    }


    public void Retrieve(String url) {
        final String APIurl = url;


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, APIurl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("VOLLEY_TAG", response.toString());
                try {

                    for (int i = 0; i < response.length(); i++) {
                        Log.d("VOLLEY_TAG", "INDEX: " + i);
                        JSONObject jobj = response.getJSONObject(i);
                        List<String> urls = new ArrayList<>();
                        JSONArray jsonUrlArray = jobj.getJSONArray("images");
                        for (int j = 0; j < jsonUrlArray.length(); j++) {
                            String imageUrl = jsonUrlArray.getJSONObject(j).getString("url");
                            urls.add(imageUrl);
                        }

                        BlindWall blindWall = new BlindWall.Builder()
                                .id(jobj.getInt("id"))
                                .title(jobj.getJSONObject("title").getString("en"))
                                .author(jobj.getString("author"))
                                .year(String.valueOf(jobj.getInt("year")))
                                .descriptionEnglish(jobj.getJSONObject("description").getString("en"))
                                .descriptionDutch(jobj.getJSONObject("description").getString("nl"))
                                .materialEnglish(jobj.getJSONObject("material").getString("en"))
                                .materialDutch(jobj.getJSONObject("material").getString("nl"))
                                .numberOnMap(jobj.getInt("numberOnMap"))
                                .latitude(jobj.getDouble("latitude"))
                                .longitude(jobj.getDouble("longitude"))
                                .adress(jobj.getString("address"))
                                .videoAuthor(jobj.getString("videoAuthor"))
                                .videoUrl(jobj.getString("videoUrl"))
                                .photographer(jobj.getString("photographer"))
                                .imagesUrls(urls)
                                .build();

                        listener.onBlindWallAvailable(blindWall);
                    }

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onBlindWallError(new Error("COULD NOT RETRIEVE RESPONSE"));
            }
        });

        queue.add(request);
    }
}
