package csdev.com.black.view.layout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import csdev.com.black.R;
import csdev.com.black.data.PolylineDraw;
import csdev.com.black.model.Coordinate;
import csdev.com.black.model.PolylineInfo;
import csdev.com.black.model.SportActivity;

public class DetailedActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mGoogleMap;
    private TextView title;
    private TextView description;
    private TextView date;
    private TextView distance;
    private ArrayList<LatLng> latLngList;
    private ArrayList<PolylineInfo> infos;
    private PolylineDraw polylineDraw;
    private SportActivity activity;
    private ArrayList<Polyline> polylines = new ArrayList<>();
    private Dialog dPolyMessage;

    private ImageView category;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Intent intent = getIntent();

        this.activity = (SportActivity) intent.getSerializableExtra("SPORTACTIVITY");
        this.infos = (ArrayList<PolylineInfo>) intent.getSerializableExtra("POLYLINEINFO");

        initalise();

        this.title.setText(activity.getTitle());
        this.description.setText(activity.getDescription());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy\tHH:mm");
        this.date.setText(activity.getStartTime());

        this.distance.setText(activity.getDistance() + " Km");

        dPolyMessage = new Dialog(this);
        dPolyMessage.setCancelable(false);
        dPolyMessage.setCanceledOnTouchOutside(false);


        switch (activity.getType())
        {
            case "Cycling":
                this.category.setImageResource(R.color.BicycleColor);
                break;
            case "Running":
                this.category.setImageResource(R.color.RunningColor);
                break;
            case "Walking":
                this.category.setImageResource(R.color.WalkingColor);
                break;
        }
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

        this.title = findViewById(R.id.detailed_title);
        this.description = findViewById(R.id.detailed_text_description);
        this.date = findViewById(R.id.detailed_date_text);
        this.distance = findViewById(R.id.detailed_distance_text);
        this.polylineDraw = new PolylineDraw();

        this.category = findViewById(R.id.detailed_category_image);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailed_map);
        mapFragment.getMapAsync(this);

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

        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
    }
}
