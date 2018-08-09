package com.example.yijian.firstproject_demo;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * 创建时间： 2018/8/8
 * 创建人：yijian
 * 功能描述：新建个人工具类
 */

public class global {
    public static void dump(FragmentActivity fragmentActivity, Fragment f) {
        Fragment fragment=f;
        FragmentManager framanager;
        android.support.v4.app.FragmentTransaction fragmentTransaction;
        framanager = fragmentActivity.getSupportFragmentManager();
        fragmentTransaction = framanager.beginTransaction();
        fragmentTransaction.replace(R.id.linear, fragment);
        fragmentTransaction.commit();
    }
}
