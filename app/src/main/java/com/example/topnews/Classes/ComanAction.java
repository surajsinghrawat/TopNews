package com.example.topnews.Classes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

public class ComanAction {



//    public static String getDeviceId(Context c) {
//        TelephonyManager telephonyManager = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions((Activity) c,new String[]{Manifest.permission.READ_PHONE_STATE},10);
//            return null;
//        }
//        String device_id = telephonyManager.getDeviceId();
//
//        if (device_id == null || device_id.isEmpty()) {
//            //no telephony manager
//            String androidId = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);
//            device_id = androidId;
//        }
//
//        return device_id;
//    }
}
