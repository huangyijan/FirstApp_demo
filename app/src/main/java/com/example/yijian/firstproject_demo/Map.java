package com.example.yijian.firstproject_demo;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;


/**
 * A simple {@link Fragment} subclass.
 */
public class Map extends SupportMapFragment implements AMap.OnMyLocationChangeListener, View.OnClickListener {

    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private TextView leftText;
    private TextView centerText;
    private TextView rightText;
    private MapView mapView;
    private double latitude;
    private double longtitude;

    public Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        //初始化地图
        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        init(v);

        return v;
    }

    /*
    * @date: 2018/8/8
    * @author: yijian
    * @description: 初始化一些布局参数
    * @version:
    */

    private void init(View v) {
        leftText = ((TextView) v.findViewById(R.id.backText));
        leftText.setText("主页");
        centerText = ((TextView) v.findViewById(R.id.mainTitle));
        centerText.setText("地图");
        rightText = ((TextView) v.findViewById(R.id.right_text));
        rightText.setText("离线地图");
        leftText.setOnClickListener(this);
        rightText.setOnClickListener(this);


        //初始化位置
        myLocationStyle = new MyLocationStyle();

        //交通情况
        aMap.setTrafficEnabled(false);
        //aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //设置定位
        aMap.setMyLocationStyle(myLocationStyle);
        try {
            latitude = ((Location) aMap.getMyLocation()).getLatitude();
            longtitude = ((Location) aMap.getMyLocation()).getLongitude();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //定位图标显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(this);
        aMap.showIndoorMap(true);
        //设置logo位置
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setLogoPosition(-550);
        //设置缩放3~19
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }


    @Override
    public void onMyLocationChange(Location location) {
//        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
//        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,18,30,30)));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_text:
                Toast.makeText(getContext(), "定位方法", Toast.LENGTH_SHORT).show();
                break;
            case R.id.backText:
                global gb = new global();
                main_Page mainPage = new main_Page();
                gb.dump(getActivity(), mainPage);
                break;
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        mapView.onSaveInstanceState(bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
