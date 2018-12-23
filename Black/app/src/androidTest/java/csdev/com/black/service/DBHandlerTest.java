package csdev.com.black.service;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import csdev.com.black.model.Coordinate;
import csdev.com.black.model.SportActivity;

public class DBHandlerTest
{
    DBHandler handler = new DBHandler(InstrumentationRegistry.getTargetContext());
    private ArrayList<SportActivity> activityList = new ArrayList<>();
    private ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();


    @Test
    public void retrieveAll()
    {
        ArrayList<SportActivity> activities = handler.retrieveAll();
        for (SportActivity activity : activities) {
            Log.i("KDSKNK", activity.toString());
        }
    }

    @Test
    public void insert()
    {
        temp();
        handler.Insert(activityList.get(0));
    }

    @Test
    public void update()
    {
    }

    public void temp() {
        this.coordinates.add(new LatLng(50,51));
        this.coordinates.add(new LatLng(51,52));
        this.coordinates.add(new LatLng(52,53));
        this.coordinates.add(new LatLng(53,54));
        this.coordinates.add(new LatLng(54,55));
        SportActivity cycleActivity1 = new SportActivity("Cycled to Breda",
                LocalDateTime.now(),
                LocalDateTime.now(),
                3,
                "Basic description of the cycling",
                5.0,
                15.0, "Cycling", coordinates);
        this.activityList.add(cycleActivity1);
    }

    @Test
    public void onCreate()
    {
        this.handler.onCreate(this.handler.getReadableDatabase());

    }

    @Test
    public void onUpgrade()
    {
        this.handler.onUpgrade(this.handler.getReadableDatabase(), 0 , 1);
    }

}