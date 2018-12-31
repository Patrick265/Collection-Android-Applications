package csdev.com.black.view.adapter.spinner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import csdev.com.black.R;

public class SpinnerSettingsActivity extends ArrayAdapter<String>
{

    private Context context;
    private ArrayList<String> categories;

    public SpinnerSettingsActivity(Context context, ArrayList<String> categories)
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
                    R.layout.activity_spinner_settings, parent, false
            );
        }


        TextView spinnerItem = convertView.findViewById(R.id.spinner_settings_text);
        spinnerItem.setText(this.categories.get(position));


        return convertView;
    }
}
