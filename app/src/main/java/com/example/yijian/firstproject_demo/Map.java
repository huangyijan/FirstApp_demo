package com.example.yijian.firstproject_demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.MyLocationStyle;


/**
 * A simple {@link Fragment} subclass.
 */
public class Map extends Fragment {

    private AMap aMap;
    private MyLocationStyle myLocationStyle;

    public Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_map, container, false);
        //初始化位置
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);

        //初始化地图
        MapView mapView=v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();

        }
        aMap.setTrafficEnabled(true);
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //
        aMap.setMyLocationStyle(myLocationStyle);
        myLocationStyle.showMyLocation(true);
        aMap.setTrafficEnabled(false);
        //remove logo
        UiSettings uiSettings=aMap.getUiSettings();
        uiSettings.setLogoPosition(-550);
        return v;
    }



}
