package com.example.topnews;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
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


public class GPSUpdateService extends JobIntentService {
    Context context;
    Double latitude, longitude;
    Long time;
    String deviceID;
    DatabaseReference ref;
    public static String DATE_TIME_FORMAT = "yyyy/MM/dd' T 'HH:mm:ss.SSS'Z'";
    public static int JOB_ID=1001;
    public static final String TAG="GPSService";

    FusedLocationProviderClient client;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, GPSUpdateService.class, JOB_ID, work);

    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG,"Job Intent Service Started");
        context=this;


        String path = getString(R.string.firebase_path);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceID = telephonyManager.getDeviceId();
        Log.d("Device", "Device:-" + deviceID);
        context = this;
        ref = FirebaseDatabase.getInstance().getReference(path).child(deviceID);
        for (int i = 0; i < 1; i++) {
            GPSUpdateLocation();
        }
    }
    private void GPSUpdateLocation() {
        LocationRequest request = LocationRequest.create();
        request.setInterval(300000);
        request.setFastestInterval(300000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        client = LocationServices.getFusedLocationProviderClient(this);
        client.requestLocationUpdates(request, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.d(TAG, "After onLocationResult function Log");
                Location location = locationResult.getLastLocation();
                Log.d(TAG, "Location" + location);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    time = location.getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
                    String dateString = formatter.format(new Date(time));
                    Log.d("DATE", dateString);
                    HashMap<String, String> map = new HashMap<>();

                    map.put("DeviceID", deviceID);
                    map.put("GPS_Latitude", Double.toString(latitude));
                    map.put("GPS_Longitude", Double.toString(longitude));
                    map.put("Time", dateString);

                    Log.d(TAG,deviceID);
                    Log.d(TAG, String.valueOf(latitude));
                    Log.d(TAG, String.valueOf(longitude));
                    Log.d(TAG,dateString);

                    ref.push().setValue(map);
                }
            }
        }, Looper.getMainLooper());

    }

    @Override
    public boolean onStopCurrentWork() {
        return super.onStopCurrentWork();
    }
}