package com.gas.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gas.epiboly.R;
import com.gas.ui.calendar.DatePickerController;
import com.gas.ui.calendar.DayPickerView;
import com.gas.ui.calendar.SimpleMonthAdapter;
import com.gas.ui.common.BaseFragment;

/**
 * Created by Heart on 2015/7/21.
 */
public class AttendanceFragment extends BaseFragment implements DatePickerController {
    protected View rootView;
    private DayPickerView calendarView;
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
    }

    @Override
    public int getMaxYear() {
        return 0;
    }
    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        Log.d("czd",year+" "+month+" "+day);
    }
    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
    }
}
