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
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gas.conf.Common;
import com.gas.connector.HttpCallBack;
import com.gas.connector.protocol.BusinessHttpProtocol;
import com.gas.entity.OrderStatisticsBean;
import com.gas.entity.User;
import com.gas.epiboly.R;
import com.gas.ui.common.SuperActivity;
import com.gas.utils.TimeFormat;
import com.gas.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Administrator on 2015/9/11.
 * 订单统计
 */
public class OrderStatistics extends SuperActivity implements HttpCallBack, View.OnClickListener {


    private User user = Common.getInstance().user;
    private PopupWindow showWindow;
    private View rootView;
    private TextView start_date;
    private TextView end_date;

    private TextView order_count;
    private TextView count3;
    private TextView count2;
    private TextView count1;
    private TextView total_cost;
    private TextView total_h_cost;
    private TextView total_wx_cost;
    private TextView date_statistics;
    private Button button_statistics;
    private DatePicker datePicker;
    int year, month, day;
    private OrderStatisticsBean orderStatisticsBean;
    private Gson gson;
    private TextView selectTextView;
    private View loading_progress_layout;

    public static void launchActivity(Activity fromActivity) {
        Intent i = new Intent(fromActivity, OrderStatistics.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        fromActivity.startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_order_statistics, null);
        setContentView(R.layout.activity_order_statistics);

        init();
        initListener();
    }


    public void init() {
        findViewById(R.id.title_home).setVisibility(View.GONE);
        gson = new Gson();
        start_date = (TextView) findViewById(R.id.start_date);
        end_date = (TextView) findViewById(R.id.end_date);
        button_statistics = (Button) findViewById(R.id.button_statistics);
        date_statistics = (TextView) findViewById(R.id.date_statistics);
        order_count = (TextView) findViewById(R.id.order_count);
        count3 = (TextView) findViewById(R.id.count3);
        count2 = (TextView) findViewById(R.id.count2);
        count1 = (TextView) findViewById(R.id.count1);
        total_cost = (TextView) findViewById(R.id.total_cost);
        total_h_cost = (TextView) findViewById(R.id.total_h_cost);
        total_wx_cost = (TextView) findViewById(R.id.total_wx_cost);
        loading_progress_layout = findViewById(R.id.loading_progress_layout);

        start_date.setText(TimeFormat.getToday());
        end_date.setText(TimeFormat.getToday());
        date_statistics.setText(start_date.getText().toString() + "~" + end_date.getText().toString());
    }

    public void initListener() {
        start_date.setOnClickListener(this);
        end_date.setOnClickListener(this);
        button_statistics.setOnClickListener(this);
        findViewById(R.id.title_back).setOnClickListener(this);
    }

    public void loadData(){
        order_count.setText(orderStatisticsBean.getCountOrder());
        count3.setText(orderStatisticsBean.getCount3());
        count2.setText(orderStatisticsBean.getCount2());
        count1.setText(orderStatisticsBean.getCount1());
        total_cost.setText("¥  "+orderStatisticsBean.getTotal_cost()+(orderStatisticsBean.getTotal_cost().equals("0")?"":"元"));
        total_h_cost.setText("¥  "+orderStatisticsBean.getTotal_h_cost()+(orderStatisticsBean.getTotal_h_cost().equals("0")?"":"元"));
        total_wx_cost.setText("¥  "+orderStatisticsBean.getTotal_wx_cost()+(orderStatisticsBean.getTotal_wx_cost().equals("0")?"":"元"));
    }
    @Override
    public void onGeneralSuccess(String result, long flag) {
        hidenLoading();
        try {
            Utils.toastMsg(this, "查询完毕");
            JSONObject jsonObject = new JSONObject(result);
            orderStatisticsBean = gson.fromJson(jsonObject.optString("apk_count"),OrderStatisticsBean.class);
            loadData();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onGeneralError(String e, long flag) {
        hidenLoading();
        Utils.toastMsg(this, e);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.start_date:
                selectTextView = start_date;
                showWindow();
                break;
            case R.id.button_statistics:

                long startTime = TimeFormat.convertTimeString2Long(start_date.getText().toString(), Calendar.DATE)/1000;
                long endTime = TimeFormat.convertTimeString2Long(end_date.getText().toString(), Calendar.DATE)/1000;
                if(startTime > endTime){
                    Utils.toastMsg(this,"开始时间必须小于结束时间");
                    return;
                }

                showLoading();
                BusinessHttpProtocol.orderStatistics(this,user.getId(),startTime,endTime);
                date_statistics.setText(start_date.getText().toString() + "~" + end_date.getText().toString());
                break;
            case R.id.end_date:
                selectTextView = end_date;
                showWindow();
                break;

        }
    }

    private void showWindow() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);

        String dateTemp[] = selectTextView.getText().toString().split("-");
        year = Integer.parseInt(dateTemp[0]);
        month = Integer.parseInt(dateTemp[1]) - 1;
        day = Integer.parseInt(dateTemp[2]);

        if (datePicker == null) {
            datePicker = new DatePicker(this);
            datePicker.setBackgroundColor(getResColor(R.color.white));
            datePicker.setCalendarViewShown(false);
            datePicker.init(year, month, day, onDateChangedListener);
        }

        datePicker.updateDate(year, month, day);

        if (showWindow == null) {
            showWindow = new PopupWindow(this);
        }
        showWindow.setContentView(datePicker);
        showWindow.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        showWindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);

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

    DatePicker.OnDateChangedListener onDateChangedListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            selectTextView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    };

    public void showLoading() {
        loading_progress_layout.setVisibility(View.VISIBLE);
    }

    public void hidenLoading() {
        loading_progress_layout.setVisibility(View.GONE);
    }
}
