package com.gas.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gas.adapter.CommonAdapter;
import com.gas.adapter.ViewHolder;
import com.gas.connector.HttpCallBack;
import com.gas.connector.protocol.BusinessHttpProtocol;
import com.gas.entity.ClientBottleListBean;
import com.gas.epiboly.R;
import com.gas.ui.common.SuperActivity;
import com.gas.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orange on 2017/11/18.
 */

public class clientBottleManager extends SuperActivity implements HttpCallBack, View.OnClickListener {

    private long CLIENT_BOTTLE_FLAG = -1;
    private long SAVE_CLIENT_BOTTLE_FLAG = -1;
    private ClientBottleListBean clientBottleListBean;
    private String gas_card;
    private Button buttom_client_bottle;
    private Gson gson;
    private View loading_progress_layout;

    private TextView tvBorrow;
    private TextView tvDetain;
    private TextView tvRepay;

    private ListView clientBottleBorrowList;
    private ListView clientBottleDetainList;
    private ListView clientBottleRepayList;

    private CommonAdapter borrowAdapter;
    private CommonAdapter detainAdapter;
    private CommonAdapter repayAdapter;

    private List<ClientBottleListBean.BottleState> borrowList = new ArrayList();
    private List<ClientBottleListBean.BottleState> detainList = new ArrayList();
    private List<ClientBottleListBean.BottleState> repayList = new ArrayList();

    public static void launchActivity(Activity mContext, String gas_card) {
        Intent intent = new Intent();
        intent.setClass(mContext, clientBottleManager.class);
        intent.putExtra("gas_card", gas_card);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientbottlemanager);
        init();
        initListener();
    }

    public void init() {
        gson = new Gson();
        loading_progress_layout = findViewById(R.id.loading_progress_layout);
        showLoading();
        gas_card = getIntent().getExtras().getString("gas_card");
        findViewById(R.id.title_home).setVisibility(View.GONE);

        buttom_client_bottle = (Button) findViewById(R.id.button_save);

        CLIENT_BOTTLE_FLAG = BusinessHttpProtocol.getClientBottle(this, gas_card);

        tvBorrow = (TextView) findViewById(R.id.tv_borrow);
        tvDetain = (TextView) findViewById(R.id.tv_detain);
        tvRepay = (TextView) findViewById(R.id.tv_repay);

        clientBottleBorrowList = (ListView) findViewById(R.id.list_client_bottle_borrow);
        clientBottleDetainList = (ListView) findViewById(R.id.list_client_bottle_detain);
        clientBottleRepayList = (ListView) findViewById(R.id.list_client_bottle_repay);


        borrowAdapter = createAdapter(borrowList);
        detainAdapter = createAdapter(detainList);
        repayAdapter  = createAdapter(repayList);

        clientBottleBorrowList.setAdapter(borrowAdapter);
        clientBottleDetainList.setAdapter(detainAdapter);
        clientBottleRepayList.setAdapter(repayAdapter);

    }

    public void initListener() {
        buttom_client_bottle.setOnClickListener(this);
        findViewById(R.id.title_back).setOnClickListener(this);

    }

    public CommonAdapter createAdapter(List list) {

        return new CommonAdapter<ClientBottleListBean.BottleState>(this, list, R.layout.item_clientbottle_list) {
            @Override
            public void convert(ViewHolder helper, ClientBottleListBean.BottleState item) {
                helper.setText(R.id.name, item.name);
                helper.setText(R.id.num, item.num + "");
                addNum(helper, item);
                subtract(helper, item);
            }
        };
    }

    public void addNum(final ViewHolder helper, final ClientBottleListBean.BottleState item) {
        helper.getView(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.num++;
                helper.setText(R.id.num, item.num + "");
            }
        });
    }

    public void subtract(final ViewHolder helper, final ClientBottleListBean.BottleState item) {
        helper.getView(R.id.button_subtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.num == 0) return;
                item.num--;
                helper.setText(R.id.num, item.num + "");
            }
        });
    }

    @Override
    public void onGeneralSuccess(String result, long flag) {
        hidenLoading();
        if (flag == CLIENT_BOTTLE_FLAG) {
            clientBottleListBean = gson.fromJson(result, ClientBottleListBean.class);
            borrowList.addAll(clientBottleListBean.list.borrow);
            detainList.addAll(clientBottleListBean.list.detain);
            repayList.addAll(clientBottleListBean.list.repay);
            borrowAdapter.notifyDataSetChanged();
            repayAdapter.notifyDataSetChanged();
            detainAdapter.notifyDataSetChanged();

            Utils.showAllListChildren(clientBottleBorrowList);
            Utils.showAllListChildren(clientBottleDetainList);
            Utils.showAllListChildren(clientBottleRepayList);

        } else if(flag == SAVE_CLIENT_BOTTLE_FLAG){
            Utils.toastMsg(this, "保存成功");
        }
    }

    @Override
    public void onGeneralError(String e, long flag) {
        hidenLoading();
        Utils.toastMsg(this, e);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_save:
                showLoading();
//                Log.d("gson", gson.toJson(clientBottleListBean.list));
                SAVE_CLIENT_BOTTLE_FLAG = BusinessHttpProtocol.saveClientBottle(this, gson.toJson(clientBottleListBean.list));
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    public void showLoading() {
        loading_progress_layout.setVisibility(View.VISIBLE);
    }

    public void hidenLoading() {
        loading_progress_layout.setVisibility(View.GONE);
    }

}
