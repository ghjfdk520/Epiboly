package com.gas.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gas.epiboly.R;
import com.gas.ui.common.SuperActivity;

/**
 * Created by Heart on 2015/8/23.
 * 油耗记录
 */
public class OilActivity extends SuperActivity implements View.OnClickListener {
    private Button log_add;

    private ListView logList;
    private PopupWindow showWindow;

    private View rootView;

    private TextView car_no;
    private EditText capactiy;
    private EditText spend;
    private EditText month;
    private EditText year;
    private EditText day;

    private Button submit;
    private Button cancle;

    public static void launchActivity(Activity fromActivity) {
        Intent i = new Intent(fromActivity, OilActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        fromActivity.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_car_oil, null);
        setContentView(rootView);
        init();
    }

    public void init() {
        findViewById(R.id.add_record).setOnClickListener(this);
    }

    @Override
    public void onGeneralError(String e, long flag) {

    }

    @Override
    public void onGeneralSuccess(String result, long flag) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:
                AddoilActivity.launchActivity(this);
               // showWindow();
                break;

        }
    }



    private void showWindow() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        View showView = LayoutInflater.from(this).inflate(
                R.layout.dialog_car_oil_add, null);

        if (showWindow == null) {
            showWindow = new PopupWindow(this);
        }

        loadDate(showView);
        showWindow.setContentView(showView);
        showWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        showWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);


        showWindow.setFocusable(true);
        showWindow.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        showWindow.setBackgroundDrawable(dw);
        showWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        showWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
    }


    public void loadDate(View parent) {
        car_no = (TextView) parent.findViewById(R.id.car_no);
        capactiy = (EditText) parent.findViewById(R.id.capactiy);
        spend = (EditText) parent.findViewById(R.id.spend);
        month = (EditText) parent.findViewById(R.id.month);
        year = (EditText) parent.findViewById(R.id.year);
        day = (EditText) parent.findViewById(R.id.day);
        submit = (Button) parent.findViewById(R.id.submit);
        cancle = (Button) parent.findViewById(R.id.cancle);
        submit.setOnClickListener(this);
        cancle.setOnClickListener(this);
        car_no.setOnClickListener(this);
    }
}
