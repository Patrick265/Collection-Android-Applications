package csdev.com.black.view.layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;

import csdev.com.black.DetailedActivity;
import csdev.com.black.R;
import csdev.com.black.data.SportStorage;
import csdev.com.black.view.adapter.ListCell;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView listView;
    private ListCell adapter;
    private SportStorage storage;
    private LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalise();
    }

    private void initalise() {
        this.storage = SportStorage.getInstance();
        this.listView = findViewById(R.id.main_recycleview);
        this.adapter = new ListCell(sportactivity -> {
            Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
            intent.putExtra("SPORTACTIVITY",sportactivity);
            startActivity(intent);

        }, this.storage.getActivityList());

        this.layoutManager = new LinearLayoutManager(getApplicationContext());
        this.listView.setAdapter(this.adapter);
        this.listView.setLayoutManager(this.layoutManager);
    }
}
