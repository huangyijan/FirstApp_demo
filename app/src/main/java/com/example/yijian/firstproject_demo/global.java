package com.example.yijian.firstproject_demo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


import static android.content.ContentValues.TAG;

/**
 * 创建时间： 2018/8/8
 * 创建人：yijian
 * 功能描述：新建个人工具类
 */

public class global {

    public static String WeatherUrl="https://www.sojson.com/open/api/weather/json.shtml?city=";

    public static String getWeatherUrl(){
        return WeatherUrl;
    }

    public static void dump(FragmentActivity fragmentActivity, Fragment f) {
        Fragment fragment=f;
        FragmentManager framanager;
        android.support.v4.app.FragmentTransaction fragmentTransaction;
        framanager = fragmentActivity.getSupportFragmentManager();
        fragmentTransaction = framanager.beginTransaction();
        fragmentTransaction.replace(R.id.linear, fragment);
        fragmentTransaction.commit();
    }


    public static void sentString (Context context,String url,int method,Response.Listener<String> listener){
        RequestQueue mQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(method,url, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "onErrorResponse: ", volleyError);
            }
        });
        mQueue.add(stringRequest);
    }


    public static void sentJson (Context context,String url,int method,Response.Listener<JSONObject> listene){
        JSONObject json=new JSONObject();

        RequestQueue mQueue= Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(method,url,null,listene, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "onErrorResponse: ",volleyError );
            }
        });
        mQueue.add(jsonObjectRequest);
    }


}
