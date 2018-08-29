package com.example.yijian.firstproject_demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends FragmentActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private AMapLocationListener myLocationListener;
    private String TAG = "intro";
    private String wendu=null;
    //当前城市
    private String City = null;
    //声明AMapLocationClient对象
    private AMapLocationClient myLocationclient = null;
    private main_Page main_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置状态栏为半透明
//        Activity ac=MainActivity.this;
//        Window window = ac.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //设置状态栏为透明
        test();
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            main_page = new main_Page();

        }
        startActivity(new Intent(MainActivity.this,time_machine.class));
        locationCity();

    }

    private void test() {
        // 要申请的权限 数组 可以同时申请多个权限
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};

        if (Build.VERSION.SDK_INT >= 23) {
            //如果超过6.0才需要动态权限，否则不需要动态权限
            //如果同时申请多个权限，可以for循环遍历
            int check = ContextCompat.checkSelfPermission(this,permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check == PackageManager.PERMISSION_GRANTED) {
                //写入你需要权限才能使用的方法
                run();
            } else {
                //手动去请求用户打开权限(可以在数组中添加多个权限) 1 为请求码 一般设置为final静态变量
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            //写入你需要权限才能使用的方法
            run();
        }
    }

    private void run() {
        Log.i(TAG, "run: 拿到了定位了");
    }


    public  String transmitTheCity(){
        return City;
    }

    public void locationCity(){
        //声明监听器
        myLocationListener = new AMapLocationListener() {


            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                //判断是否为空
                if (null != aMapLocation) {
                    //判断状态码，为0的时候说明获取定位成功
                    if (aMapLocation.getErrorCode() == 0) {
                        Log.i(TAG, "onLocationChanged: " + aMapLocation.toString());
                        City = aMapLocation.getCity();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.linear,main_page)
                                .commit();
                        main_page.changetitle(City);
                        main_page.initDisplay();
                        myLocationclient.stopLocation();
                    } else {
                        Log.e(TAG, "onLocationChanged: " + aMapLocation.getErrorCode() + "/" + aMapLocation.getErrorInfo());
                    }
                }

            }
        };
        //初始化对象
        myLocationclient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        myLocationclient.setLocationListener(myLocationListener);
        //声明AMapLocationClientOption
        AMapLocationClientOption myClientOption = null;
        //c初始化
        myClientOption = new AMapLocationClientOption();
        //设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
        myClientOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        //设置定位模式,这里跟着官方的方法采用高精度模式：会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
        //低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；
        //仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，自 v2.9.0 版本支持返回地址描述信息。
        myClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //单次定位
//        myClientOption.setOnceLocation(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        myClientOption.setInterval(1000);
        //获取最近三秒里面的最精准的一次定位
//        myClientOption.setOnceLocationLatest(true);
        //设置是否缓存，默认不缓存
        myClientOption.setLocationCacheEnable(false);
        //获取返回信息的地址描述与否
        myClientOption.setNeedAddress(true);
        //设置myLocationclient设置参数
        myLocationclient.setLocationOption(myClientOption);
        //官方建议先停下一次定位以确保sttartLocation正常运行
        myLocationclient.stopLocation();
        myLocationclient.startLocation();
    }
}
