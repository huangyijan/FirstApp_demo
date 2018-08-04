package com.example.yijian.firstproject_demo;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class Remind extends Fragment implements View.OnClickListener {

    //实例化
    private EditText name;
    private FrameLayout remind_button;
    private ImageView circle;
    private ImageView circle_rect;
    private ImageView circle1;
    private Boolean clickStatus = true;
    private Calendar cal;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int mininus;
    private DatePickerDialog datePickerDialog;
    private TextView time;
    private TimePickerDialog timePickerDialog;
    private TextView time1;
    private TextView frequency1;
    private String[] frequenry = new String[]{"Monthly", "weekly", "daily", "yearly"};
    private String[] End_Alarm = new String[]{"Monthly", "weekly", "daily", "yearly"};
    private LinearLayout end_repeat;
    private TextView end_text;

    public Remind() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_remind, container, false);
        //设置editText的预留文字的颜色
        name = ((EditText) v.findViewById(R.id.edit_name));
        name.setHintTextColor(Color.parseColor("#B6ffffff"));
//        赋值
        remind_button = ((FrameLayout) v.findViewById(R.id.remind_button));
        circle = ((ImageView) v.findViewById(R.id.circle));
        circle1 = ((ImageView) v.findViewById(R.id.circle1));
        circle_rect = ((ImageView) v.findViewById(R.id.circle_rect));
        time = ((TextView) v.findViewById(R.id.time));
        time1 = (TextView) v.findViewById(R.id.time1);
        frequency1 = ((TextView) v.findViewById(R.id.frequency));
        end_repeat = ((LinearLayout) v.findViewById(R.id.end_repeat));
        end_text = ((TextView) v.findViewById(R.id.end_text));
        end_repeat.setOnClickListener(this);
        frequency1.setOnClickListener(this);
        time1.setOnClickListener(this);
        time.setOnClickListener(this);
        remind_button.setOnClickListener(this);


        //获取日历对象
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        mininus = cal.get(Calendar.MINUTE);

        time.setText(year + "/" + (month + 1) + "/" + day);
        time1.setText(hour + ":" + hour);
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                time.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, year, month, day);
        timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time1.setText(hourOfDay + ":" + minute);
            }
        }, hour, mininus, true);
        return v;
    }

    @Override
    public void onClick(View v) {
        System.out.println(v.getId());
        switch (v.getId()) {
            case R.id.remind_button:
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
                break;
            case R.id.time:
                datePickerDialog.show();
                break;
            case R.id.time1:
                timePickerDialog.show();
                break;
            case R.id.frequency:
                showDialog();
                break;
            case R.id.end_repeat:
                showEndDialog();
                break;
        }

    }

    /*
    * @date: 2018/8/4
    * @author: yijian
    * @description: 展示什么时候结束提醒
    * @version:
    */
    private void showEndDialog() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setSingleChoiceItems(End_Alarm, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                end_text.setText(End_Alarm[which]);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /*
    * @date: 2018/8/3
    * @author: yijian
    * @description：弹出选择器
    * @version:
    */
    private void showDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(getContext());// 自定义对话框
        builder3.setSingleChoiceItems(frequenry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                frequency1.setText(frequenry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }
}
