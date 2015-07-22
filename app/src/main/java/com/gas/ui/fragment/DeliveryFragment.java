package com.gas.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gas.epiboly.R;
import com.gas.ui.common.BaseFragment;

/**
 * Created by Heart on 2015/7/22.
 */
public class DeliveryFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_delivery_order,container,
                false);
    }
}
