package com.example.topnews.Classes;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class RequestClass extends JsonObjectRequest {

    public static final String server="https://newsapi.org/v2/top-headlines?country=us&category=";
    public static final String key="&apiKey=72daddecba9840dd8e1ab24b7e88df6f";

    public static final String BUSINES="business";
    public static final String POLITICS="politics";
    public static final String SPORT="Sports";
    public static final String ENTERTENMENT="entertainment";

    Context context;
    public RequestClass(int method, String endpoint, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                        @Nullable Response.ErrorListener errorListener,Context mContext) {
        super(method, server+endpoint+key, jsonRequest, listener, errorListener);
        this.context=mContext;
    }
}
