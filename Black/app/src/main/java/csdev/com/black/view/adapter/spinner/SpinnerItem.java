package csdev.com.black.view.adapter.spinner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import csdev.com.black.R;


public class SpinnerItem extends ArrayAdapter<String>
{
    private Context context;
    private ArrayList<String> categories;

    public SpinnerItem(Context context, ArrayList<String> categories)
    {
        super(context, 0, categories);
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_layout, parent, false
            );
        }

        ImageView categoryImage = convertView.findViewById(R.id.spinner_imageview);
        TextView categoryName = convertView.findViewById(R.id.spinner_textview);

        String currentItem = getItem(position);

        if (currentItem != null) {
            switch (currentItem)
            {
                case "Cycling":
                    categoryImage.setImageResource(R.color.BicycleColor);
                    categoryName.setText(currentItem);
                    break;
                case "Running":
                    categoryImage.setImageResource(R.color.RunningColor);
                    categoryName.setText(currentItem);
                    break;
                case "Walking":
                    categoryImage.setImageResource(R.color.WalkingColor);
                    categoryName.setText(currentItem);
                    break;
            }
        }
        return convertView;
    }
}
