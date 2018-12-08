package csdev.com.black.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        viewHolder.getTitle().setText(activity.getTitle());
        viewHolder.getDate().setText(activity.getStartTime().toString());
        String distance = activity.getDistance() + " Km";
        viewHolder.getDistance().setText(distance);

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
