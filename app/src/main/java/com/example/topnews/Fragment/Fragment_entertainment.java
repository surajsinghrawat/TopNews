package com.example.topnews.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.topnews.Adapter.ListAdapter;
import com.example.topnews.Classes.Data;
import com.example.topnews.Classes.RequestClass;
import com.example.topnews.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Objects;
import java.util.zip.Inflater;

public class Fragment_entertainment extends Fragment {
    RecyclerView recyclerView;
    public static final String URL="https://newsapi.org/v2/top-headlines?country=us&category=entertainment&apiKey=72daddecba9840dd8e1ab24b7e88df6f";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_entertainment,container,false);

        recyclerView = layout.findViewById(R.id.itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RequestClass request=new RequestClass(Request.Method.GET, RequestClass.ENTERTENMENT,
                new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Data data = Data.parseResponse(response.toString());
                recyclerView.setAdapter(new ListAdapter(getActivity(),data));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        },getActivity());

        RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        queue.add(request);

        return layout;
    }
}
