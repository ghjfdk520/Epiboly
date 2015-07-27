package com.gas.epiboly;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.gas.connector.HttpCallBack;
import com.gas.map.BaiduLocationUtil;
import com.gas.map.BaiduLocationUtil.BaiduCallBack;
import com.gas.ui.common.SuperActivity;
import com.gas.ui.fragment.AttendanceFragment;
import com.gas.ui.fragment.DeliveryFragment;
import com.gas.ui.fragment.GasFragment;
import com.gas.ui.fragment.PersonalFrament;
import com.gas.ui.fragment.RepairFragment;
import com.gas.ui.view.NestRadioGroup;
import com.gas.utils.Utils;

public class MainActivity extends SuperActivity implements HttpCallBack {
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

    // 百度地位模块
    private BaiduLocationUtil mBaiduLocationutil;
    private Button button;

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
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
    }

    // 定位功能！！
    private void initLocation() {
        mBaiduLocationutil = BaiduLocationUtil.getInstance(this);
        mBaiduLocationutil.startBaiduListener(new BaiduCallBack() {

            public void updateBaidu(int type, int lat, int lng, String address,
                                    String simpleAddress) {
                // TODO Auto-generated method stub
                Utils.log("czd", type + " " + lat / 1e6 + " " + lng / 1e6 + " "
                        + address);
            }
        }, 1);
    }

    @Override
    public void onGeneralSuccess(String result, long flag) {
        Log.d("czd", result);
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
}
