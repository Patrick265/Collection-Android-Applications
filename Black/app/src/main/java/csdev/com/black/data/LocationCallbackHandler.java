package csdev.com.black.data;

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

    private Marker mCurrLocationMarker;
    private Location mLastLocation;
    private List<LatLng> polygon;
    private PolylineDraw polylineDraw;
    private GoogleMap mMap;
    private boolean firstTime = true;

    public LocationCallbackHandler(GoogleMap mGoogleMap)
    {
        polygon = new ArrayList<>();
        polylineDraw = new PolylineDraw(polygon,mGoogleMap);
        mMap = mGoogleMap;

    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        List<Location> locationList = locationResult.getLocations();
        if (locationList.size() > 0) {
            //The last location in the list is the newest
            Location location = locationList.get(locationList.size() - 1);
            if(firstTime)
            {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),15));
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
}
