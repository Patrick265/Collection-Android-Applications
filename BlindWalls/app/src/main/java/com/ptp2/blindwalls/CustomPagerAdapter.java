package com.ptp2.blindwalls;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<String> urls;

    public CustomPagerAdapter(Context context, List<String> urls)
    {
     this.context = context;
     this.urls = urls;
     layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View itemView = layoutInflater.inflate(R.layout.activity_pager, null);
        ImageView imageView = itemView.findViewById(R.id.imageView);
        Picasso.get().load("https://api.blindwalls.gallery/" + urls.get(position)).into(imageView);
        ViewPager vp = (ViewPager) container;

        vp.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((ConstraintLayout) object);
    }
}
