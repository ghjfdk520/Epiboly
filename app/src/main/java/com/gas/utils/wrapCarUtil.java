package com.gas.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gas.BaseApplication;
import com.gas.conf.Common;
import com.gas.connector.protocol.BusinessHttpProtocol;
import com.gas.database.SharedPreferenceUtil;
import com.gas.entity.User;
import com.gas.ui.common.LocationService;

/**
 * Created by Heart on 2015/8/22.
 */
public class wrapCarUtil {
    private static final String TAG="Test";

    private static wrapCarUtil wrapCarUtil;
    private LightTimer lightTimer ;
    private Context mContext;
    private User user = Common.getInstance().user;
    private String carId;
    private int loopTime = 15*1000;
    public wrapCarUtil() {

        mContext = BaseApplication.mContext;
        carId = SharedPreferenceUtil.getInstance(mContext).getString(SharedPreferenceUtil.SHARED_CAR);
        lightTimer = new LightTimer() {
            @Override
            public void run(LightTimer timer) {
                sendLocation();
            }
        };

    }

    public static wrapCarUtil getInstance() {
        if (wrapCarUtil == null) {
            synchronized (SharedPreferenceUtil.class) {

                if (wrapCarUtil == null) {
                    wrapCarUtil = new wrapCarUtil();
                }
            }

        }
        return wrapCarUtil;
    }

    public void clear(){
        wrapCarUtil = null;
    }

    public boolean startLocation(){
        boolean isShare =  SharedPreferenceUtil.getInstance(mContext).getString(SharedPreferenceUtil.SHARED_PLACES).equals("1");

        if(isShare){
            startLocationTimer();
        }
        return  isShare;
    }
    public void startLocation(String carId){
        SharedPreferenceUtil.getInstance(mContext).putString(SharedPreferenceUtil.SHARED_CAR,carId);
        SharedPreferenceUtil.getInstance(mContext).putString(SharedPreferenceUtil.SHARED_PLACES, "1");
        startLocationTimer();
    }

    private void startLocationTimer(){
        lightTimer.startTimer(loopTime);
    }

    public  void stopLocationTimer(){
       if(lightTimer.isRunning()){
           lightTimer.stop();
       }
        if (wrapCarUtil!=null) {
            wrapCarUtil = null;
        }
    }

    public static boolean startLocation(Context mContext){
        boolean isShare =  SharedPreferenceUtil.getInstance(mContext).getString(SharedPreferenceUtil.SHARED_PLACES).equals("1");

        if(isShare){
            startService(mContext);
        }
        return  isShare;
    }

    public static void startLocation(Context mContext,String carId){
        SharedPreferenceUtil.getInstance(mContext).putString(SharedPreferenceUtil.SHARED_CAR,carId);
        SharedPreferenceUtil.getInstance(mContext).putString(SharedPreferenceUtil.SHARED_PLACES, "1");
        startService(mContext);
    }


    public static  void stopLocation(Context mContext){
        SharedPreferenceUtil.getInstance(mContext).putString(SharedPreferenceUtil.SHARED_PLACES,"0");
        SharedPreferenceUtil.getInstance(mContext).putString(SharedPreferenceUtil.SHARED_CAR, "");
        stopService(mContext);
    }

    private static void startService(Context mContext){
        Intent intent=new Intent(mContext, LocationService.class);
        mContext.startService(intent);
    }

    private static  void stopService(Context mContext){
        Intent intent=new Intent(mContext, LocationService.class);
        mContext.stopService(intent);

    }
    private void sendLocation(){
        Log.i(TAG, "sendLocation--->start");
        BaiduLocationUtil.getInstance(mContext).startBaiduListener(new BaiduLocationUtil.BaiduCallBack() {
            @Override
            public void updateBaidu(int type, int lat, int lng, String address, String simpleAddress) {
                Log.i(TAG, "sendLocation--->send");
                BusinessHttpProtocol.siteCar(null, carId, lat, lng);
            }
        });
    }
}
