package csdev.com.black.data;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import csdev.com.black.R;

import static csdev.com.black.data.App.CHANNEL_ID;

public class MyService extends Service {

    LocationRequest mLocationRequest;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallbackHandler locationCallbackHandler;
    Looper looper;
    HandlerThread handlerThread;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // String input = intent.getStringExtra("inputExtra");

     //   Intent notificationIntent = new Intent(this, MainActivity.class);
      //  PendingIntent pendingIntent = PendingIntent.getActivity(this,
         //       0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Black")
                .setContentText("Started sport activity")
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.ic_launcher_foreground))
                .build();

        startForeground(1, notification);

        handlerThread = new HandlerThread("MyHandlerThread");
        looper = handlerThread.getLooper();
        handlerThread.start();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(4000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(8);

        locationCallbackHandler = LocationCallbackHandler.getInstance();


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Location Permission already granted
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallbackHandler, looper);
                    }
                }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mFusedLocationClient.removeLocationUpdates(locationCallbackHandler);
        stopSelf();
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}