package com.example.topnews;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
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

public class ExampleJobIntentService extends JobIntentService {
    Handler handler;
    DatabaseReference ref;
    String deviceId;
    Long time;
    LocationManager locationManager;
    public static String DATE_TIME_FORMAT = "yyyy/MM/dd' T 'HH:mm:ss.SSS'Z'";
    Context context;
    public static final String LOG_TAG = "GPS_UPDATE_SERVICE";
    Handler mHandler;
    // UserSession userSession;
    double longitude;
    double latitude;
    String version, provider;
    String dateString;

   public static void enqueueWork(Context context,Intent work){
        enqueueWork(context,ExampleJobIntentService.class,1234,work);
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
       // locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String path = getString(R.string.firebase_path);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();
        Log.d("Device", "Device:-" + deviceId);
        context=this;
        ref = FirebaseDatabase.getInstance().getReference(path).child(deviceId);
        for (int i = 0; i < 1; i++) {
            GPSUpdateLocation();
        }

    }

    private void GPSUpdateLocation(){
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            version = pInfo.versionName;
            Log.d("VERSION",version);
        }catch(PackageManager.NameNotFoundException e){
            Log.d("Version","Version Not Found");
        }
        LocationRequest request=new LocationRequest();
        request.setInterval(300000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        FusedLocationProviderClient client= LocationServices.getFusedLocationProviderClient(this);

        client.requestLocationUpdates(request,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location=locationResult.getLastLocation();
                if (location!=null){
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    time = location.getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
                    String dateString = formatter.format(new Date(time));
                    Log.d("DATE", dateString);
                    HashMap<String, String> map = new HashMap<>();

                    map.put("DeviceID", deviceId);
                    //map.put("EmployID",EmployId);
                   // map.put("Version",version);
                    map.put("GPS_Latitude", Double.toString(latitude));
                    map.put("GPS_Longitude", Double.toString(longitude));
                    map.put("Time",dateString);

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
