package csdev.com.black.view.layout;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.maps.android.SphericalUtil;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import csdev.com.black.R;
import csdev.com.black.data.LocationCallbackHandler;
import csdev.com.black.data.LocationCallbackListener;
import csdev.com.black.data.MyService;
import csdev.com.black.data.PolylineDraw;
import csdev.com.black.model.Coordinate;
import csdev.com.black.model.MapType;
import csdev.com.black.model.PolylineInfo;
import csdev.com.black.model.SportActivity;
import csdev.com.black.service.SPHandler;

public class RaceActivity extends FragmentActivity implements OnMapReadyCallback, LocationCallbackListener {

    private GoogleMap mMap;
    private Timer timer;

    private ArrayList<PolylineInfo> infos;
    private ArrayList<LatLng> latlngList;

    private ArrayList<PolylineInfo> compareInfos;
    private ArrayList<LatLng> copyOfPrevious;

    private ArrayList<Polyline> polyLines;

    private Context context;
    private int color;

    private PolylineDraw polylineDraw;
    private LocationCallbackHandler loc;

    private Button startButton;
    private Button stopButton;
    private Boolean startButtonControl;
    private Boolean stopButtonControl;
    private Boolean startTracking;
    private SimpleDateFormat sdfDate;
    private Boolean checkOnce = true;
    private Dialog dPolyMessage;
    private int counter;
    private int position;

    private LocalDateTime expiredTime;
    private double distanceInteger;
    private Dialog dMessage;
    private int i;
    private Boolean firstTime;
    private LocalDateTime startTime;
    private String startDate;
    private SPHandler handler;
    private SportActivity activity;

