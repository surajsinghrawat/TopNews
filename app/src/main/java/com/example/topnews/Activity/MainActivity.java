package com.example.topnews.Activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.topnews.Adapter.PageViewAdapter;
import com.example.topnews.ExampleJobIntentService;
import com.example.topnews.GPSUpdateService;
import com.example.topnews.R;
import com.example.topnews.TreckingService;





public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    TextView entertainment, sports, politics, business;
    ViewPager viewPager;

    private static final int PERMISSIONS_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        business = findViewById(R.id.business);
        sports = findViewById(R.id.sports);
        politics = findViewById(R.id.politics);
        entertainment = findViewById(R.id.entertainment);
        viewPager = findViewById(R.id.view_Pager);
        PageViewAdapter pageViewAdapter = new PageViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageViewAdapter);

        pageclik();

        pageChamgeEvent();

        setupToolbar();
        checkGPSProvider();


        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("GPSON","GPS IS ON");

        }
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION+Manifest.permission.READ_PHONE_STATE+Manifest.permission.ACCESS_COARSE_LOCATION);

            //If the location permission has been granted, then start the TrackerService//

        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {

            //If the app doesn’t currently have access to the user’s location, then request access//

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST);
        }


        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.shareApp:
                        Intent intent = new Intent(Intent.ACTION_SEND);

                        final String aapPackagName = getApplicationContext().getPackageName();
                        String strAppLink = "";
                        try {
                            strAppLink = "http://play.googal.com/store/app/details?id=" + aapPackagName;
                        } catch (android.content.ActivityNotFoundException anfe) {
                            strAppLink = "http://play.googal.com/store/app/details?id=" + aapPackagName;
                        }
                        intent.setType("text/link");
                        String shareBody = "Hey! Download The AmaZing App" + "\n" + "" + strAppLink;
                        String shareSub = "App NAME/TITLE";
                        intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(intent, "Share Using"));
                        break;
                    case R.id.news_business:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.news_politics:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.news_sport:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.news_entertainment:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.location:
                        Intent intentloc = new Intent(MainActivity.this, LocationActivity.class);
                        startActivity(intentloc);
                        break;

                }
                return false;
            }
        });





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            //If the permission has been granted...//

        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 3
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //...then start the GPS tracking service//

            startTrackerService();

        } else {

            //If the user denies the permission request, then display a toast with some more information//

            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTrackerService() {

        startService(new Intent(this, TreckingService.class));

        Intent serviceIntent=new Intent(this, ExampleJobIntentService.class);
        ExampleJobIntentService.enqueueWork(this,serviceIntent);

        //Notify the user that tracking has been enabled//

        Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show();

    }


    private void pageChamgeEvent() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                onTabChange(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void onTabChange(int i) {
        if (i == 0) {
            business.setTextSize(20);
            business.setTextColor(getResources().getColor(R.color.white));

            politics.setTextSize(15);
            politics.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            sports.setTextSize(15);
            sports.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            entertainment.setTextSize(15);
            entertainment.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        }

        if (i == 1) {
            politics.setTextSize(20);
            politics.setTextColor(getResources().getColor(R.color.white));

            business.setTextSize(15);
            business.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            entertainment.setTextSize(15);
            entertainment.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            sports.setTextSize(15);
            sports.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (i == 2) {
            sports.setTextSize(20);
            sports.setTextColor(getResources().getColor(R.color.white));

            business.setTextSize(15);
            business.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            entertainment.setTextSize(15);
            entertainment.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            politics.setTextSize(15);
            politics.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (i == 3) {
            entertainment.setTextSize(20);
            entertainment.setTextColor(getResources().getColor(R.color.white));

            business.setTextSize(15);
            business.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            politics.setTextSize(15);
            politics.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            sports.setTextSize(15);
            sports.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    private void pageclik() {

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        politics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });
    }


//    private void getData()
//    {
//
//        recyclerView = findViewById(R.id.itemList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        StringRequest request=new StringRequest(URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("code",response);
//                GsonBuilder gsonBuilder=new GsonBuilder();
//                Gson gson=gsonBuilder.create();
//                Data data =gson.fromJson(response,Data.class);
//                recyclerView.setAdapter(new ListAdapter(MainActivity.this,data));
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();
//            }
//        });
//
//        RequestQueue queue= Volley.newRequestQueue(this);
//        queue.add(request);
//    }

    private void setupActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }


//    private void ImageSlideView()
//    {
////        Data data1=data;
////        List<Article>first=data1.getArticles();
//        sliderView = findViewById(R.id.sliderView);
//        ArrayList<Integer> slideImages = new ArrayList<>();
//        slideImages.add(R.drawable.web);
//        slideImages.add(R.drawable.ss);
//        slideImages.add(R.drawable.rr);
//        slideImages.add(R.drawable.img);
//        sliderView.setImages(slideImages);
//
//
//        TimerTask task = sliderView.getTimerTask();
//        Timer timer = new Timer();
//        timer.schedule(task,25,4000);
//    }


    private void setupToolbar() {
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void checkGPSProvider() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            Toast.makeText(this, "Location not available.", Toast.LENGTH_SHORT).show();
        } else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("GPS is turned off. This app requires you to turn on the GPS to work.");
            builder.setCancelable(false);
            builder.setPositiveButton("Turn On GPS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                    dialogInterface.dismiss();
                }
            });

            builder.create().show();
        }
    }

//    public String getDeviceId(Context c) {
//        TelephonyManager telephonyManager = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
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

    public void startGPSUpdates() {

        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, GPSUpdateService.class);
        PendingIntent alarmIntent = PendingIntent.getService(this, 0, intent, 0);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        60 * 1000,
                300000, alarmIntent);

        Log.d("GPSUPDATESSTART", "GPS Updater for every 5 min");
    }

}

