package com.gas.ui.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.gas.conf.Common;
import com.gas.connector.HttpCallBack;
import com.gas.connector.protocol.BusinessHttpProtocol;
import com.gas.database.SharedPreferenceUtil;
import com.gas.entity.User;
import com.gas.epiboly.R;
import com.gas.ui.common.BaseFragment;
import com.gas.utils.Utils;

/**
 * Created by Heart on 2015/7/21.
 */
public class AttendanceFragment extends BaseFragment implements HttpCallBack,View.OnClickListener {
    protected View rootView;
    private ImageView imageview_work_bt;
    private ImageView imageview_off_bt;
    private ImageView bt_check_all;
    private User u  = Common.getInstance().user;
    private String temp[];
    private PopupWindow showWindow;
    private long workFlag;
    private long offFlag;
    private long checkallFlag;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_attendance,container,
                false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rootView = getView();

        init();
        initListener();
    }


    public void init(){
        temp = SharedPreferenceUtil.getInstance(getActivity()).getString(SharedPreferenceUtil.LONGITUDE).split("/");
        imageview_work_bt = (ImageView) rootView.findViewById(R.id.imageview_work_bt);
        imageview_off_bt = (ImageView) rootView.findViewById(R.id.imageview_off_bt);
        bt_check_all = (ImageView) rootView.findViewById(R.id.bt_check_all);
    }

    public void initListener(){
        imageview_work_bt.setOnClickListener(this);
        imageview_off_bt.setOnClickListener(this);
        bt_check_all.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.imageview_off_bt:
               offFlag = BusinessHttpProtocol.ClockIn(this,u.getId()+"",temp[0],temp[1],temp[2],String.format("%d",System.currentTimeMillis() / 1000+3*24*60*60),2);
               break;
           case R.id.imageview_work_bt:
               workFlag =  BusinessHttpProtocol.ClockIn(this,u.getId()+"",temp[0],temp[1],temp[2],String.format("%d",System.currentTimeMillis() / 1000+3*24*60*60),1);
               break;
           case R.id.bt_check_all:
               checkallFlag =  BusinessHttpProtocol.checkingInfo(this,u.getId()+"");
               break;
       }
    }

    @Override
    public void onGeneralSuccess(String result, long flag) {
        try {
            Utils.log(" flag result",Utils.decodeUnicode(result));
            if(workFlag == flag){
                showWindow(0);
            }else if(offFlag == flag){
                showWindow(1);
            }else if(checkallFlag == flag){

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onGeneralError(String e, long flag) {
        Utils.log(" flag result eeor",Utils.decodeUnicode(e));
        Utils.toastMsg(getActivity(), Utils.decodeUnicode(e));
        showWindow(1);
    }

    private void showWindow(int showPotision){
        WindowManager.LayoutParams params=getActivity().getWindow().getAttributes();
        params.alpha=0.7f;
        getActivity().getWindow().setAttributes(params);
        if (showWindow == null) {
            showWindow = new PopupWindow();
        }
        ImageView img = new ImageView(getActivity());


        if(showPotision ==0){
            img.setImageResource(R.mipmap.attendance_popup_work);
            showWindow.setContentView(img);
        }else {
            img.setImageResource(R.mipmap.attendance_popup_work);
            showWindow.setContentView(img);
        }
        showWindow.setContentView(img);
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
                WindowManager.LayoutParams params=getActivity().getWindow().getAttributes();
                params.alpha=1.0f;
                getActivity().getWindow().setAttributes(params);
            }
        });
    }
}
