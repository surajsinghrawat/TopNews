package com.example.topnews.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.topnews.Adapter.ListAdapter;
import com.example.topnews.Classes.Data;
import com.example.topnews.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Fragment_politics extends Fragment {
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public static final String URL="https://newsapi.org/v2/top-headlines?country=us&category=politics&apiKey=72daddecba9840dd8e1ab24b7e88df6f";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout= inflater.inflate(R.layout.fragment_politics,container,false);

        recyclerView = layout.findViewById(R.id.itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("code", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Data data = gson.fromJson(response, Data.class);
                recyclerView.setAdapter(new ListAdapter(getActivity(), data));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);

        return layout;
    }
}
