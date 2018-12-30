package csdev.com.black.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


import csdev.com.black.R;
import csdev.com.black.model.SportActivity;

public class ListCell extends RecyclerView.Adapter<ViewHolder>
{
    private ListOnItemClickListener listener;
    private List<SportActivity> activityList;

    public ListCell(ListOnItemClickListener listener, List<SportActivity> activities) {
        this.listener = listener;
        this.activityList = activities;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_cell, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        SportActivity activity = this.activityList.get(i);


        switch (activity.getType())
        {
            case "Cycling":
                viewHolder.getCategory().setImageResource(R.color.BicycleColor);
                break;
            case "Running":
                viewHolder.getCategory().setImageResource(R.color.RunningColor);
                break;
            case "Walking":
                viewHolder.getCategory().setImageResource(R.color.WalkingColor);
                break;
        }


        viewHolder.getTitle().setText(activity.getTitle());
        viewHolder.getDate().setText(calcDuration(activity.getStartTime(), activity.getEndTime()));
        String distance = activity.getDistance() + " meter";
        viewHolder.getDistance().setText(distance);
        viewHolder.bindActivity(activity, this.listener);

    }

    @Override
    public int getItemCount()
    {
        return this.activityList.size();
    }

    public ListOnItemClickListener getListener()
    {
        return listener;
    }

    public List<SportActivity> getActivityList()
    {
        return activityList;
    }

    public void setActivityList(List<SportActivity> activityList)
    {
        this.activityList = activityList;
    }

    public String calcDuration(String startTime, String endTime) {

        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime start = null;
        LocalDateTime end = null;
        try {
            start = LocalDateTime.parse(startTime, DATEFORMATTER);
            end = LocalDateTime.parse(endTime, DATEFORMATTER);
        } catch(RuntimeException e) {
            DATEFORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            start = LocalDateTime.parse(startTime, DATEFORMATTER);
            end = LocalDateTime.parse(endTime, DATEFORMATTER);
        }


        LocalDateTime tempDateTime = LocalDateTime.from( start );

        long years = tempDateTime.until( end, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears( years );

        long months = tempDateTime.until( end, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths( months );

        long days = tempDateTime.until( end, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays( days );


        long hours = tempDateTime.until( end, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours( hours );

        long minutes = tempDateTime.until( end, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes( minutes );

        long seconds = tempDateTime.until( end, ChronoUnit.SECONDS);
        DATEFORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.of((int) hours, (int) minutes, (int) seconds);

        return DATEFORMATTER.format(localTime);
    }
}
