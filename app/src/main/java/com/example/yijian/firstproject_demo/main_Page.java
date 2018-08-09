package com.example.yijian.firstproject_demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class main_Page extends Fragment {


    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simAdapt;
    private int[] IconName = {R.drawable.sun, R.drawable.list, R.drawable.bell, R.drawable.stocks};
    private String[] StrName = {"19°,6mps", "24 new", "12 new", "Stocks"};

    public main_Page() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("onCreateView");
        View v = inflater.inflate(R.layout.fragment_main__page, container, false);
        gridView = (GridView) v.findViewById(R.id.grid);
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        simAdapt = new SimpleAdapter(getActivity(), dataList, R.layout.item, new String[]{"image", "name"}, new int[]{R.id.imageV, R.id.text2});
        gridView.setAdapter(simAdapt);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                if (position == 0) {
//                    int b=R.drawable.border;
//                    view.setBackgroundDrawable(getContext().getResources().getDrawable(b));
//                }
//                if(position==3){
//                    int a = R.drawable.boreder_bom_top;
//                    view.setBackgroundDrawable(getContext().getResources().getDrawable(a));
//
//                }
                switch (position) {
                    case 0:
                        Weather weather=new Weather();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.linear,weather)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        New new1=new New();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.linear,new1)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 2:
                        Remind remind=new Remind();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.linear,remind)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 3:
//                        Stocks stocks=new Stocks();
                        com.example.yijian.firstproject_demo.Map map=new com.example.yijian.firstproject_demo.Map();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.linear,map)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        System.out.println("onStar");
        super.onStart();
//        gridView.setSelection(1);
//        View view = simAdapt.getView(0, gridView.getAdapter().getView(0,null,null), gridView);
//        System.out.println(gridView.getCount());
//        View view=gridView.getAdapter().getView(1,gridView,null);
//        int a = R.drawable.boreder_bom_top;
//                //首先获得上下文，然后获得资源，再得到drawable里面的资源
//        view.setBackgroundDrawable(getContext().getResources().getDrawable(a));
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

}