    private Location mLastLocation;
    private ArrayList<Double> totalTimes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);

        initialise();

    }

    private void initialise()
    {
        this.context = getApplicationContext();
        this.handler = SPHandler.getInstance(this.context);
        this.startButton = findViewById(R.id.race_start);
        this.stopButton = findViewById(R.id.race_stop);
        this.sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.latlngList = new ArrayList<>();
        this.polyLines =  new ArrayList<>();
        this.infos = new ArrayList<>();
        this.copyOfPrevious = new ArrayList<>();
        this.counter = 0;
        this.position = 0;
        this.timer = new Timer();


        Intent intent = getIntent();
        this.activity = (SportActivity) intent.getSerializableExtra("RACEACTIVITY");
        this.compareInfos = (ArrayList<PolylineInfo>) intent.getSerializableExtra("RACEINFO");

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startForegroundService(new Intent(context, MyService.class));
        } else {
            checkLocationPermission();
        }

        convertCoordinates(activity.getCoordinates());

        dMessage = new Dialog(this);
        dMessage.setCanceledOnTouchOutside(false);
        dMessage.setCancelable(false);

        dPolyMessage = new Dialog(this);
        dPolyMessage.setCancelable(false);
        dPolyMessage.setCanceledOnTouchOutside(false);

        loc = new LocationCallbackHandler();
        loc.addListener(this);

        this.startTracking = false;
        this.polylineDraw = new PolylineDraw();
        this.firstTime = true;
        this.distanceInteger = 0;
        this.i = 0;
        color = Color.rgb(201,64,234);

        double b = 0;

        totalTimes = new ArrayList<>();
        for(int i = 0; i < compareInfos.size() + 1; i++)
        {
           for(int k = 0; k < i; k++)
           {
               b += compareInfos.get(k).getTime();
           }
           totalTimes.add(b);
           b = 0;
        }


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTracking = true;
                Date now = new Date();
                startTime = LocalDateTime.now();
                startDate = sdfDate.format(now);
                startButtonControl = false;
                startButton.setAlpha(0.5f);
                expiredTime = startTime;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(position < totalTimes.size())
                                {
                                    if(totalTimes.get(position) < counter)
                                    {
                                        Log.i("WEW", position + "");
                                        copyOfPrevious.add(latlngList.get(position));
                                        polylineDraw.addList(mMap, copyOfPrevious);
                                        position++;
                                    }
                                }
                                else
                                {
                                    timer.cancel();
                                }
                                counter++;
                            }
                        });
                    }
                },0,1000);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage();
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.raceMap);
        mapFragment.getMapAsync(this);
    }

    private void checkProgress()
    {

    }

    public void googleMapSettings(GoogleMap mGoogleMap) {
        MapType mapType = this.handler.getSettings().getMap();
        switch (mapType)
        {
            case Hybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case Terrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case Normal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case Satellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
        }
        mGoogleMap.setMaxZoomPreference(22);
        mGoogleMap.setMinZoomPreference(10);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMapSettings(mMap);
        startButtonControl = true;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
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

    private void convertCoordinates(ArrayList<Coordinate> coordinateList)
    {
        for(Coordinate c : coordinateList)
        {
            latlngList.add(new LatLng(c.getLatitude(),c.getLongitude()));
        }
    }



    @Override
    public void onLocationAvailable(Location location) {
        if (mMap != null) {
            if (firstTime) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                firstTime = false;
            }
            if (startTracking) {
                this.runOnUiThread(() -> {
                    //The last location in the list is the newest

                    Location mPrevloc = mLastLocation;
                    mLastLocation = location;

                    if(mPrevloc != null) {
                        LatLng fresh = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
                        LatLng old = new LatLng(mPrevloc.getLatitude(), mPrevloc.getLongitude());
                        ArrayList<LatLng> hide = new ArrayList<>();
                        hide.add(old);
                        hide.add(fresh);
                        LocalDateTime now = LocalDateTime.now();

                        if (polylineDraw != null) {
                            i++;
                            if (latlngList.size() >= 1) {
                                if(infos.isEmpty())
                                {
                                    long difference = Duration.between(startTime, now).toMillis();
                                    infos.add(new PolylineInfo((SphericalUtil.computeLength(hide)),difference, i));
                                    startTime = LocalDateTime.now();
                                    polylineDraw.updatePolygon(old, fresh, mMap, latlngList, polyLines, String.valueOf(i), color);
                                    distanceInteger = (int) SphericalUtil.computeLength(latlngList);
                                   // distance.setText(distanceInteger + " meter");
                                }
                                else
                                {
                                    if(checkOnce)
                                    {
                                        stopButtonControl = true;
                                        stopButton.setAlpha(1f);
                                    }
                                    checkOnce = false;
                                    long difference = Duration.between(startTime, now).toMillis();
                                    infos.add(new PolylineInfo((int)(SphericalUtil.computeLength(hide)),difference, i));
                                    startTime = LocalDateTime.now();
                                    polylineDraw.updatePolygon(old, fresh, mMap, latlngList, polyLines, String.valueOf(i), color);
                                    distanceInteger = (int) SphericalUtil.computeLength(latlngList);
                                   // distance.setText(distanceInteger + " meter");
                                }
                            }
                            else
                            {
                                polylineDraw.updatePolygon(old, fresh, mMap, latlngList, polyLines, String.valueOf(i), color);
                                distanceInteger = (int) SphericalUtil.computeLength(latlngList);
                                //distance.setText(distanceInteger + " meter");
                            }
                        }
                    }
                });
            }
        }

    }

    private void showMessage() {
        try {
            dMessage.setContentView(R.layout.activity_confirmation);
            dMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dMessage.show();
            Button ok = dMessage.findViewById(R.id.btn_confirmationOk);
            Button cancel = dMessage.findViewById(R.id.btn_confirmationCancel);

            ok.setOnClickListener(v -> bundleUp());
            cancel.setOnClickListener(v -> dMessage.hide());

        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
    }

    private void bundleUp()
    {
        startTracking = false;

        Intent intent = new Intent(getApplicationContext(), EnteringDetails.class);
        Bundle bundle = new Bundle();

        Date date = new Date();
        String endDate = sdfDate.format(date);

//        ArrayList<Coordinate> coordinates = new ArrayList<>();
//        for(LatLng poly : polygon)
//        {
//            coordinates.add(new Coordinate(poly.latitude,poly.longitude));
//        }
//
//        bundle.putSerializable("distance", distanceInteger);
//        bundle.putSerializable("coordinates", coordinates);
//        bundle.putSerializable("start", startDate );
//        bundle.putSerializable("end", endDate);
//        bundle.putSerializable("avgspeed", 0.0);
//        bundle.putSerializable("info", infos);
//        intent.putExtras(bundle);
//        stopButtonControl = false;
//        mapButtonStop.setAlpha(0.5f);
//        startActivity(intent);
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

        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
                dialogBuilder
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(RaceActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        startForegroundService(new Intent(context, MyService.class));
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }

        }
    }
}
