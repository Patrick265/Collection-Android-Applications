package com.ptp2.blindwalls;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ptp2.blindwalls.model.BlindWall;
import com.ptp2.blindwalls.util.APIManager;
import com.ptp2.blindwalls.util.BlindWallListener;
import com.ptp2.blindwalls.util.OnBlindWallClickListener;

public class MainActivity extends AppCompatActivity implements BlindWallListener {

    private BlindWallsWrapper wrapper;
    private APIManager manager;
    private JSONReader reader;
    private RecyclerView bwListView;
    private LinearLayoutManager linearLayoutManager;
    private MainCellList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalize();


        Log.i("VOLLEY_TAG", "MAIN SIZE: " + this.wrapper.getBlindWallList().size());
    }

    public void initalize() {

        this.wrapper = BlindWallsWrapper.getInstance();
        this.manager = new APIManager(getApplicationContext(), this);
        this.reader = new JSONReader();




        this.bwListView = findViewById(R.id.Main_list);
        this.linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        this.linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.bwListView.setLayoutManager(this.linearLayoutManager);
        this.adapter = new MainCellList(this.wrapper, new OnBlindWallClickListener() {
            @Override
            public void onItemClick(BlindWall wall) {
                Intent intent = new Intent(getApplicationContext(), Activity_detailed.class);
                intent.putExtra("BLINDWALL", wall);
                startActivity(intent);
            }
        });
        this.bwListView.setAdapter(this.adapter);

        this.manager.Retrieve("https://api.blindwalls.gallery/apiv2/murals");

    }

    @Override
    public void onBlindWallAvailable(BlindWall wall) {
        this.wrapper.getBlindWallList().add(wall);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onBlindWallError(Error error) {
        Log.e("ERROR", "BLIND WALL ERROR WITH API");
    }
}
