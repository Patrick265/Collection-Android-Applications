package csdev.com.black.service;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import csdev.com.black.model.Coordinate;
import csdev.com.black.model.PolylineInfo;
import csdev.com.black.model.SportActivity;

public class DBHandlerTest
{
    DBHandler handler = new DBHandler(InstrumentationRegistry.getTargetContext());
    private ArrayList<SportActivity> activityList = new ArrayList<>();
    private ArrayList<Coordinate> coordinates = new ArrayList<>();
    private ArrayList<PolylineInfo> polylineInfos= new ArrayList<>();

    public void temp() {
        this.coordinates.add(new Coordinate(50,51));
        this.coordinates.add(new Coordinate(51,52));
        this.coordinates.add(new Coordinate(52,53));
        this.coordinates.add(new Coordinate(53,54));
        this.coordinates.add(new Coordinate(54,55));
        SportActivity cycleActivity1 = new SportActivity("Cycled to Breda",
                LocalDateTime.now(),
                LocalDateTime.now(),
                3,
                "Basic description of the cycling",
                5.0,
                15.0, "Cycling", coordinates);
        this.activityList.add(cycleActivity1);
        for (int i = 0; i < 5 ; i++)
        {
            this.polylineInfos.add(new PolylineInfo(Math.random() * 500, (long) (Math.random() * 500),i));
        }

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
        handler.insert(activityList.get(0), this.polylineInfos);
    }


    @Test
    public void delete()
    {
        ArrayList<SportActivity> list = this.handler.retrieveAll();

        this.handler.delete(list.get(0));
    }


    @Test
    public void PRetrieveByActivity()
    {
        SportActivity activity = this.handler.retrieveAll().get(1);
        ArrayList<PolylineInfo> poly = this.handler.PRetrieveByActivity(activity);
        for(PolylineInfo polylineInfo : poly) {
            Log.d("DEBUGPOLY", polylineInfo.toString());
        }
    }

    @Test
    public void Pupdate()
    {
        SportActivity activity = this.handler.retrieveAll().get(1);
        ArrayList<PolylineInfo> poly = new ArrayList<>();
        for (int i = 0; i < 10 ; i++)
        {
            poly.add(new PolylineInfo(35, (long) 25, (int) (Math.random() * 500)));
        }

        this.handler.Pupdate(poly, activity.getId());
    }

    @Test
    public void PDelete()
    {
        SportActivity activity = this.handler.retrieveAll().get(1);
        ArrayList<PolylineInfo> poly = this.handler.PRetrieveByActivity(activity);
        this.handler.PDelete(poly.get(0));
    }

    @Test
    public void cupdate()
    {
        SportActivity activity = this.handler.retrieveAll().get(1);
        ArrayList<Coordinate> poly = new ArrayList<>();
        for (int i = 0; i < 10 ; i++)
        {
            poly.add(new Coordinate(50, 50));
        }

        this.handler.Cupdate(poly, activity.getId());

    }

    @Test
    public void cinsert()
    {
    }
}