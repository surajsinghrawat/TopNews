package com.example.topnews;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

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


public class GPSUpdateService extends IntentService {
//    Context context;
//    Double latitude, longitude;
      Handler handler;
      DatabaseReference ref;
      String deviceId;
      Long time;
      LocationManager locationManager;
      public static String DATE_TIME_FORMAT = "yyyy/MM/dd' T 'HH:mm:ss.SSS'Z'";
    public GPSUpdateService() {
        super("GPSUPDATE");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String path = getString(R.string.firebase_path);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();
        Log.d("Device", "Device:-" + deviceId);
        context=this;
        handler=new Handler();
        ref = FirebaseDatabase.getInstance().getReference(path).child(deviceId);
        gps();

    }


//    private void getDiviceId(){
//        String path = getString(R.string.firebase_path);
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        deviceId = telephonyManager.getDeviceId();
//        Log.d("Device", "Device:-" + deviceId);
//        context=this;
//        handler=new Handler();
//        ref = FirebaseDatabase.getInstance().getReference(path).child(deviceId);
//       requestLocationUpdates();
//    }
//
////    @Override
////    public void onCreate() {
////        super.onCreate();
////        String path = getString(R.string.firebase_path);
////        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
////        deviceId = telephonyManager.getDeviceId();
////        Log.d("Device", "Device:-" + deviceId);
////        context=this;
////        handler=new Handler();
////        ref = FirebaseDatabase.getInstance().getReference(path).child(deviceId);
////        requestLocationUpdates();
////    }
//
//    private void requestLocationUpdates() {
//
//        LocationRequest request = new LocationRequest();
//        request.setInterval(10000);
//        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
//        int permission = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//
//        if (permission == PackageManager.PERMISSION_GRANTED) {
//
//            client.requestLocationUpdates(request, new LocationCallback() {
//                @Override
//                public void onLocationResult(LocationResult locationResult) {
//
//                    Location location = locationResult.getLastLocation();
//                    if (location != null) {
//                        latitude = location.getLatitude();
//                        longitude = location.getLongitude();
//                        time = location.getTime();
//                        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
//                        String dateString = formatter.format(new Date(time));
//                        Log.d("DATE", dateString);
//                        //Save the location data to the database//
////                            ref.child("EmployLocation").setValue(location);
//                        //  ref.setValue(location);
//                        HashMap<String, String> map = new HashMap<>();
//                        map.put("DeviceID", deviceId);
//                        map.put("Latitude", Double.toString(latitude));
//                        map.put("Longitude", Double.toString(longitude));
//                        map.put("Time", dateString);
//
//                        ref.push().setValue(map);
//                    }
//                }
//            }, null);
//        }
//    }
Context context;
    public static final String LOG_TAG = "GPS_UPDATE_SERVICE";
    Handler mHandler;
   // UserSession userSession;
    double longitude;
    double latitude;
    String version, provider;
    String dateString;

//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        //getDiviceId();
//    }

    private void gps(){
        HashMap<String,String>map=new HashMap<>();
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setBearingRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        provider = LocationManager.GPS_PROVIDER;


        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(context, "Permission Required", Toast.LENGTH_SHORT).show();
            }
        Location coordinates = locationManager.getLastKnownLocation(provider);
            if (coordinates != null) {
                latitude = coordinates.getLatitude();
                longitude = coordinates.getLongitude();
                time = coordinates.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
                dateString = formatter.format(new Date(time));
            }

        }

        String lat = Double.toString(latitude);
        String lon = Double.toString(longitude);

        map.put("DeviceId", deviceId);
        map.put("GPS_Latitude", lat);
        map.put("GPS_Longitude", lon);
        map.put("Time",dateString);

        Log.d("DiviceId", deviceId);
        Log.d("GPS_Latitude", "Latitude" + lat);
        Log.d("GPS_Longitude", "Longitude" + lon);
        ref.push().setValue(map);
    }
}