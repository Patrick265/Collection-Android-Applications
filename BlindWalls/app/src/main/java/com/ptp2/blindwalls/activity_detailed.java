package com.ptp2.blindwalls;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptp2.blindwalls.model.BlindWall;
import com.squareup.picasso.Picasso;

public class Activity_detailed extends AppCompatActivity {

    private TextView title;
    private TextView photographer;
    private TextView year;
    private TextView description;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        title = findViewById(R.id.detailed_title);
        image = findViewById(R.id.detailed_picture);
        photographer = findViewById(R.id.detailed_photographer);
        year = findViewById(R.id.detailed_year);
        description = findViewById(R.id.detailed_description);

        Intent intent = getIntent();
        BlindWall blindWall = (BlindWall) intent.getSerializableExtra("BLINDWALL");

        photographer.setText(blindWall.getPhotographer());
        year.setText(String.valueOf(blindWall.getYear()));
        description.setText(blindWall.getDescriptionEnglish());
        title.setText( blindWall.getTitle());

        String imageUrl = "https://api.blindwalls.gallery/" + blindWall.getImagesUrls().get(0);
        Picasso.get().load(imageUrl).into(image);
    }
}
