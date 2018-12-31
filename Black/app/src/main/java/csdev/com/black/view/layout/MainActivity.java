package csdev.com.black.view.layout;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import csdev.com.black.R;
import csdev.com.black.model.SportActivity;
import csdev.com.black.service.DBHandler;
import csdev.com.black.service.SPHandler;
import csdev.com.black.view.adapter.ListCell;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView listView;
    private ListCell adapter;
    private DBHandler dbHandler;
    private ArrayList<SportActivity> activities;
    private LayoutManager layoutManager;
    private FloatingActionButton newActivityButton;
    private ImageButton settingsButton;
    private SPHandler spHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalise();
    }



    private void initalise() {
        this.spHandler = SPHandler.getInstance(getApplicationContext());
        this.spHandler.read();

        this.dbHandler = new DBHandler(getApplicationContext());

        this.activities = this.dbHandler.retrieveAll();

        sort();

        this.newActivityButton = findViewById(R.id.main_fab);
        this.listView = findViewById(R.id.main_recycleview);
        this.adapter = new ListCell(activity ->
        {
            Log.i("CELL", "Clicked on cell" + activity.getTitle());
            Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
            intent.putExtra("SPORTACTIVITY", activity);
            intent.putExtra("POLYLINEINFO", this.dbHandler.PRetrieveByActivity(activity));
            startActivity(intent);
        }, this.activities);


        this.newActivityButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        });
        this.layoutManager = new LinearLayoutManager(getApplicationContext());
        this.listView.setAdapter(this.adapter);
        this.listView.setLayoutManager(this.layoutManager);

        this.settingsButton = findViewById(R.id.main_settings);
        this.settingsButton.setOnClickListener(l -> {
            float deg = this.settingsButton.getRotation() + 360f;
            this.settingsButton.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
            Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settings);
        });
    }

    public void sort() {
        switch (this.spHandler.getSettings().getSort())
        {
            case "A to Z - Title":
                this.activities.sort(new Comparator<SportActivity>()
                {
                    @Override
                    public int compare(SportActivity o1, SportActivity o2)
                    {
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                });
                break;
            case "Z to A - Title":
                this.activities.sort((Comparator<SportActivity>) (o1, o2) ->
                {
                    return o1.getTitle().compareTo(o2.getTitle());
                });
                Collections.reverse(this.activities);
                break;
            case "01-01-2000 to 12-12-2019 - Date":
                Collections.sort(this.activities, new Comparator<SportActivity>()
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:MM:ss");
                    @Override
                    public int compare(SportActivity o1, SportActivity o2)
                    {
                        try
                        {
                            return formatter.parse(o1.getStartTime()).compareTo(formatter.parse(o2.getStartTime()));
                        } catch (ParseException e)
                        {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                break;
            case "12-12-2019 to 01-01-2000 - Date":
                Collections.sort(this.activities, new Comparator<SportActivity>()
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:MM:ss");
                    @Override
                    public int compare(SportActivity o1, SportActivity o2)
                    {
                        try
                        {
                            return formatter.parse(o1.getStartTime()).compareTo(formatter.parse(o2.getStartTime()));
                        } catch (ParseException e)
                        {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });

                Collections.reverse(this.activities);
                break;
            case "Distance":
                this.activities.sort(new Comparator<SportActivity>()
                {
                    @Override
                    public int compare(SportActivity o1, SportActivity o2)
                    {
                        return Double.compare(o1.getDistance(), o2.getDistance());
                    }
                });
                break;
        }
    }
}
