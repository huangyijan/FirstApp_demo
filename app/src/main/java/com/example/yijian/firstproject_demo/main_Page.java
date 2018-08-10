package com.example.yijian.firstproject_demo;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class main_Page extends Fragment {


    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simAdapt;
    private int[] IconName = {R.drawable.sun, R.drawable.list, R.drawable.bell, R.drawable.stocks};
    private String[] StrName = {"天气", "24 资讯", "12 消息", "地图"};
    private Handler uihandler=new Handler();
    private TextView title;
    private String city;
    private Calendar cal;
    private int year;
    private int month;
    private int day;
    private TextView time;
    private int week;

    public main_Page() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main__page, container, false);
        gridView = (GridView) v.findViewById(R.id.grid);
        title = ((TextView) v.findViewById(R.id.MainPageTitle));
        time = ((TextView) v.findViewById(R.id.DateTime));
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        simAdapt = new SimpleAdapter(getActivity(), dataList, R.layout.item, new String[]{"image", "name"}, new int[]{R.id.imageV, R.id.text2});
        gridView.setAdapter(simAdapt);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Weather weather = new Weather();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.linear, weather)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        New new1 = new New();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.linear, new1)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 2:
                        Remind remind = new Remind();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.linear, remind)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 3:
//                        Stocks stocks=new Stocks();
                        com.example.yijian.firstproject_demo.Map map = new com.example.yijian.firstproject_demo.Map();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.linear, map)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }
        });
        return v;
    }


    /*
    * @date: 2018/8/9
    * @author: yijian
    * @description: 初始化页面上面的参数
    * @version:
    */
    public void initDisplay() {
//        global.sentJson(getContext(), global.getWeatherUrl()+city, Request.Method.GET, new Response.Listener<JSONObject>() {
//            private String wendu;
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                if (jsonObject!=null) {
//                    try {
//                        wendu = jsonObject.getJSONObject("data").get("wendu").toString();
//                        Runnable runnable=new Runnable() {
//                            @Override
//                            public void run() {
//                                StrName[0]=wendu+"°";
//                                dataList=new ArrayList<Map<String, Object>>();
//                                getData();
//                                simAdapt = new SimpleAdapter(getActivity(), dataList, R.layout.item, new String[]{"image", "name"}, new int[]{R.id.imageV, R.id.text2});
//                                gridView.setAdapter(simAdapt);
//                                simAdapt.notifyDataSetChanged();
//                                gridView.invalidateViews();
//
//                            }
//                        };
//                        uihandler.post(runnable);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(),"线程出现问题",Toast.LENGTH_SHORT);
//                    }
//                }
//            }
//        });

        //初始化时间
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        week = cal.get(Calendar.DAY_OF_WEEK);
        String weekDay=null;
        switch (week) {
            case 1:
                weekDay="周日";
                break;
            case 2:
                weekDay="周一";
                break;
            case 3:
                weekDay="周二";
                break;
            case 4:
                weekDay="周三";
                break;
            case 5:
                weekDay="周四";
                break;
            case 6:
                weekDay="周五";
                break;
            case 7:
                weekDay="周六";
                break;
        }
        time.setText(weekDay+","+year+"-"+month+"-"+day);

    }

    @Override
    public void onStart() {
        System.out.println("onStar");
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", IconName[i]);
            map.put("name", StrName[i]);
            dataList.add(map);
        }
        return dataList;
    }
    public void changetitle(String str){
        title.setText(str);
        city=str;

    }
}
