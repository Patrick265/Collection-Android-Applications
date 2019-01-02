package csdev.com.black.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import csdev.com.black.model.Coordinate;
import csdev.com.black.model.Weather;
import csdev.com.black.util.OnWeatherAvailable;

public class APIHandler
{
    private final String KEY;
    private final String URL;
    private final Coordinate coordinate;
    private OnWeatherAvailable listener;
    private RequestQueue queue;
    private Context context;


    public APIHandler(Context context, Coordinate coordinate, OnWeatherAvailable listener)
    {
        this.context = context;
        this.coordinate = coordinate;
        this.KEY = "687f0dd98976712cc991e5fa464f8f90";
        this.URL = "http://api.openweathermap.org/data/2.5/weather?lat=" + this.coordinate.getLatitude() + "&lon=" + this.coordinate.getLongitude() + "&APPID=" + this.KEY;
        this.listener = listener;
        this.queue = Volley.newRequestQueue(this.context);
    }

    public void retrieve()
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, this.URL, null, response ->
        {
            Log.d("VOLLEY_TAG", response.toString());
            try
            {
                double temp = response.getJSONObject("main").getDouble("temp");
                double tempMin = response.getJSONObject("main").getDouble("temp_min");
                double tempMax = response.getJSONObject("main").getDouble("temp_max");
                double humidity = response.getJSONObject("main").getDouble("humidity");
                double pressure = response.getJSONObject("main").getDouble("pressure");

                double windSpeed = response.getJSONObject("wind").getDouble("speed");
                double windDir = response.getJSONObject("wind").getDouble("deg");
                double clouds = response.getJSONObject("clouds").getDouble("all");
                String city = response.getString("name");
                Weather weather = new Weather(temp, tempMin, tempMax, humidity, pressure, windSpeed, windDir,clouds, city);
                listener.OnWeatherAvailable(weather);

            } catch (JSONException e1)
            {
                e1.printStackTrace();
            }
        }, error ->
                listener.OnWeatherError(new Error("COULD NOT RETRIEVE RESPONSE")));
        this.queue.add(request);
    }
}