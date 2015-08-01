package com.gas.epiboly;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gas.conf.Common;
import com.gas.connector.HttpCallBack;
import com.gas.map.BaiduLocationUtil;
import com.gas.ui.common.SuperActivity;
import com.gas.ui.fragment.AttendanceFragment;
import com.gas.ui.fragment.DeliveryFragment;
import com.gas.ui.fragment.GasFragment;
import com.gas.ui.fragment.PersonalFrament;
import com.gas.ui.fragment.RepairFragment;
import com.gas.ui.view.NestRadioGroup;
import com.gas.utils.CommonUtil;
import com.gas.utils.Utils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends SuperActivity implements HttpCallBack {
    private static final int MSG_SET_ALIAS = 1001;
    public static final String MESSAGE_RECEIVED_ACTION = "com.gas.epiboly.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    private MessageReceiver mMessageReceiver;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment[] mFragments;
    private AttendanceFragment attendanceFragment;
    private DeliveryFragment deliveryFragment;
    private GasFragment gasFragment;
    private PersonalFrament personalFrament;
    private RepairFragment repairFragment;
    private NestRadioGroup mNestRadioGroup;
    private int checkId = 1;  //显示界面id
    private View v;
    // 百度地位模块
    private BaiduLocationUtil mBaiduLocationutil;
    private Button button;
    public static boolean isForeground = false;


    public static void launchActivity(Activity fromActivity) {
        Intent i = new Intent(fromActivity, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        fromActivity.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListeners();
        initLocation();
    }

    public void init() {
        initJpush();
        registerMessageReceiver();
        mNestRadioGroup = (NestRadioGroup) findViewById(R.id.nav_radio_group);
        fragmentManager = getFragmentManager();
        attendanceFragment = (AttendanceFragment) fragmentManager.findFragmentById(R.id.attendance_fragment);

        deliveryFragment = (DeliveryFragment) fragmentManager.findFragmentById(R.id.delivery_fragment);
        gasFragment = (GasFragment) fragmentManager.findFragmentById(R.id.gas_fragment);
        personalFrament = (PersonalFrament) fragmentManager.findFragmentById(R.id.personal_fragment);
        repairFragment = (RepairFragment) fragmentManager.findFragmentById(R.id.repair_fragment);
        mFragments = new Fragment[5];
        mFragments[0] = personalFrament;
        mFragments[1] = attendanceFragment;
        mFragments[2] = deliveryFragment;
        mFragments[3] = repairFragment;
        mFragments[4] = gasFragment;

         fragmentTransaction = fragmentManager.beginTransaction()
                 .hide(mFragments[0])
                .hide(mFragments[1]).hide(mFragments[2])
                .hide(mFragments[3]).hide(mFragments[4]);
        fragmentTransaction.show(mFragments[checkId]);
        fragmentTransaction.commit();

        switch (checkId) {
            case 0:
                mNestRadioGroup.check(R.id.radio_personal);
                break;
            case 1:
                mNestRadioGroup.check(R.id.radio_attendance);
                break;
            case 2:
                mNestRadioGroup.check(R.id.radio_delivery);
                break;
            case 3:
                mNestRadioGroup.check(R.id.radio_repair);
                break;
            case 4:
                mNestRadioGroup.check(R.id.radio_gas);
                break;
            default:
                break;
        }
    }

    public void initListeners() {
        mNestRadioGroup
                .setOnCheckedChangeListener(new NestRadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(NestRadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.radio_personal:
                                showFragment(0);
                                break;
                            case R.id.radio_attendance:
                                showFragment(1);
                                break;
                            case R.id.radio_delivery:
                                showFragment(2);
                                break;
                            case R.id.radio_repair:
                                showFragment(3);
                                break;
                            case R.id.radio_gas:
                                showFragment(4);
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理

    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }
    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    // 定位功能！！
    private void initLocation() {
        BaiduLocationUtil.getInstance(this).startBaiduListener(null);
//        mBaiduLocationutil = BaiduLocationUtil.getInstance(this);
//        mBaiduLocationutil.startBaiduListener(new BaiduCallBack() {
//
//            public void updateBaidu(int type, int lat, int lng, String address,
//                                    String simpleAddress) {
//                // TODO Auto-generated method stub
//                Utils.log("czd", type + " " + lat / 1e6 + " " + lng / 1e6 + " "
//                        + address);
//
//            }
//        });
    }

    @Override
    public void onGeneralSuccess(String result, long flag) {
        System.out.print(result);
        Log.d("czd result", result);
    }

    @Override
    public void onGeneralError(String e, long flag) {

    }

    public void showFragment(int position) {
            fragmentTransaction = fragmentManager
                    .beginTransaction().hide(mFragments[0])
                    .hide(mFragments[1]).hide(mFragments[2])
                    .hide(mFragments[3]).hide(mFragments[4]);
            fragmentTransaction.show(mFragments[position]);
            fragmentTransaction.commit();
    }

    public void initJpush(){
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, Common.getInstance().user.getPhone()));
    }

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Utils.log("JPUSH", logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Utils.log("JPUSH", logs);
                    if (CommonUtil.isNetworkAvailable(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Utils.log("JPUSH", "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Utils.log("JPUSH", logs);
            }

            //Utils.toastMsg(MainActivity.this,logs);
        }

    };
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }
    public class MessageReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MESSAGE_RECEIVED_ACTION.equals(intent.getAction())){
                String message= intent.getStringExtra(KEY_MESSAGE);
                Utils.log("Main",message);
            }
        }
    }
}
