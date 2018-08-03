package com.example.yijian.firstproject_demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Stocks extends Fragment {


    private TextView stocks;

    public Stocks() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_stocks, container, false);
        stocks = ((TextView) v.findViewById(R.id.mainTitle));
        stocks.setText("Stocks");
        return v;
    }

}
