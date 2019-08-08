package com.example.topnews.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sliderviewlibrary.SliderView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar("Top News");
        getData();
       // ImageSlideView();

    }

    private void getData()
    {

        recyclerView = findViewById(R.id.itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        StringRequest request=new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("code",response);
                GsonBuilder gsonBuilder=new GsonBuilder();
                Gson gson=gsonBuilder.create();
                Data data =gson.fromJson(response,Data.class);
                recyclerView.setAdapter(new ListAdapter(MainActivity.this,data));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);
    }

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

