package com.gas.epiboly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gas.conf.Common;
import com.gas.database.UserWorker;
import com.gas.entity.User;
import com.gas.ui.common.SuperActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Heart on 2015/8/17.
 */
public class HomeActivity extends SuperActivity implements View.OnClickListener{
    private User user = Common.getInstance().user;
    private ImageButton home_person;
    private ImageButton home_repair;
    private ImageButton home_vehicles;
    private ImageButton home_bottle;
    private ImageButton home_delivery;
    private ImageButton home_attendance;
    private ImageButton home_empty;
    private ImageButton home_logout;
    private PopupWindow showWindow;
    private View rootView;
    public static void launchActivity(Activity fromActivity) {
        Intent i = new Intent(fromActivity, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        fromActivity.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView  = LayoutInflater.from(this).inflate(R.layout.activity_home,null);
        setContentView(R.layout.activity_home);
        init();
        initListener();
    }

    public void init(){

    }

    public void initListener(){
        findViewById(R.id.home_person).setOnClickListener(this);
        findViewById(R.id.home_repair).setOnClickListener(this);
        findViewById(R.id.home_vehicles).setOnClickListener(this);
        findViewById(R.id.home_bottle).setOnClickListener(this);
        findViewById(R.id.home_delivery).setOnClickListener(this);
        findViewById(R.id.home_attendance).setOnClickListener(this);
        findViewById(R.id.home_empty).setOnClickListener(this);
        findViewById(R.id.home_logout).setOnClickListener(this);

    }
    @Override
    public void onGeneralError(String e, long flag) {

    }

    @Override
    public void onGeneralSuccess(String result, long flag) {

    }

    @Override
    protected void onDestroy() {
        if(showWindow != null)
        showWindow.dismiss();
        super.onDestroy();
    }

    public void logout(){
        JPushInterface.setAliasAndTags(getApplicationContext(), "", null, null);
        new UserWorker(this).removeAll(user.getId());
        Common.getInstance().user = null;
        StartActity.launchActivity(this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_person:
                MainActivity.lauchActivity(this, 0);break;
            case R.id.home_repair:
                MainActivity.lauchActivity(this,3);break;
          //  case R.id.home_vehicles: MainActivity.lauchActivity(this,2);break;
            case R.id.home_bottle: MainActivity.lauchActivity(this,4);break;
            case R.id.home_delivery:
                MainActivity.lauchActivity(this,2);break;
            case R.id.home_attendance: MainActivity.lauchActivity(this,1);break;
           //case R.id.home_empty: MainActivity.lauchActivity(this,2);break;
            case R.id.home_logout: showWindow();break;
            case R.id.ly_prompt:
                showWindow.dismiss();
                logout();
                break;
        }
    }



    private void showWindow() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        View showView = LayoutInflater.from(this).inflate(
                R.layout.ly_prompt_dialog, null);
        TextView titleView = (TextView) showView.findViewById(R.id.prompt_title);
        TextView contentView = (TextView) showView.findViewById(R.id.prompt_content);
        String title = "确定退出";
        String content  = "确定退出登录？";
        titleView.setText(title);
        contentView.setText(content);

        LinearLayout ly_prompt = (LinearLayout) showView.findViewById(R.id.ly_prompt);
        ly_prompt.setOnClickListener(this);

        if (showWindow == null) {
            showWindow = new PopupWindow(this);
        }

        showWindow.setContentView(showView);
        showWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        showWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

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
}