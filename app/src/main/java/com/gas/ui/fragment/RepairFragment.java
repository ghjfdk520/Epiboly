package com.gas.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gas.adapter.CommonAdapter;
import com.gas.adapter.ViewHolder;
import com.gas.conf.Common;
import com.gas.connector.HttpCallBack;
import com.gas.connector.protocol.BusinessHttpProtocol;
import com.gas.database.SharedPreferenceUtil;
import com.gas.entity.DeliveryOrder;
import com.gas.entity.RepairOrder;
import com.gas.epiboly.R;
import com.gas.ui.common.BaseFragment;
import com.gas.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Heart on 2015/7/22.
 */
public class RepairFragment extends BaseFragment implements HttpCallBack {

    private SharedPreferenceUtil sharedPreferenceUtil;


    //当前显示list
    private LinkedList<RepairOrder> currentList;


    private List<RepairOrder> historyDatas = new LinkedList<>();
    private List<RepairOrder> accpetDatas = new LinkedList<>();
    private List<RepairOrder> unaccpetDatas = new LinkedList<>();

    private PullToRefreshListView accpetListView;
    private PullToRefreshListView historyListView;
    private PullToRefreshListView unaccpetListView;

    private CommonAdapter<RepairOrder> historyAdapter;
    private CommonAdapter<RepairOrder> accpetAdapter;
    private CommonAdapter<RepairOrder> unaccpetAdapter;

    private RadioGroup group_status_selector;
    protected View rootView;
    private Activity mActivity;
    private long DOWN_FLAG = 0;
    private long UP_FLAG = 0;
    private long referenceTime = 0;

    private String DOWN_STATE = "1";
    private String UP_STATE = "0";
    private int currentViewPosition = 0;  //0未接订单  1 已接订单  2 历史订单
    private Handler handler = new Handler();


    private View emptyView;

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
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(mActivity);
        init();
        initListener();

        showListView(currentViewPosition);
        referenceList(currentViewPosition);
    }


    //读取缓存
    public void getRecordToBuffer() {

        String historyJsonArray = SharedPreferenceUtil.getInstance(mActivity).getString(SharedPreferenceUtil.REPAIRORDER_HISTORY);
        String accpetJsonArray = SharedPreferenceUtil.getInstance(mActivity).getString(SharedPreferenceUtil.REPAIRORDER_ACCPET);
        String unaccpetyJsonArray = SharedPreferenceUtil.getInstance(mActivity).getString(SharedPreferenceUtil.REPAIRORDER_UNACCPET);

        List<RepairOrder> tempList = gson.fromJson(historyJsonArray, new TypeToken<List<DeliveryOrder>>() {
        }.getType());
        if (tempList != null)
            historyDatas.addAll(tempList);

        tempList = gson.fromJson(unaccpetyJsonArray, new TypeToken<List<DeliveryOrder>>() {
        }.getType());
        if (tempList != null)
            unaccpetDatas.addAll(tempList);

        tempList = gson.fromJson(accpetJsonArray, new TypeToken<List<DeliveryOrder>>() {
        }.getType());
        if (tempList != null)
            accpetDatas.addAll(tempList);

        if (currentViewPosition == 2)
            currentList = (LinkedList) historyDatas;
        else if (currentViewPosition == 1)
            currentList = (LinkedList) accpetDatas;
        else if (currentViewPosition == 0)
            currentList = (LinkedList) unaccpetDatas;
    }

    public void init() {
        getRecordToBuffer();
        emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.ly_empty_view, null);
        historyListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_history_list);
        accpetListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_accpet_list);
        unaccpetListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_un_accpet_list);

        unaccpetAdapter = new CommonAdapter<RepairOrder>(mActivity, unaccpetDatas, R.layout.item_delivery_order_history) {
            @Override
            public void convert(final ViewHolder helper, RepairOrder item) {
                helper.setText(R.id.customer_address, item.getAddress());
                helper.setText(R.id.customer_name, item.getClient_name());

            }
        };
        accpetAdapter = new CommonAdapter<RepairOrder>(mActivity, accpetDatas, R.layout.item_delivery_order_history) {
            @Override
            public void convert(final ViewHolder helper, RepairOrder item) {
                helper.setText(R.id.customer_address, item.getAddress());
                helper.setText(R.id.customer_name, item.getClient_name());

            }
        };
        historyAdapter = new CommonAdapter<RepairOrder>(mActivity, historyDatas, R.layout.item_delivery_order_history) {
            @Override
            public void convert(ViewHolder helper, RepairOrder item) {
                helper.setText(R.id.customer_address, item.getAddress());
                helper.setText(R.id.customer_name, item.getClient_name());
            }
        };
        group_status_selector = (RadioGroup) rootView.findViewById(R.id.group_status_selector);

        unaccpetListView.setAdapter(unaccpetAdapter);
        unaccpetListView.setMode(PullToRefreshBase.Mode.BOTH);
        accpetListView.setAdapter(accpetAdapter);
        accpetListView.setMode(PullToRefreshBase.Mode.BOTH);
        historyListView.setAdapter(historyAdapter);
        historyListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    public void initListener() {
        unaccpetListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Utils.log("down", "yeah");
                referenceTime = System.currentTimeMillis() / 1000;
                DOWN_FLAG = BusinessHttpProtocol.newDeliverOrder(RepairFragment.this, Common.getInstance().user, 0, DOWN_STATE);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                referenceTime = System.currentTimeMillis() / 1000;
                if (currentList.size() > 0)
                    UP_FLAG = BusinessHttpProtocol.newDeliverOrder(RepairFragment.this, Common.getInstance().user, currentList.getLast().getId(), UP_STATE);
            }
        });

        accpetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.log("click ", "clikc");
                //  orderDetail.launchActivity(DeliveryFragment.this, 0x2222, accpetDatas.get(position));
            }
        });
        accpetListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Utils.log("down", "yeah");
                referenceTime = System.currentTimeMillis() / 1000;
                DOWN_FLAG = BusinessHttpProtocol.onDeliverOrder(RepairFragment.this, Common.getInstance().user, 0, DOWN_STATE);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Utils.log("down", "yeah");
                referenceTime = System.currentTimeMillis() / 1000;

                if (currentList.size() > 0)
                    UP_FLAG = BusinessHttpProtocol.onDeliverOrder(RepairFragment.this, Common.getInstance().user, currentList.getLast().getId(), UP_STATE);
            }
        });

        historyListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                referenceTime = System.currentTimeMillis() / 1000;
                DOWN_FLAG = BusinessHttpProtocol.deliveryHisOrder(RepairFragment.this, Common.getInstance().user, 0, DOWN_STATE);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                referenceTime = System.currentTimeMillis() / 1000;
                if (currentList.size() > 0)
                    UP_FLAG = BusinessHttpProtocol.deliveryHisOrder(RepairFragment.this, Common.getInstance().user, currentList.getLast().getId(), UP_STATE);
            }
        });

        group_status_selector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onRefreshComplete();
                switch (checkedId) {
                    case R.id.radio_un_accpet:
                        showListView(0);
                        referenceList(0);
                        break;
                    case R.id.radio_accpet:
                        showListView(1);
                        referenceList(1);
                        break;
                    case R.id.radio_history:
                        showListView(2);
                        referenceList(2);
                        break;
                }
            }
        });

