package csdev.com.black.view.layout;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import csdev.com.black.R;
import csdev.com.black.data.PolylineDraw;
import csdev.com.black.model.Coordinate;
import csdev.com.black.model.SportActivity;

public class DetailedActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mGoogleMap;
    private TextView title;
    private TextView description;
    private TextView date;
    private TextView distance;
    private ArrayList<LatLng> latLngList;
    private PolylineDraw polylineDraw;
    private SportActivity activity;

    private ImageView category;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Intent intent = getIntent();

        this.activity = (SportActivity) intent.getSerializableExtra("SPORTACTIVITY");
        initalise();

        this.title.setText(activity.getTitle());
        this.description.setText(activity.getDescription());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy\tHH:mm");
        this.date.setText(activity.getStartTime());

        this.distance.setText(activity.getDistance() + " Km");


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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        convertCoordinates(activity.getCoordinates());
        if(!latLngList.isEmpty()) {
            polylineDraw.addAllPolygon(latLngList, mGoogleMap);
        }
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
    }
}
