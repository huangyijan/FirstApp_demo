package com.example.yijian.firstproject_demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class New extends Fragment {


    private TextView textId;
    private TextView titleId;

    public New() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_new, container, false);
        textId = ((TextView) v.findViewById(R.id.backText));
        titleId = ((TextView) v.findViewById(R.id.mainTitle));
        titleId.setText("The Verge");
        textId.setText("News");
        return v;
    }

}
