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

import csdev.com.black.view.layout.MapsActivity;

public class LocationCallbackHandler extends LocationCallback {

    private static LocationCallbackHandler instance;
    private Marker mCurrLocationMarker;
    private Location mLastLocation;
    private List<LatLng> polygon;
    private PolylineDraw polylineDraw;
    private GoogleMap mMap = null;
    private boolean firstTime = true;
    List<Location> locationList;
    private Activity activity;

    public LocationCallbackHandler() {
        polygon = new ArrayList<>();
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
            dfsd();
        }
    }

    public void dfsd() {
        if (mMap != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (locationList.size() > 0) {
                        //The last location in the list is the newest
                        Location location = locationList.get(locationList.size() - 1);
                        if (firstTime) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                            firstTime = false;
                        }
                        Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                        mLastLocation = location;
                        if (mCurrLocationMarker != null) {
                            mCurrLocationMarker.remove();
                        }
                        if (polylineDraw != null) {
                            polylineDraw.updatePolygon(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        }
                        //Place current location marker
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title("Current Position");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        mCurrLocationMarker = mMap.addMarker(markerOptions);

                        //move map camera
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
            });
        }
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
        polylineDraw = new PolylineDraw(polygon,mMap);
    }
}
