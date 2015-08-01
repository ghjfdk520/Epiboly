package com.gas.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gas.conf.Common;
import com.gas.connector.HttpCallBack;
import com.gas.connector.protocol.BusinessHttpProtocol;
import com.gas.data.SharedPreferenceUtil;
import com.gas.entity.User;
import com.gas.epiboly.R;
import com.gas.ui.calendar.DatePickerController;
import com.gas.ui.calendar.DayPickerView;
import com.gas.ui.calendar.SimpleMonthAdapter;
import com.gas.ui.calendar.SimpleMonthView;
import com.gas.ui.common.BaseFragment;
import com.gas.utils.Utils;

/**
 * Created by Heart on 2015/7/21.
 */
public class AttendanceFragment extends BaseFragment implements HttpCallBack,DatePickerController,View.OnClickListener {
    protected View rootView;
    private DayPickerView calendarView;
    private ImageView imageview_work_bt;
    private ImageView imageview_off_bt;
    private ImageView bt_check_all;
    private User u  = Common.getInstance().user;
    private String temp[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_attendance,container,
                false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rootView = getView();
        calendarView = (DayPickerView) rootView.findViewById(R.id.calendar_view);
        calendarView.setController(this);
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
    public int getMaxYear() {
        return 0;
    }
    @Override
    public void onDayOfMonthSelected(int year, int month, int day,SimpleMonthView simpleMonthView) {
        Utils.log("czd", year + " " + month + " " + day + "  " + ((float[]) simpleMonthView.getTag())[0] + " " + ((float[]) simpleMonthView.getTag())[1]);
    }
    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.imageview_off_bt:
               BusinessHttpProtocol.ClockIn(this,u.getId(),temp[0],temp[1],temp[2],String.format("%d",System.currentTimeMillis() / 1000+3*24*60*60),2);
               break;
           case R.id.imageview_work_bt:
               BusinessHttpProtocol.ClockIn(this,u.getId(),temp[0],temp[1],temp[2],String.format("%d",System.currentTimeMillis() / 1000+3*24*60*60),1);
               break;
           case R.id.bt_check_all:
               BusinessHttpProtocol.checkingInfo(this,u.getId());
               break;
       }
    }

    @Override
    public void onGeneralSuccess(String result, long flag) {
        Utils.log(" flag result",Utils.decodeUnicode(result));
    }

    @Override
    public void onGeneralError(String e, long flag) {
        Utils.log(" flag result",Utils.decodeUnicode(e));
    }
}
