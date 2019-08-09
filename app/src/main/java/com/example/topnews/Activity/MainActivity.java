package com.example.topnews.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sliderviewlibrary.SliderView;
import com.example.topnews.Adapter.PageViewAdapter;
import com.example.topnews.Classes.Article;
import com.example.topnews.Classes.Data;
import com.example.topnews.Adapter.ListAdapter;
import com.example.topnews.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

   private static final String URL="https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=72daddecba9840dd8e1ab24b7e88df6f";
    public Data data;
    RecyclerView recyclerView;
    ImageView imageView;
     SliderView sliderView;
     TextView entertainment,sports,politics,business;
     ViewPager viewPager;
     PageViewAdapter pageViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar("Top News");
       // getData();
       // ImageSlideView();
        business=findViewById(R.id.business);
        sports=findViewById(R.id.sports);
        politics=findViewById(R.id.politics);
        entertainment=findViewById(R.id.entertainment);
        viewPager=findViewById(R.id.view_Pager);
        PageViewAdapter pageViewAdapter=new PageViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageViewAdapter);
        //Tabs
        pageclik();
        //pageChangeEvent
        pageChamgeEvent();


    }

    private void pageChamgeEvent(){

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
        if (i==0) {
            business.setTextSize(20);
            business.setTextColor(getResources().getColor(R.color.white));

            politics.setTextSize(15);
            politics.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            sports.setTextSize(15);
            sports.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            entertainment.setTextSize(15);
            entertainment.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        }

        if (i==1) {
            politics.setTextSize(20);
            politics.setTextColor(getResources().getColor(R.color.white));

            business.setTextSize(15);
            business.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            entertainment.setTextSize(15);
            entertainment.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            sports.setTextSize(15);
            sports.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (i==2) {
            sports.setTextSize(20);
            sports.setTextColor(getResources().getColor(R.color.white));

            business.setTextSize(15);
            business.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            entertainment.setTextSize(15);
            entertainment.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            politics.setTextSize(15);
            politics.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (i==3) {
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

    private void pageclik(){

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
    }

