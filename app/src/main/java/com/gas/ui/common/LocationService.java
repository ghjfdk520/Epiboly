package com.gas.ui.common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.gas.conf.Common;
import com.gas.connector.HttpCallBack;
import com.gas.connector.protocol.BusinessHttpProtocol;
import com.gas.database.SharedPreferenceUtil;
import com.gas.entity.User;
import com.gas.utils.BaiduLocationUtil;
import com.gas.utils.LightTimer;

import java.util.Timer;

/**
 * Created by Heart on 2015/8/22.
 */
public class LocationService extends Service implements HttpCallBack{


    private LightTimer locationTimer;

    private User user = Common.getInstance().user;
    private String carId;
    Timer timer;
    private int loopTime = 90*1000;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final String TAG="Test";

    @Override
    public void onCreate()
    { super.onCreate();
    //Service时被调用
        Log.i(TAG, "Service onCreate--->");
        carId = SharedPreferenceUtil.getInstance(this).getString(SharedPreferenceUtil.SHARED_CAR);
        locationTimer = new LightTimer() {
            @Override
            public void run(LightTimer timer) {
                Log.i(TAG, "Service onCreate--->run");
                sendLocation();
            }
        };
        // if(Config.DEBUG) loopTime = 10000;
        locationTimer.startTimer(loopTime);
        Log.i(TAG, "Service onCreate--->" + locationTimer.isRunning());


    }

    @Override
    //当调用者使用startService()方法启动Service时，该方法被调用+
    public void onStart(Intent intent, int startId)
    {
        Log.i(TAG, "Service onStart--->");

        if(!locationTimer.isRunning())
            locationTimer.startTimer(loopTime);
        Log.i(TAG, "Service onStart--->"+locationTimer.isRunning());
        super.onStart(intent, startId);
    }

    @Override
    //当Service不在使用时调用
    public void onDestroy()
    {
        Log.i(TAG, "Service onDestroy--->");
        super.onDestroy();
        locationTimer.stop();
    }

    private void sendLocation(){
        Log.i(TAG, "sendLocation--->start");
        BaiduLocationUtil.getInstance(this).startBaiduListener(new BaiduLocationUtil.BaiduCallBack() {
            @Override
            public void updateBaidu(int type, int lat, int lng, String address, String simpleAddress) {
                Log.i(TAG, "sendLocation--->send");
                BusinessHttpProtocol.siteCar(LocationService.this,carId,lat,lng);
            }
        });
    }

    @Override
    public void onGeneralError(String e, long flag) {

    }

    @Override
    public void onGeneralSuccess(String result, long flag) {

    }




}
