package csdev.com.black.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import csdev.com.black.R;
import csdev.com.black.model.SportActivity;

public class ViewHolder extends RecyclerView.ViewHolder
{
    private TextView title;
    private TextView date;
    private TextView distance;
    private ImageView icon;

    public ViewHolder(@NonNull View view)
    {

        super(view);
        this.title = view.findViewById(R.id.ListCell_Title_TextView);
        this.date = view.findViewById(R.id.ListCell_date_TextView);
        this.distance = view.findViewById(R.id.listCell_distance_TextView);
        this.icon = view.findViewById(R.id.ListCell_imageview_SportActivityType);
    }

    public void bindActivity(final SportActivity activty, final ListOnItemClickListener listener) {
        super.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(activty);
            }
        });
    }

    public TextView getTitle()
    {
        return title;
    }

    public TextView getDate()
    {
        return date;
    }

    public TextView getDistance()
    {
        return distance;
    }

    public ImageView getIcon()
    {
        return icon;
    }
}
