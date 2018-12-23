package csdev.com.black.view.layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import csdev.com.black.R;
import csdev.com.black.model.Coordinate;
import csdev.com.black.model.SportActivity;

public class EnteringDetails extends AppCompatActivity {
    private String startTime;
    private String endTime;
    private ArrayList<Coordinate> latLngList;
    private int distance;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entering_details);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        distance = (int)bundle.getSerializable("distance");
        startTime = (String) bundle.getSerializable("start");
        endTime = (String) bundle.getSerializable("end");
        latLngList = (ArrayList<Coordinate>) bundle.getSerializable("coordinates");

        TextView startTxt = findViewById(R.id.final_start);
        TextView endTxt = findViewById(R.id.final_end);
        TextView distanceTxt = findViewById(R.id.final_distance);
        startTxt.setText(startTime);
        endTxt.setText(endTime);
        distanceTxt.setText(distance + " meter");

    }
}
