package csdev.com.black.view.layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import csdev.com.black.R;
import csdev.com.black.model.MapType;
import csdev.com.black.service.SPHandler;
import csdev.com.black.view.adapter.spinner.SpinnerSettingsActivity;

public class SettingsActivity extends AppCompatActivity
{

    private Switch apiWeather;
    private Spinner mapType;
    private Spinner sortActivity;
    private ArrayList<String> mapListTypes;
    private ArrayAdapter<String> spinnerMapAdapter;

    private ArrayList<String> sortingTypes;
    private ArrayAdapter<String> spinnerSortAdapter;
    private ImageButton returnToMain;
    private SPHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initalise();


        listener();
    }

    private void initalise() {
        this.handler = SPHandler.getInstance(getApplicationContext());
        this.returnToMain = findViewById(R.id.settings_imageButton_return);
        this.apiWeather = findViewById(R.id.settings_switch_weather);
        this.apiWeather.setChecked(this.handler.getSettings().isApi());

        this.mapType = findViewById(R.id.settings_spinner_maptype);
        this.mapListTypes = new ArrayList<>();
        this.mapListTypes.add("Hybrid");
        this.mapListTypes.add("Normal");
        this.mapListTypes.add("Terrain");
        this.mapListTypes.add("Satellite");
        this.spinnerMapAdapter = new SpinnerSettingsActivity(getApplicationContext(), this.mapListTypes);
        this.mapType.setAdapter(this.spinnerMapAdapter);

        int i = 0;
        for(String s : this.mapListTypes) {
            if(s.equals(this.handler.getSettings().getMap().toString()))
            {
                this.mapType.setSelection(i);
            }
            i++;
        }


        this.sortActivity = findViewById(R.id.settings_spinner_sort);
        this.sortingTypes = new ArrayList<>();
        this.sortingTypes.add("A to Z - Title");
        this.sortingTypes.add("Z to A - Title");
        this.sortingTypes.add("01-01-2000 to 12-12-2019 - Date");
        this.sortingTypes.add("12-12-2019 to 01-01-2000 - Date");
        this.sortingTypes.add("Distance");
        this.spinnerSortAdapter = new SpinnerSettingsActivity(getApplicationContext(), this.sortingTypes);
        this.sortActivity.setAdapter(this.spinnerSortAdapter);

        for(int y = 0; y < this.sortingTypes.size(); y++) {
            if(this.sortingTypes.get(y).equals(this.handler.getSettings().getSort())) {
                this.sortActivity.setSelection(y);
            }
        }
    }

    private void listener() {

        this.returnToMain.setOnClickListener(l -> {
            this.handler.getSettings().setApi(this.apiWeather.isChecked());
            this.handler.getSettings().setMap(MapType.valueOf(this.mapType.getSelectedItem().toString()));
            this.handler.getSettings().setSort(this.sortActivity.getSelectedItem().toString());
            this.handler.write();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });


    }
}
