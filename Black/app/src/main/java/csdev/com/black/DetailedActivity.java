package csdev.com.black;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import csdev.com.black.model.SportActivity;

public class DetailedActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Intent intent = getIntent();

        SportActivity activity = (SportActivity) intent.getSerializableExtra("SPORTACTIVITY");


    }
}
