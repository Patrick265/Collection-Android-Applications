package com.ptp2.blindwalls;

import android.app.Activity;
import android.util.Log;

import com.ptp2.blindwalls.model.BlindWall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JSONReader {
    private BlindWallsWrapper blindWallsWrapper;
    public void CreateBlindWalls(String json)
    {
        this.blindWallsWrapper = BlindWallsWrapper.getInstance();
        JSONArray jsonArray;
        JSONObject jsonObject;

        try {
            jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++)
            {
                jsonObject = jsonArray.getJSONObject(i);
                BlindWall wall = new BlindWall(jsonObject);
                this.blindWallsWrapper.getBlindWallList().add(wall);
            }

        } catch(JSONException e)
        {

        }
    }

    public void LoadFile(Activity activity)
    {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("walls.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        CreateBlindWalls(json);
    }
}
