package csdev.com.black.data;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

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
        Log.i("Message: ","Location changed, " + location.getAccuracy() + " , " + location.getLatitude()+ "," + location.getLongitude());
        updatePolygon(location.getLatitude(),location.getLongitude());
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
        mMap.addPolygon(new PolygonOptions()
                .addAll(polygon)
                .strokeColor(Color.YELLOW)
                .strokeWidth(10)
                .fillColor(Color.YELLOW)
        );
    }
}

