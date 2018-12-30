package csdev.com.black.view.layout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import csdev.com.black.R;
import csdev.com.black.data.PolylineDraw;
import csdev.com.black.model.Coordinate;
import csdev.com.black.model.PolylineInfo;
import csdev.com.black.model.SportActivity;
import csdev.com.black.service.DBHandler;

public class DetailedActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mGoogleMap;
    private ArrayList<LatLng> latLngList;
    private ArrayList<PolylineInfo> infos;
    private PolylineDraw polylineDraw;
    private SportActivity activity;
    private ArrayList<Polyline> polylines = new ArrayList<>();
    private Dialog dPolyMessage;



    private TextView title;
    private TextView description;
    private TextView startTime;
    private TextView distance;
    private TextView endTime;
    private TextView duration;
    private TextView velocity;


    private ImageView category;
    private ImageView iconCategory;

    private ImageButton editButton;
    private ImageButton deleteButton;
    private ImageButton returnButton;

    private DBHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        this.handler = new DBHandler(getApplicationContext());
        Intent intent = getIntent();
        this.activity = (SportActivity) intent.getSerializableExtra("SPORTACTIVITY");
        this.infos = (ArrayList<PolylineInfo>) intent.getSerializableExtra("POLYLINEINFO");


        initalise();

        this.title.setText(activity.getTitle());
        this.description.setText(activity.getDescription());

        this.startTime.setText(activity.getStartTime());
        this.endTime.setText(activity.getEndTime());

        this.distance.setText(activity.getDistance() + " m");

        dPolyMessage = new Dialog(this);
        dPolyMessage.setCancelable(false);
        dPolyMessage.setCanceledOnTouchOutside(false);
        calcDuration(this.activity.getStartTime(), this.activity.getEndTime());


        switch (activity.getType())
        {
            case "Cycling":
                this.category.setImageResource(R.color.BicycleColor);
                this.iconCategory.setImageResource(R.drawable.ic_baseline_bike_24px);
                break;
            case "Running":
                this.category.setImageResource(R.color.RunningColor);
                this.iconCategory.setImageResource(R.drawable.ic_baseline_run_24px);
                break;
            case "Walking":
                this.category.setImageResource(R.color.WalkingColor);
                this.iconCategory.setImageResource(R.drawable.ic_baseline_walk_24px);
                break;
        }

        this.editButton.setOnClickListener(l -> {
            Intent editIntent = new Intent(getApplicationContext(), EditSportActivity.class);
            editIntent.putExtra("SPORT", this.activity);
            editIntent.putExtra("POLY", this.infos);
            startActivity(editIntent);
            finish();
        });

        this.deleteButton.setOnClickListener(l -> {
            this.handler.delete(this.activity);
            Intent delete = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(delete);
            finish();

        });

        this.returnButton.setOnClickListener(l -> {
            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(main);
            finish();
        });
    }

    private void convertCoordinates(ArrayList<Coordinate> coordinateList)
    {
        latLngList = new ArrayList<>();
        for(Coordinate c : coordinateList)
        {
           latLngList.add(new LatLng(c.getLatitude(),c.getLongitude()));
        }
    }

    private void initalise() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailed_map);
        mapFragment.getMapAsync(this);
        this.title = findViewById(R.id.detailed_title);
        this.description = findViewById(R.id.detailed_text_description);
        this.startTime = findViewById(R.id.detailed_date_text);
        this.distance = findViewById(R.id.detailed_distance_text);
        this.polylineDraw = new PolylineDraw();
        this.velocity = findViewById(R.id.detailed_text_avgspeed);

        this.category = findViewById(R.id.detailed_category_image);
        this.endTime = findViewById(R.id.detailed_date_textEnd);
        this.duration = findViewById(R.id.detailed_text_time);

        this.iconCategory = findViewById(R.id.detailed_icon_typeSport);

        this.deleteButton = findViewById(R.id.detailed_delete_button);
        this.editButton = findViewById(R.id.detailed_edit_button);
        this.returnButton = findViewById(R.id.detailed_return_button);


    }

    private void fillPolyLines(GoogleMap mMap)
    {
        int i = 2;
        for(int b = 0; b < latLngList.size(); b++)
        {
            if((b + 1) < latLngList.size())
            {
                LatLng first = latLngList.get(b);
                LatLng second = latLngList.get(b +1);
                polylineDraw.updatePolygonFresh(polylines, String.valueOf(i), mMap, first,second);
                i++;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        convertCoordinates(activity.getCoordinates());
        this.mGoogleMap.setOnMapLoadedCallback(() ->
        {
            if(!latLngList.isEmpty()) {
                fillPolyLines(mGoogleMap);
                polylineDraw.setCamera(latLngList,mGoogleMap);
            }
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        });


        mGoogleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                for(PolylineInfo p : infos)
                {
                    if(polyline.getTag().equals(String.valueOf(p.getIdentificationID())))
                    {
                        showPolylineDetails(polyline, p);
                    }
                }
            }
        });
    }

    private void showPolylineDetails(Polyline polyline, PolylineInfo polylineInfo)
    {
        try {
            polyline.setColor(Color.RED);
            dPolyMessage.setContentView(R.layout.activity_polyline_info_screen);
            dPolyMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Button ok = dPolyMessage.findViewById(R.id.poly_ok);
            TextView time = dPolyMessage.findViewById(R.id.polyline_time_fill);
            TextView distance = dPolyMessage.findViewById(R.id.polyline_distance_fill);
            TextView velocity = dPolyMessage.findViewById(R.id.polyline_velocity_fill);

            time.setText(polylineInfo.getTime() + " seconds" );
            distance.setText(polylineInfo.getLength() + " meters");
            velocity.setText(polylineInfo.getSpeed() + " km/h");

            dPolyMessage.show();

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    polyline.setColor(Color.BLUE);
                    dPolyMessage.hide();
                }
            });

        } catch (Exception e)
        {
            Log.d("ERROR", e.toString());
        }

    }

    public void calcDuration(String startTime, String endTime) {

        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime start = null;
        LocalDateTime end = null;
        try {
            start = LocalDateTime.parse(startTime, DATEFORMATTER);
            end = LocalDateTime.parse(endTime, DATEFORMATTER);
        } catch(RuntimeException e) {
            DATEFORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            start = LocalDateTime.parse(startTime, DATEFORMATTER);
            end = LocalDateTime.parse(endTime, DATEFORMATTER);
        }



        LocalDateTime tempDateTime = LocalDateTime.from( start );
        long years = tempDateTime.until( end, ChronoUnit.YEARS);

        tempDateTime = tempDateTime.plusYears( years );
        long months = tempDateTime.until( end, ChronoUnit.MONTHS);

        tempDateTime = tempDateTime.plusMonths( months );

        long hours = tempDateTime.until( end, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours( hours );

        long days = tempDateTime.until( end, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);

        long seconds = tempDateTime.until( end, ChronoUnit.SECONDS);

        long minutes = tempDateTime.until( end, ChronoUnit.MINUTES);

        tempDateTime = tempDateTime.plusMinutes( minutes );
        LocalTime localTime = LocalTime.of((int) hours, (int) minutes, (int) seconds % 60);

        DATEFORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.duration.setText(DATEFORMATTER.format(localTime));
        long second = (days * 86400) + (hours * 60 * 60) + (minutes * 60) + (seconds % 60);
        this.velocity.setText(new DecimalFormat("##.##").format((activity.getDistance() / second) * 3.6) + " km/h");
    }
}
