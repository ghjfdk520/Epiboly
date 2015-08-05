package com.gas.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gas.adapter.CommonAdapter;
import com.gas.adapter.ViewHolder;
import com.gas.epiboly.R;
import com.gas.ui.common.BaseFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Heart on 2015/7/22.
 */
public class DeliveryFragment extends BaseFragment {
    private PullToRefreshListView mPullRefreshListView;
    private CommonAdapter<String> deliveryAdapter;
    protected View rootView;
    private Activity mActivity;
    private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler" };
    private List<String> datas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delivery_order, container,
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
        datas = new ArrayList<>();
        for (String data : mStrings) {
            datas.add(data);
        }
        mPullRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_list);
        deliveryAdapter = new CommonAdapter<String>(mActivity,datas,R.layout.item_delivery_order_history) {
            @Override
            public void convert(ViewHolder helper, String item) {

            }
        };
        ListView actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.setAdapter(deliveryAdapter);
    }

    public void initListener() {
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

}
