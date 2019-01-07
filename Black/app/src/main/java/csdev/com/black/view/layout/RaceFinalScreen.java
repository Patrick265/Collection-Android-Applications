package csdev.com.black.view.layout;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import csdev.com.black.R;
import csdev.com.black.model.Coordinate;
import csdev.com.black.model.PolylineInfo;
import csdev.com.black.model.SportActivity;
import csdev.com.black.service.DBHandler;

public class RaceFinalScreen extends AppCompatActivity {
    private SportActivity activity;
    private double distance;
    private ArrayList<Coordinate> coordinates;
    private String startDate;
    private String endDate;
    private double avgSpeed;
    private ArrayList<PolylineInfo> infos;

    private TextView prevTime;
    private TextView currentTime;
    private TextView differenceTime;
    private DateTimeFormatter D = DateTimeFormatter.ofPattern("HH:mm:ss");

    private Button continueButton;

    private LocalTime previousLocalTime;
    private LocalTime currentLocalTime;

    private DBHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_finalscreen);

        Intent intent = getIntent();

        this.handler = new DBHandler(getApplicationContext());
        this.activity = (SportActivity) intent.getSerializableExtra("SACTIVITY");
        this.distance = (Double) intent.getSerializableExtra("distance");
        this.coordinates = (ArrayList<Coordinate>) intent.getSerializableExtra("coordinates");
        this.startDate = (String) intent.getSerializableExtra("start");
        this.endDate = (String) intent.getSerializableExtra("end");
        this.avgSpeed = (Double) intent.getSerializableExtra("avgspeed");
        this.infos = (ArrayList<PolylineInfo>) intent.getSerializableExtra("info");

        this.prevTime = findViewById(R.id.racefinal_previousFill);
        this.currentTime = findViewById(R.id.racefinal_currentFill);
        this.differenceTime = findViewById(R.id.racefinal_diffFill);
        this.continueButton = findViewById(R.id.racefinal_return);

        this.previousLocalTime = calcDuration(activity.getStartTime(),activity.getEndTime());
        this.currentLocalTime = calcDuration(startDate,endDate);

        this.prevTime.setText(D.format(previousLocalTime));
        this.currentTime.setText(D.format(currentLocalTime));

        continueButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent1);
            finish();
        });

        if(previousLocalTime.getSecond() > currentLocalTime.getSecond())
        {
            LocalTime localTime = LocalTime.parse(previousLocalTime.toString(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            int h = localTime.get(ChronoField.CLOCK_HOUR_OF_DAY);
            int m = localTime.get(ChronoField.MINUTE_OF_HOUR);
            int s = localTime.get(ChronoField.SECOND_OF_MINUTE);
            long second =(h * 60 * 60) + (m * 60) + (s % 60);


            LocalTime localTime2 = LocalTime.parse(currentLocalTime.toString(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            int h2 = localTime2.get(ChronoField.CLOCK_HOUR_OF_DAY);
            int m2 = localTime2.get(ChronoField.MINUTE_OF_HOUR);
            int s2 = localTime2.get(ChronoField.SECOND_OF_MINUTE);
            long second2 =(h2 * 60 * 60) + (m2 * 60) + (s2 % 60);

            int secs = (int)(second - second2);
            int hours = secs / 3600;
            int remainder = secs % 3600;
            int minutes = remainder / 60;
            int seconds = remainder % 60;

            String displayHours= (hours < 10 ? "0" : "") + hours,
                    displayMinutes= (minutes < 10 ? "0" : "") + minutes ,
                    displaySec = (seconds < 10 ? "0" : "") + seconds ;
            differenceTime.setText("-" + "" + displayHours +":"+ displayMinutes+":"+displaySec);
            differenceTime.setTextColor(Color.GREEN);
            overwriting();
        }
        else
        {
            LocalTime localTime = LocalTime.parse(previousLocalTime.toString(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            int h = localTime.get(ChronoField.CLOCK_HOUR_OF_DAY);
            int m = localTime.get(ChronoField.MINUTE_OF_HOUR);
            int s = localTime.get(ChronoField.SECOND_OF_MINUTE);
            long second =(h * 60 * 60) + (m * 60) + (s % 60);


            LocalTime localTime2 = LocalTime.parse(currentLocalTime.toString(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            int h2 = localTime2.get(ChronoField.CLOCK_HOUR_OF_DAY);
            int m2 = localTime2.get(ChronoField.MINUTE_OF_HOUR);
            int s2 = localTime2.get(ChronoField.SECOND_OF_MINUTE);
            long second2 =(h2 * 60 * 60) + (m2 * 60) + (s2 % 60);

            int secs = (int)(second2 - second);
            int hours = secs / 3600;
            int remainder = secs % 3600;
            int minutes = remainder / 60;
            int seconds = remainder % 60;

            String displayHours= (hours < 10 ? "0" : "") + hours,
                    displayMinutes= (minutes < 10 ? "0" : "") + minutes ,
                    displaySec = (seconds < 10 ? "0" : "") + seconds ;
            differenceTime.setText("+" + "" + displayHours +":"+ displayMinutes+":"+displaySec);
            differenceTime.setTextColor(Color.RED);
        }

    }

    public void overwriting()
    {
        this.activity.setCoordinates(coordinates);
        this.activity.setStartTime(startDate);
        this.activity.setEndTime(endDate);
        this.activity.setDistance(distance);
        this.activity.setAverageSpeed(avgSpeed);

        //Causes unexpected nullpointers, -> Patrick fix je database I swear
      //  this.handler.Cupdate(coordinates, activity.getId());
     //   this.handler.Pupdate(infos, activity.getId());

        this.handler.update(activity);
    }

    public LocalTime calcDuration(String startTime, String endTime) {

        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime start;
        LocalDateTime end;
        try {
            start = LocalDateTime.parse(startTime, DATEFORMATTER);
            end = LocalDateTime.parse(endTime, DATEFORMATTER);
        } catch(RuntimeException e) {
            DATEFORMATTER = DateTimeFormatter.ofPattern("HH:mm");
            start = LocalDateTime.parse(startTime, DATEFORMATTER);
            end = LocalDateTime.parse(endTime, DATEFORMATTER);
        }

        LocalDateTime tempDateTime = LocalDateTime.from( start );
        long years = tempDateTime.until( end, ChronoUnit.YEARS);

        tempDateTime = tempDateTime.plusYears( years );
        long months = tempDateTime.until( end, ChronoUnit.MONTHS);

        tempDateTime = tempDateTime.plusMonths( months );

        long hours = tempDateTime.until( end, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours( hours );

        long days = tempDateTime.until( end, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);

        long seconds = tempDateTime.until( end, ChronoUnit.SECONDS);

        long minutes = tempDateTime.until( end, ChronoUnit.MINUTES);

        LocalTime localTime = LocalTime.of((int) hours, (int) minutes, (int) seconds % 60);


        return localTime;
    }

}
