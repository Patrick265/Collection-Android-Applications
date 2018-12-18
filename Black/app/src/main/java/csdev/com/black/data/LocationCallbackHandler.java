package csdev.com.black.data;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class LocationCallbackHandler extends LocationCallback {

    private static LocationCallbackHandler instance;
    private List<Location> locationList;
    private ArrayList<LocationCallbackListener> listeners;

    public LocationCallbackHandler() {
        listeners = new ArrayList<>();
        locationList = new ArrayList<>();
        instance = this;
    }

    public static LocationCallbackHandler getInstance() {
        return instance;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        locationList = locationResult.getLocations();
        if (locationList.size() > 0) {
            Location location = locationList.get(locationList.size() - 1);
            Log.i("WEW", locationResult.getLastLocation().getLatitude() + " " + locationResult.getLastLocation().getLatitude());
            for (LocationCallbackListener e : listeners) {
                e.onLocationAvailable(location);
            }
        }
    }

    public void addListener(LocationCallbackListener listener) {
        listeners.add(listener);
    }

}
