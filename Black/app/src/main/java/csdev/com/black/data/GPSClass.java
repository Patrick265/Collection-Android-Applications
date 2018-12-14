package csdev.com.black.data;

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
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class GPSClass implements LocationListener {

    private List<LatLng> polygon;
    private GoogleMap mMap;

    public GPSClass(List<LatLng> polygon, GoogleMap mMap) {
        this.polygon = polygon;
        this.mMap = mMap;
    }

    @Override
    public void onLocationChanged(Location location) {
       // Log.i("WOLLA","Location changed, " + location.getAccuracy() + " , " + location.getLatitude()+ "," + location.getLongitude());
       // updatePolygon(location.getLatitude(),location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void updatePolygon( double latitude, double longitude){
        polygon.add(new LatLng(latitude,longitude));
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
}

