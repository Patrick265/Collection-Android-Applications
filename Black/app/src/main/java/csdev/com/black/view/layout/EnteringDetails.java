package csdev.com.black.view.layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import csdev.com.black.R;
import csdev.com.black.model.Coordinate;
import csdev.com.black.model.SportActivity;
import csdev.com.black.service.DBHandler;
import csdev.com.black.view.adapter.spinner.SpinnerItem;


public class EnteringDetails extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private SeekBar rating;
    private TextView ratingText;
    private Spinner category;
    private Button save;
    private ArrayList<String> categories;
    private ArrayAdapter<String> spinnerAdapter;
    private DBHandler handler;
    private SportActivity activity;


    private String startTime;
    private String endTime;
    private ArrayList<Coordinate> latLngList;
    private double distance;
    private double avgspeed;


    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entering_details);
        this.activity = new SportActivity();
        initalise();
        listeners();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        this.distance = (double)bundle.getSerializable("distance");
        this.startTime = (String) bundle.getSerializable("start");
        this.endTime = (String) bundle.getSerializable("end");
        this.latLngList = (ArrayList<Coordinate>) bundle.getSerializable("coordinates");
        this.avgspeed = (double) bundle.getSerializable("avgspeed");

        this.save.setOnClickListener(l -> {
            if(validate() && this.activity != null) {
                handler.insert(this.activity);
                Intent intentToMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentToMain);
            }
        });
    }

    private void initalise() {
        this.title = findViewById(R.id.entering_edittext_title);
        this.description = findViewById(R.id.entering_edittext_description);
        this.rating = findViewById(R.id.entering_SeekBar_Rating);
        this.ratingText = findViewById(R.id.entering_TextView_Rating_text);
        this.category = findViewById(R.id.entering_Spinner_category);
        this.save = findViewById(R.id.entering_button_finished);
        this.handler = new DBHandler(getApplicationContext());

        this.categories = new ArrayList<>();
        this.categories.add("Cycling");
        this.categories.add("Running");
        this.categories.add("Walking");
        this.spinnerAdapter = new SpinnerItem(getApplicationContext(), this.categories);
        this.category.setAdapter(this.spinnerAdapter);

        this.category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), clickedItem + " selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void listeners() {
        this.rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                String progressText = seekBar.getProgress() + " / 5";
                ratingText.setText(progressText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }
    private boolean validate()
    {
        if(title.getText().length() > 0 &&
            title.getText().length() < 1000 &&
            description.getText().length() > 0 &&
            description.getText().length() < 2000)
        {
            this.activity.setTitle(String.valueOf(this.title.getText()));
            this.activity.setStartTime(this.startTime);
            this.activity.setEndTime(this.endTime);
            this.activity.setRating(this.rating.getProgress());
            this.activity.setDescription(String.valueOf(this.description.getText()));
            this.activity.setDistance(this.distance);
            this.activity.setAverageSpeed(this.avgspeed);
            this.activity.setType(this.category.getSelectedItem().toString());
            this.activity.setCoordinates(this.latLngList);
            return true;
        }
        return false;
    }
}
