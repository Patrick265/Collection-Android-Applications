package csdev.com.black.view.layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import java.time.format.DateTimeFormatter;

import csdev.com.black.R;
import csdev.com.black.model.SportActivity;

public class DetailedActivity extends AppCompatActivity
{

    private MapView map;
    private TextView title;
    private TextView description;
    private TextView date;
    private TextView distance;

    private ImageView category;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Intent intent = getIntent();

        SportActivity activity = (SportActivity) intent.getSerializableExtra("SPORTACTIVITY");
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

    private void initalise() {
        this.map = findViewById(R.id.detailed_map);

        this.title = findViewById(R.id.detailed_title);
        this.description = findViewById(R.id.detailed_text_description);
        this.date = findViewById(R.id.detailed_date_text);
        this.distance = findViewById(R.id.detailed_distance_text);

        this.category = findViewById(R.id.detailed_category_image);

    }
}
