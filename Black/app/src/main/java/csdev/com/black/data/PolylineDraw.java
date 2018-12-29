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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public class PolylineDraw {

    public PolylineDraw() {
    }

    public void addAllPolygon(ArrayList<LatLng> polygon, GoogleMap mMap)
    {
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        options.addAll(polygon);
        mMap.clear();
        mMap.addPolyline(options);


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : polygon) {
            builder.include(latLng);
        }

        final LatLngBounds bounds = builder.build();

        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);

        mMap.animateCamera(cu);
    }

    public void updatePolygonFresh(ArrayList<Polyline> polylines, String identifier, GoogleMap mMap, LatLng first, LatLng second)
    {
        ArrayList<LatLng> cur = new ArrayList<>();
        cur.add(first);
        cur.add(second);

        Polyline polyline = mMap.addPolyline(new PolylineOptions()
                .addAll(cur)
                .width(15)
                .color(Color.BLUE)
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

    public void updatePolygon(LatLng old,LatLng fresh, GoogleMap mMap, List<LatLng> polygon, ArrayList<Polyline> polylines, String identifier){
        polygon.add(fresh);
        ArrayList<LatLng> cur = new ArrayList<>();
        cur.add(old);
        cur.add(fresh);

        Polyline polyline = mMap.addPolyline(new PolylineOptions()
                .addAll(cur)
                .width(15)
                .color(Color.BLUE)
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


}

