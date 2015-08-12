package com.gas.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gas.conf.Common;
import com.gas.connector.protocol.BusinessHttpProtocol;
import com.gas.entity.DeliveryOrder;
import com.gas.entity.User;
import com.gas.epiboly.R;
import com.gas.ui.common.SuperActivity;

import org.json.JSONException;

/**
 * Created by Heart on 2015/8/11.
 */
public class orderDetail extends SuperActivity {

    public User  user= Common.getInstance().user;
    public static int REQUEST_CODE;
    public static final int REQUEST_LOGIN_ACTIVITY = 0X10012;
    private Button accpet_order;
    private Button refuse_order;
    private Button finish_order;
    private static DeliveryOrder itemOrder;
    public static void launchActivity(Fragment mContext, int requestCode,DeliveryOrder item) {
        Intent intent = new Intent();
        intent.setClass(mContext.getActivity(), orderDetail.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        REQUEST_CODE = requestCode;
        ((Fragment) mContext).startActivityForResult(intent,
                REQUEST_LOGIN_ACTIVITY);
        itemOrder = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorder_detail);
        init();
        initListener();
    }

    public void init(){
        accpet_order = (Button) findViewById(R.id.accpet_order);
        refuse_order = (Button) findViewById(R.id.refuse_order);
        finish_order = (Button) findViewById(R.id.finish_order);
    }

    public void initListener(){
        accpet_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessHttpProtocol.getDeliverOrder(orderDetail.this, itemOrder.getId(), user.getId());
            }
        });
        refuse_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessHttpProtocol.rejectDeliverOrder(orderDetail.this, itemOrder.getId(), user.getId());
            }
        });
        finish_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessHttpProtocol.finishOrder(orderDetail.this, user.getId(), itemOrder.getId());
            }
        });
    }
    @Override
    public void onGeneralError(String e, long flag) {

    }

    @Override
    public void onGeneralSuccess(String result, long flag) throws JSONException {

    }
}