//        emptyView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DOWN_FLAG = BusinessHttpProtocol.newDeliverOrder(DeliveryFragment.this, Common.getInstance().user, 0, DOWN_STATE);
//            }
//        });
    }

    public void showListView(int position) {
        historyListView.setVisibility(View.GONE);
        unaccpetListView.setVisibility(View.GONE);
        accpetListView.setVisibility(View.GONE);
        //空数据 显示界面
        TextView tv = new TextView(mActivity);
        tv.setText("空的");


        switch (position) {
            case 0:
                currentViewPosition = 0;
                unaccpetListView.setVisibility(View.VISIBLE);
                group_status_selector.check(R.id.radio_un_accpet);
                unaccpetListView.setEmptyView(emptyView);
                currentList = (LinkedList) unaccpetDatas;
                break;
            case 1:
                currentViewPosition = 1;
                accpetListView.setVisibility(View.VISIBLE);
                group_status_selector.check(R.id.radio_accpet);
                accpetListView.setEmptyView(emptyView);
                currentList = (LinkedList) accpetDatas;
                break;
            case 2:
                currentViewPosition = 2;
                historyListView.setVisibility(View.VISIBLE);
                group_status_selector.check(R.id.radio_history);
                historyListView.setEmptyView(emptyView);
                currentList = (LinkedList) historyDatas;
                break;
        }
    }


    @Override
    public void onGeneralSuccess(final String result, final long flag) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefreshComplete();

                try {
                    JSONObject json = new JSONObject(result);

                    List<RepairOrder> tempList = new LinkedList<RepairOrder>();
                    tempList = gson.fromJson(json.getString("all"), new TypeToken<List<RepairOrder>>() {
                    }.getType());
                    if (flag == DOWN_FLAG) {
                        if (currentViewPosition == 2) {
                            sharedPreferenceUtil.putString(SharedPreferenceUtil.DELIVERY_HISTORY, json.getString("all"));
                            historyDatas.clear();
                            historyDatas.addAll(tempList);
                            historyAdapter.notifyDataSetChanged();
                        } else if (currentViewPosition == 1) {
                            sharedPreferenceUtil.putString(SharedPreferenceUtil.DELIVERY_ACCPET, json.getString("all"));
                            accpetDatas.clear();
                            accpetDatas.addAll(tempList);
                            accpetAdapter.notifyDataSetChanged();
                        } else if (currentViewPosition == 0) {
                            sharedPreferenceUtil.putString(SharedPreferenceUtil.DELIVERY_UNACCPET, json.getString("all"));
                            unaccpetDatas.clear();
                            unaccpetDatas.addAll(tempList);
                            unaccpetAdapter.notifyDataSetChanged();
                        }

                    } else if (flag == UP_FLAG) {
                        if (currentViewPosition == 2) {
                            historyDatas.addAll(tempList);
                            historyAdapter.notifyDataSetChanged();
                        } else if (currentViewPosition == 1) {
                            accpetDatas.addAll(tempList);
                            accpetAdapter.notifyDataSetChanged();
                        } else if (currentViewPosition == 0) {
                            unaccpetDatas.addAll(tempList);
                            unaccpetAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, ((System.currentTimeMillis() / 1000) - referenceTime) < 2000 ? 2000 : 100);


    }

    @Override
    public void onGeneralError(String e, long flag) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefreshComplete();
            }
        }, ((System.currentTimeMillis() / 1000) - referenceTime) < 2000 ? 2000 : 100);

    }

    public void onRefreshComplete() {
        accpetListView.onRefreshComplete();
        historyListView.onRefreshComplete();
        unaccpetListView.onRefreshComplete();
    }


    public void referenceList(int position) {
        switch (position) {
            case 0:
                unaccpetListView.setRefreshing(false);
                break;
            case 1:
                accpetListView.setRefreshing(false);
                break;
            case 2:
                historyListView.setRefreshing(false);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Utils.log("czd", requestCode + "  " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
