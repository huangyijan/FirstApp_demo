package com.example.yijian.firstproject_demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class Stocks extends Fragment {


    private TextView stocks;

    public Stocks() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stocks, container, false);
        stocks = ((TextView) v.findViewById(R.id.mainTitle));
        stocks.setText("Stocks");
        RequestQueue mQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest("http://result.eolinker.com/Whk7xRy1c8997ff495bb61f3ccb04c7639b6ada69ab9733?uri=test3",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
        return v;
    }

}
