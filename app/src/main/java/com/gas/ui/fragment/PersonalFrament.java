package com.gas.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gas.conf.Common;
import com.gas.entity.User;
import com.gas.epiboly.R;
import com.gas.ui.common.BaseFragment;

/**
 * Created by Heart on 2015/7/22.
 */
public class PersonalFrament extends BaseFragment implements View.OnClickListener {


    private User user =  Common.getInstance().user;
    private Activity mActivity;
    private View rootView;
    private LinearLayout ly_text_personal;
    private LinearLayout ly_edit_personal;
    private TextView user_name;
    private TextView user_phone;
    private TextView user_address;  //地址
    private TextView user_sex;      //性别
    private TextView user_alternate_phone;  //备用电话
    private TextView user_service;   //服务项目
    private EditText edit_user_address;
    private EditText user_edit_alternate_phone;
    private RadioGroup sex_radioGrop;
    private CheckBox checkbox_deliver_gas;
    private CheckBox checkbox_repair;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, container,
                false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = this.getActivity();
        rootView = getView();
        init();
        initListener();
    }

    public void init() {
        ly_text_personal = (LinearLayout) rootView.findViewById(R.id.ly_text_personal);
        ly_edit_personal = (LinearLayout) rootView.findViewById(R.id.ly_edit_personal);
        user_name = (TextView) rootView.findViewById(R.id.user_name);
        user_phone = (TextView) rootView.findViewById(R.id.user_phone);
        user_address = (TextView) rootView.findViewById(R.id.user_address);
        user_sex = (TextView) rootView.findViewById(R.id.user_sex);
        user_alternate_phone = (TextView) rootView.findViewById(R.id.user_alternate_phone);
        user_service = (TextView) rootView.findViewById(R.id.user_service);
        edit_user_address = (EditText) rootView.findViewById(R.id.edit_user_address);
        user_edit_alternate_phone = (EditText) rootView.findViewById(R.id.user_edit_alternate_phone);
        sex_radioGrop = (RadioGroup) rootView.findViewById(R.id.sex_radioGrop);
        checkbox_deliver_gas = (CheckBox) rootView.findViewById(R.id.checkbox_deliver_gas);
        checkbox_repair = (CheckBox) rootView.findViewById(R.id.checkbox_repair);

        user_name.setText(user.getName());
        user_phone.setText(user.getPhone());
    }

    public void initListener() {
    }

    @Override
    public void onClick(View v) {

    }
}
