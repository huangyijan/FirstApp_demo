package com.example.yijian.firstproject_demo;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Weather extends Fragment {


    private GridView g;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    private String[] weekDay = {};
    private String[] temp = {};
    private ImageView imageId;
    private TextView textId;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager framanager;
    private String ci;
    private String wendu;
    private Handler uiHandler=new Handler();
    private TextView location;
    private TextView templeText;
    private TextView shidu;
    private TextView qualityText;
    private TextView pm;

    public Weather() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        g = ((GridView) v.findViewById(R.id.dayList));
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        simpleAdapter = new SimpleAdapter(getContext(), dataList, R.layout.item2, new String[]{"day", "template"}, new int[]{R.id.dayText, R.id.temText});
        g.setAdapter(simpleAdapter);

        //监听回退按钮
        imageId = ((ImageView) v.findViewById(R.id.turnback));
        textId = ((TextView) v.findViewById(R.id.backText));
        location = ((TextView) v.findViewById(R.id.location1));
        templeText = ((TextView) v.findViewById(R.id.templeText));
        qualityText = ((TextView) v.findViewById(R.id.quality));
        pm = ((TextView) v.findViewById(R.id.pm25));
        shidu = ((TextView) v.findViewById(R.id.shidu));
        textId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dump();
            }
        });
        imageId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dump();
            }


        });


        initDisplay();
        return v;

    }

    /*
    * @date: 2018/8/10
    * @author: yijian
    * @description:初始化页面数据
    * @version:
    */
    private void initDisplay() {
        ci = ((MainActivity) getActivity()).transmitTheCity();
        //请求天气数据
        global.sentJson(getContext(), global.getWeatherUrl() + ci, Request.Method.GET, new Response.Listener<JSONObject>() {

            private String string;
            private Object date;
            private JSONObject test;
            private JSONArray jsonArray;
            private int hello;
            public String pm25;
            private String quality;
            public String shiduStr;
            private Runnable runnable;

            @Override
            public void onResponse(JSONObject jsonObject) {
                if (null!=jsonObject) {
                    try {
                        //初始化今天天气的各个指数
                        wendu = jsonObject.getJSONObject("data").get("wendu").toString();
                        shiduStr = jsonObject.getJSONObject("data").get("shidu").toString();
                        quality = jsonObject.getJSONObject("data").get("quality").toString();
                        pm25 = jsonObject.getJSONObject("data").get("pm25").toString();
                        //得到未来有几条天气预报
                        hello = jsonObject.getJSONObject("data").getJSONArray("forecast").length();
                        jsonArray = jsonObject.getJSONObject("data").getJSONArray("forecast");
                        weekDay= new String[hello];
                        temp=new String[hello];
                        for (int a = 0; a < hello; a++) {
                            test = jsonArray.getJSONObject(a);
                            date = test.get("date");
                            string = date.toString();
                            weekDay[a]=string;
                            temp[a]=test.get("high").toString()+"/"+test.get("low").toString()+"/"+test.get("type").toString();

                        }
                        dataList = new ArrayList<Map<String, Object>>();
                        getData();
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                location.setText(ci);
                                templeText.setText(wendu+"°");
                                shidu.setText("湿度 \n"+shiduStr);
                                qualityText.setText("空气质量\n"+quality);
                                pm.setText("PM2.5指数\n"+pm25);


                                simpleAdapter = new SimpleAdapter(getContext(), dataList, R.layout.item2, new String[]{"day", "template"}, new int[]{R.id.dayText, R.id.temText});
                                g.setAdapter(simpleAdapter);
//                                simpleAdapter.notifyDataSetChanged();
                                g.invalidateViews();

                            }

                        };
                        uiHandler.post(runnable);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void dump() {
        framanager = getActivity().getSupportFragmentManager();
        fragmentTransaction = framanager.beginTransaction();
        main_Page mainPage = new main_Page();
        fragmentTransaction.replace(R.id.linear, mainPage);
        fragmentTransaction.commit();
    }

    public List<Map<String, Object>> getData() {
        for (int i = 0; i < weekDay.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("day", weekDay[i]);
            map.put("template", temp[i]);
            dataList.add(map);
        }
        return dataList;
    }
}
