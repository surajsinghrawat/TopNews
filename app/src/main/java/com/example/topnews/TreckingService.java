package com.example.topnews;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.topnews.Classes.ComanAction;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TreckingService extends Service {
    Context context;
    LocationManager locationManager;
    public static final String TAG = "LocationBreadcrumbs";
    ComanAction comanAction;
    Double latitude, longitude;
    public String deviceId;
    DatabaseReference ref;
    Long time;

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_BREADCRUMBS";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_BREADCRUMBS";
    private ArrayList<String> mPermission = new ArrayList<>();
    private static final String NOTIFICATION_CHANNEL_ID = "notification_id";
    public static String DATE_TIME_FORMAT = "yyyy/MM/dd' T 'HH:mm:ss.SSS'Z'";
    public static int INTERVAL=60000*5;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        String path = getString(R.string.firebase_path);
//
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        deviceId = telephonyManager.getDeviceId();
//        Log.d("Device", "Device:-" + deviceId);
//
//        //Get a reference to the database, so your app can perform read and write operations//
//
//        ref = FirebaseDatabase.getInstance().getReference(path).child(deviceId);
        buildNotification();
       // requestLocationUpdates();

        return super.onStartCommand(intent, flags, startId);
    }

//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        String path = getString(R.string.firebase_path);
//
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        deviceId = telephonyManager.getDeviceId();
//        Log.d("Device", "Device:-" + deviceId);
//        ref = FirebaseDatabase.getInstance().getReference(path).child(deviceId);
//        buildNotification();
//        requestLocationUpdates();
//    }

    private void buildNotification() {
        Log.d(TAG, "Start Foreground Service");
        String stop = "stop";
        registerReceiver(stopReceiver, new IntentFilter(stop));
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mNotify = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mNotify);
        }

// Create the persistent notification//

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.tracking_enabled_notif))

//Make this notification ongoing so it can’t be dismissed by the user//

                .setOngoing(true)
                .setContentIntent(broadcastIntent)
                .setSmallIcon(R.drawable.tracking_enabled);
        startForeground(1, builder.build());
    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//Unregister the BroadcastReceiver when the notification is tapped//

            unregisterReceiver(stopReceiver);

//Stop the Service//

            stopSelf();
        }
    };

    private void requestLocationUpdates() {

        LocationRequest request = new LocationRequest();

//Specify how often your app should request the device’s location//

        request.setInterval(INTERVAL);

//Get the most accurate location data available//

        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);


        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

//If the app currently has access to the location permission...//

        if (permission == PackageManager.PERMISSION_GRANTED) {

//...then request location updates//

            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        time = location.getTime();
                        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
                        String dateString = formatter.format(new Date(time));
                        Log.d("DATE", dateString);
                        //Save the location data to the database//
//                            ref.child("EmployLocation").setValue(location);
                        //  ref.setValue(location);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("DeviceID", deviceId);
                        map.put("Latitude", Double.toString(latitude));
                        map.put("Longitude", Double.toString(longitude));
                        map.put("Time", dateString);

                        ref.push().setValue(map);
                    }
                }
            }, null);
        }
    }

}
