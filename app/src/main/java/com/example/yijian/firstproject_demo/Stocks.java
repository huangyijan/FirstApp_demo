package com.example.yijian.firstproject_demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class Stocks extends Fragment {


    private TextView stocks;
    private ArrayList<Map<String, Object>> dataList;
    private GridView grid;

    private SimpleAdapter simpleAdapter;
    public Stocks() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stocks, container, false);
        stocks = ((TextView) v.findViewById(R.id.mainTitle));
        grid = ((GridView) v.findViewById(R.id.eleShop));
        stocks.setText("Stocks");
        RequestQueue mQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest("http://result.eolinker.com/Whk7xRy1c8997ff495bb61f3ccb04c7639b6ada69ab9733?uri=test3",
                new Response.Listener<String>() {

                    private String address;
                    private JsonNode jsonNode;

                    @Override
                    public void onResponse(String response) {
                        Log.i("tag", "onResponse: "+response);

                        ObjectMapper mapper=new ObjectMapper();
                        try {
                            JsonNode node = mapper.readTree(response);
                            Log.i("20v", "onResponse: "+node.size());
                            jsonNode = node.get(1);
                            dataList = new ArrayList<Map<String, Object>>();
                            for (int k = 0; k < node.size(); k++) {
                                address = node.get(k).get("address").toString();
                                Map<String,Object> map =new HashMap<String, Object>();
                                map.put("address",address);
                                Log.i(TAG, "onResponse: "+address);
                                dataList.add(map);

                            }
                            simpleAdapter=new SimpleAdapter(getContext(),dataList,R.layout.item3,new String[]{"address"},new int[]{R.id.address});
                            grid.setAdapter(simpleAdapter);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String json = "{\"name\":\"小民\",\"age\":20,\"birthday\":844099200000,\"email\":\"xiaomin@sina.com\"}";
                        try {
                            User user = mapper.readValue(json,User.class);
                            System.out.println(user.getAge());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
