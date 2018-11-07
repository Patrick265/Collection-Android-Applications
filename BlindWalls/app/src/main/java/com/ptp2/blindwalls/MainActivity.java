package com.ptp2.blindwalls;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ptp2.blindwalls.model.BlindWall;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private BlindWallsWrapper wrapper;
    private JSONReader reader;
    private ListView bwListView;
    private ArrayAdapter<BlindWall> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalize();
    }

    public void initalize() {

        this.wrapper = BlindWallsWrapper.getInstance();
        this.reader = new JSONReader();
        this.reader.LoadFile(this);
        this.bwListView = findViewById(R.id.Main_ListView);
        this.adapter = new MainCellList(this, this.wrapper.getBlindWallList());
        this.bwListView.setAdapter(this.adapter);

    }
}
