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
import com.google.maps.android.SphericalUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import csdev.com.black.R;
import csdev.com.black.data.LocationCallbackListener;
import csdev.com.black.data.MyService;
import csdev.com.black.data.PolylineDraw;
import csdev.com.black.data.LocationCallbackHandler;
import csdev.com.black.model.Coordinate;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationCallbackListener {

    private GoogleMap mGoogleMap;
    private Button mapButton;
    private Button mapButtonStop;
    private Context context;
    private LocationCallbackHandler loc;
    private Location mLastLocation;
    private Boolean firstTime;
    private PolylineDraw polylineDraw;
    private List<LatLng> polygon;
    private Dialog dMessage;
    private Boolean startTracking;
    private TextView distance;
    private double distanceInteger;
    private String startDate;
    private SimpleDateFormat sdfDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        this.context = getApplicationContext();
        sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startForegroundService(new Intent(context, MyService.class));
        } else {
            checkLocationPermission();
        }

        startTracking = false;
        polylineDraw = new PolylineDraw();
        firstTime = true;
        polygon = new ArrayList<>();
        distanceInteger = 0;

        dMessage = new Dialog(this);
        dMessage.setCanceledOnTouchOutside(false);
        dMessage.setCancelable(false);

        loc = new LocationCallbackHandler();
        loc.addListener(this);

        distance = findViewById(R.id.txt_distance);
        mapButtonStop = findViewById(R.id.btn_map2);
        mapButtonStop.setEnabled(false);
        mapButtonStop.setVisibility(View.GONE);

        mapButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage();
            }
        });


        mapButton = findViewById(R.id.btn_map);
        mapButton.setEnabled(true);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTracking = true;
                Date now = new Date();
                startDate = sdfDate.format(now);
                mapButton.setEnabled(false);
                mapButton.setVisibility(View.GONE);
                mapButtonStop.setVisibility(View.VISIBLE);
                mapButtonStop.setEnabled(true);
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMapSettings(mGoogleMap);
        mapButton.setEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    public void googleMapSettings(GoogleMap mGoogleMap) {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mGoogleMap.setMaxZoomPreference(22);
        mGoogleMap.setMinZoomPreference(10);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
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
                            ActivityCompat.requestPermissions(MapsActivity.this,
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

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onLocationAvailable(Location location) {
        if (mGoogleMap != null) {
            if (firstTime) {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                firstTime = false;
            }
            if (startTracking) {
                this.runOnUiThread(() -> {
                    //The last location in the list is the newest
                    mLastLocation = location;
                    if (polylineDraw != null) {
                        polylineDraw.updatePolygon(mLastLocation.getLatitude(), mLastLocation.getLongitude(), mGoogleMap, polygon);
                        if(distance != null) {
                            distanceInteger = (int)SphericalUtil.computeLength(polygon);
                            distance.setText(distanceInteger + " meter");
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
            Button ok = dMessage.findViewById(R.id.btn_confirmationOK);
            Button cancel = dMessage.findViewById(R.id.btn_confirmationCancel);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundleUp();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dMessage.hide();
                }
            });

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

        ArrayList<Coordinate> coordinates = new ArrayList<>();
        for(LatLng poly : polygon)
        {
            coordinates.add(new Coordinate(poly.latitude,poly.longitude));
        }

        bundle.putSerializable("distance", distanceInteger);
        bundle.putSerializable("latlngList", coordinates);
        bundle.putSerializable("start", startDate );
        bundle.putSerializable("end", endDate);
        bundle.putSerializable("avgspeed", 0.0);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

