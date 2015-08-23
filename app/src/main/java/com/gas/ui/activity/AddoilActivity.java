package com.gas.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gas.adapter.CommonAdapter;
import com.gas.adapter.ViewHolder;
import com.gas.entity.CarList;
import com.gas.epiboly.HomeActivity;
import com.gas.epiboly.R;
import com.gas.ui.common.SuperActivity;
import com.gas.utils.TimeFormat;
import com.gas.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Heart on 2015/8/23.
 * 添加油耗记录
 */
public class AddoilActivity extends SuperActivity implements View.OnClickListener {
    private CommonAdapter<CarList.Car> carListAdapter;
    private List<CarList.Car> list;
    private PopupWindow showCarWindow;
    private TextView car_no;
    private EditText capactiy;
    private EditText spend;
    private EditText month;
    private EditText year;
    private EditText day;
    private TextView operator;
    private View loading_progress_layout;
    private Button submit;
    private Button cancle;

    private String today;

    public static void launchActivity(Activity fromActivity) {
        Intent i = new Intent(fromActivity, AddoilActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        fromActivity.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_car_oil_add);
        init();
        initListener();
    }

    public void init() {
        list = new ArrayList<>();
        loading_progress_layout = findViewById(R.id.loading_progress_layout);
        car_no = (TextView) findViewById(R.id.car_no);
        capactiy = (EditText) findViewById(R.id.capactiy);
        spend = (EditText) findViewById(R.id.spend);
        month = (EditText) findViewById(R.id.month);
        year = (EditText) findViewById(R.id.year);
        day = (EditText) findViewById(R.id.day);
        submit = (Button) findViewById(R.id.submit);
        cancle = (Button) findViewById(R.id.cancle);
        today = TimeFormat.getToday();
        operator = (TextView) findViewById(R.id.car_no);

        year.setText(today.split("-")[0]);
        year.setText(today.split("-")[1]);
        year.setText(today.split("-")[2]);
    }

    public void initListener() {
        submit.setOnClickListener(this);
        cancle.setOnClickListener(this);
        car_no.setOnClickListener(this);
        findViewById(R.id.title_back).setOnClickListener(this);
        findViewById(R.id.cancle).setOnClickListener(this);
        findViewById(R.id.title_home).setOnClickListener(this);
        findViewById(R.id.scan_code).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
            case R.id.title_back:
                finish();
                break;
            case R.id.title_home:
                HomeActivity.launchActivity(this);
                finish();
                break;
            case R.id.car_no:
                Utils.log("showCAR", "okk");
                showCarWindow();
                break;
            case R.id.submit:
                showLoading();
                break;
        }
    }

    @Override
    public void onGeneralSuccess(String result, long flag) {

    }

    @Override
    public void onGeneralError(String e, long flag) {

    }


    private void showCarWindow() {
        View showView = LayoutInflater.from(this).inflate(
                R.layout.dialog_list, null);
        ListView list_view = (ListView) showView.findViewById(R.id.list_view);


        if (showCarWindow == null) {
            showCarWindow = new PopupWindow(this);
        }


        carListAdapter = new CommonAdapter<CarList.Car>(this, list, R.layout.item_text) {
            @Override
            public void convert(ViewHolder helper, final CarList.Car item) {
                helper.setText(R.id.content, item.car_no);
                // helper.getView(R.id.ly_item).setBackgroundColor(getResColor(R.color.car_item_bg));
                helper.getView(R.id.ly_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        currentCar = item;
//                        tv_license_number.setText(currentCar.car_no);
                    }
                });
            }
        };
        int showHeight = 0;
        if (list.size() > 4) {
            View listItem = carListAdapter.getView(0, null, list_view);
            listItem.measure(0, 0);
            showHeight = listItem.getMeasuredHeight() * 4;
        } else {
            View listItem = carListAdapter.getView(0, null, list_view);
            listItem.measure(0, 0);
            showHeight = listItem.getMeasuredHeight() * list.size();
        }

        list_view.setAdapter(carListAdapter);
        showCarWindow.setContentView(showView);
        showCarWindow.setWidth(car_no.getWidth());
        showCarWindow.setHeight(showHeight);

        showCarWindow.setFocusable(true);
        showCarWindow.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        showCarWindow.setBackgroundDrawable(dw);
        showCarWindow.showAsDropDown(car_no);

        showCarWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    public void showLoading() {
        loading_progress_layout.setVisibility(View.VISIBLE);
    }

    public void hidenLoading() {
        loading_progress_layout.setVisibility(View.GONE);
    }

}
