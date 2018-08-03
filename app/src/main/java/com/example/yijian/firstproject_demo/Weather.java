package com.example.yijian.firstproject_demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Weather extends Fragment {


    private GridView g;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    private String[] weekDay = {"Wednestday", "ThursDay", "FriDay", "Saturday", "sunDay", "Wednestday", "ThursDay", "FriDay", "Saturday", "sunDay"};
    private String[] temp = {"29°/18°", "21°/12°", "23°/10°", "24°/13°", "28°/16°", "29°/18°", "21°/12°", "23°/10°", "24°/13°", "28°/16°"};
    private ImageView imageId;
    private TextView textId;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager framanager;

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

        return v;

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
