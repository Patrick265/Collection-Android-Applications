package csdev.com.black.data;

import android.app.Application;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import csdev.com.black.R;
import csdev.com.black.model.PolylineInfo;

public class PolylineDraw {

    public PolylineDraw() {
    }

    public void updatePolygonFresh(ArrayList<Polyline> polylines, String identifier, GoogleMap mMap, LatLng first, LatLng second, PolylineInfo f)
    {
        ArrayList<LatLng> cur = new ArrayList<>();
        cur.add(first);
        cur.add(second);

        int color;
        if(f.getSpeed() >= 30.00)
        {
            color = Color.rgb(255,0,0);
        }
        else if(f.getSpeed() >= 20.00)
        {
            color = Color.rgb(255,126,0);
        }
        else
        {
            color = Color.rgb(0,255,27);
        }


        Polyline polyline = mMap.addPolyline(new PolylineOptions()
                .addAll(cur)
                .width(20)
                .color(color)
                .geodesic(true));
        polyline.setClickable(true);
        polyline.setTag(identifier);
        polylines.add(polyline);
    }

    public void setCamera(ArrayList<LatLng> polygon, GoogleMap mMap)
    {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : polygon) {
            builder.include(latLng);
        }

        final LatLngBounds bounds = builder.build();

        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        mMap.animateCamera(cu);
    }

    public void updatePolygon(LatLng old,LatLng fresh, GoogleMap mMap, List<LatLng> polygon, ArrayList<Polyline> polylines, String identifier, int color){
        polygon.add(fresh);
        ArrayList<LatLng> cur = new ArrayList<>();
        cur.add(old);
        cur.add(fresh);

        Polyline polyline = mMap.addPolyline(new PolylineOptions()
                .addAll(cur)
                .width(15)
                .color(color)
                .geodesic(true));
        polyline.setClickable(true);
        polyline.setTag(identifier);
        polylines.add(polyline);



//        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true).clickable(true);
//        options.addAll(polygon);
//        mMap.clear();
//        mMap.addPolyline(options);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : polygon) {
            builder.include(latLng);
        }

        final LatLngBounds bounds = builder.build();

        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        mMap.animateCamera(cu);
    }

    public void addList(GoogleMap mMap, List<LatLng> polygon)
    {
        PolylineOptions poly = new PolylineOptions();
        poly.addAll(polygon);
        poly.width(15);
        poly.color(Color.RED);
        poly.geodesic(true);


        mMap.addPolyline(poly);
    }


}

