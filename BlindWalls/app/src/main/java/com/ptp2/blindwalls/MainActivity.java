package com.ptp2.blindwalls;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BlindWallsWrapper blindWallsWrapper = BlindWallsWrapper.getInstance();
        JSONReader jsonReader = new JSONReader();
        jsonReader.LoadFile(this);
    }
}
