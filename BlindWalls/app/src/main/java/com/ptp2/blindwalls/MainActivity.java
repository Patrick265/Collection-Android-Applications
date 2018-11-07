package com.ptp2.blindwalls;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ptp2.blindwalls.model.BlindWall;

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

        bwListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BlindWall blindWall = (BlindWall) wrapper.getBlindWallList().get(position);

                Intent intent = new Intent(getApplicationContext(), Activity_detailed.class);
                intent.putExtra("BLINDWALL", blindWall);
                startActivity(intent);
            }
        });

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
