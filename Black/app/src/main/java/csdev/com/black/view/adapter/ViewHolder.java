package csdev.com.black.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import csdev.com.black.R;
import csdev.com.black.model.SportActivity;

public class ViewHolder extends RecyclerView.ViewHolder
{
    private TextView title;
    private TextView date;
    private TextView distance;
    private ImageView category;

    public ViewHolder(@NonNull View view)
    {

        super(view);
        this.title = view.findViewById(R.id.ListCell_Title_TextView);
        this.date = view.findViewById(R.id.ListCell_date_TextView);
        this.distance = view.findViewById(R.id.ListCell_distance_TextView);
        this.category = view.findViewById(R.id.ListCell_category);
    }

    public void bindActivity(final SportActivity activty, final ListOnItemClickListener listener) {
        super.itemView.setOnClickListener(v ->
        {
            Log.i("CLICKED", "CLICKED");
            Toast.makeText(itemView.getContext(), activty.getTitle(), Toast.LENGTH_SHORT);
            listener.onItemClick(activty);
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

    public ImageView getCategory()
    {
        return category;
    }


}
