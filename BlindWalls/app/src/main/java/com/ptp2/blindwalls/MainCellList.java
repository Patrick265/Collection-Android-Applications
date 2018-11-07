package com.ptp2.blindwalls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ptp2.blindwalls.model.BlindWall;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainCellList extends ArrayAdapter<BlindWall> {

    public MainCellList(Context context, List<BlindWall> items) {
        super( context, 0, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BlindWall wall = getItem(position);

        if( convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_main_cell_list,
                    parent,
                    false
            );
        }

        // Koppelen datasource aan UI
        TextView title = (TextView) convertView.findViewById(R.id.Cell_Title);
        TextView author = (TextView) convertView.findViewById(R.id.Cell_Author);
        final ImageView thumbnail = (ImageView) convertView.findViewById(R.id.Cell_Image);

        title.setText(wall.getTitle());
        author.setText(wall.getAuthor());

        // Set Image with picasso

        String imageUrl = "https://api.blindwalls.gallery/" + wall.getImagesUrls().get(0);
        Picasso.get().load(imageUrl).into(thumbnail);
        return convertView;

    }
}
