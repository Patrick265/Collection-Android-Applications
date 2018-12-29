package csdev.com.black.view.layout;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import csdev.com.black.model.PolylineInfo;
import csdev.com.black.model.SportActivity;
import csdev.com.black.service.DBHandler;
import csdev.com.black.view.adapter.spinner.SpinnerItem;

public class EditSportActivity extends AppCompatActivity
{
    private EditText title;
    private EditText description;
    private SeekBar rating;
    private TextView ratingText;
    private Spinner category;
    private ArrayList<String> categories;
    private ArrayAdapter<String> spinnerAdapter;
    private DBHandler handler;
    private SportActivity activity;
    private ArrayList<PolylineInfo> polys;

    private Button cancelButton;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sport);

        Intent intent = getIntent();

        this.activity = (SportActivity) intent.getSerializableExtra("SPORT");
        this.polys = (ArrayList<PolylineInfo>) intent.getSerializableExtra("POLY");
        initalise();
        initValues();
        this.saveButton.setOnClickListener(l -> {
            if(updateValues() > 0)
            {
                this.handler.update(this.activity);
                Intent detail = new Intent(getApplicationContext(), DetailedActivity.class);
                detail.putExtra("SPORTACTIVITY", this.activity);
                detail.putExtra("POLYLINEINFO", this.polys);
                startActivity(detail);
            }
        });
        this.cancelButton.setOnClickListener(l -> {
            Intent detail = new Intent(getApplicationContext(), DetailedActivity.class);
            detail.putExtra("SPORTACTIVITY", this.activity);
            detail.putExtra("POLYLINEINFO", this.polys);
            startActivity(detail);
        });
    }

    private int updateValues()
    {
        int i = 0;
        if(this.title.getText().length() > 0 ) {

            this.activity.setTitle(this.title.getText().toString());
            i++;
        }
        if(this.description.getText().length() > 0) {
            this.activity.setDescription(this.description.getText().toString());
            i++;
        }
        if(this.rating.getProgress() != this.activity.getRating()) {
            this.activity.setRating(this.rating.getProgress());
            i++;
        }
        if(!this.category.getSelectedItem().toString().equals(this.activity.getType())) {
            this.activity.setType(this.category.getSelectedItem().toString());
            i++;
        }
        return i;
    }


    private void initalise() {
        this.handler = new DBHandler(getApplicationContext());
        this.title = findViewById(R.id.edit_edittext_title);
        this.description = findViewById(R.id.edit_edittext_description);
        this.rating = findViewById(R.id.edit_SeekBar_Rating);
        this.ratingText = findViewById(R.id.edit_TextView_Rating_text);
        this.category = findViewById(R.id.edit_Spinner_category);
        this.cancelButton = findViewById(R.id.edit_button_cancel);
        this.saveButton = findViewById(R.id.edit_button_save);

        this.categories = new ArrayList<>();
        this.categories.add("Cycling");
        this.categories.add("Running");
        this.categories.add("Walking");
        this.spinnerAdapter = new SpinnerItem(getApplicationContext(), this.categories);
        this.category.setAdapter(this.spinnerAdapter);
        this.category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initValues() {
        this.title.setHint(this.activity.getTitle());
        this.description.setHint(this.activity.getDescription());
        this.rating.setProgress(this.activity.getRating());
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
        this.ratingText.setText(this.activity.getRating() + " / 5");
        switch(this.activity.getType()) {
            case "Cycling":
                this.category.setSelection(0);
                break;
            case "Running":
                this.category.setSelection(1);
                break;
            case "Walking":
                this.category.setSelection(2);
                break;
        }
    }
}
