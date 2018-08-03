package com.example.yijian.firstproject_demo;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;


public class Remind extends Fragment implements View.OnClickListener {


    private EditText name;
    private FrameLayout remind_button;
    private ImageView circle;
    private ImageView circle_rect;
    private ImageView circle1;
    private Boolean clickStatus = true;

    public Remind() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_remind, container, false);
        name = ((EditText) v.findViewById(R.id.edit_name));
        name.setHintTextColor(Color.parseColor("#B6ffffff"));
        remind_button = ((FrameLayout) v.findViewById(R.id.remind_button));
        circle = ((ImageView) v.findViewById(R.id.circle));
        circle1 = ((ImageView) v.findViewById(R.id.circle1));
        circle_rect = ((ImageView) v.findViewById(R.id.circle_rect));
        remind_button.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (clickStatus) {

            circle_rect.setImageResource(R.drawable.handle_on);
            circle.setVisibility(View.VISIBLE);
            circle1.setVisibility(View.GONE);
            clickStatus = false;
        } else {
            circle_rect.setImageResource(R.drawable.out_rect);
            circle1.setVisibility(View.VISIBLE);
            circle.setVisibility(View.GONE);
            clickStatus = true;

        }
    }
}
