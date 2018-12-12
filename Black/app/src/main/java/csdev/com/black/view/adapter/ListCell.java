package csdev.com.black.view.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.format.DateTimeFormatter;
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
            case CYCLING:
                viewHolder.getCategory().setImageResource(R.color.BicycleColor);
                break;
            case RUNNING:
                viewHolder.getCategory().setImageResource(R.color.RunningColor);
                break;
            case WALKING:
                viewHolder.getCategory().setImageResource(R.color.WalkingColor);
                break;
        }


        viewHolder.getTitle().setText(activity.getTitle());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy\tHH:mm");
        viewHolder.getDate().setText(activity.getStartTime().format(formatter));
        String distance = activity.getDistance() + " Km";
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
}
