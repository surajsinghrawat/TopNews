package com.example.topnews;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.JobIntentService;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ExampleJobIntentService extends JobIntentService {
    DatabaseReference ref;
    String deviceId;
    Long time;
    LocationManager locationManager;
    public static String DATE_TIME_FORMAT = "yyyy/MM/dd' T 'HH:mm:ss.SSS'Z'";
    Context context;
    public static final String LOG_TAG = "GPS_UPDATE_SERVICE";
    double longitude;
    double latitude;


    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, ExampleJobIntentService.class, 1234, work);
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String path = getString(R.string.firebase_path);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();
        Log.d("Device", "Device:-" + deviceId);
        context = this;
        ref = FirebaseDatabase.getInstance().getReference(path).child(deviceId);
//        for (int i = 0; i < 1; i++) {
//            GPSUpdateLocation();
//        }
        GPSUpdateLocation();
    }

    private void GPSUpdateLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 100.0f, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        time = location.getTime();
                        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
                        String dateString = formatter.format(new Date(time));
                        Log.d("DATE", dateString);
                        HashMap<String, String> map = new HashMap<>();

                        map.put("DeviceID", deviceId);
                        map.put("GPS_Latitude", Double.toString(latitude));
                        map.put("GPS_Longitude", Double.toString(longitude));
                        map.put("Time", dateString);

                        Log.d(LOG_TAG,deviceId);
                        Log.d(LOG_TAG, String.valueOf(latitude));
                        Log.d(LOG_TAG, String.valueOf(longitude));
                        Log.d(LOG_TAG,dateString);

                        ref.push().setValue(map);
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            }, Looper.getMainLooper());
        }
    }

    @Override
    public boolean onStopCurrentWork() {
        return super.onStopCurrentWork();
    }
}
