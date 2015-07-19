package com.gas;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.gas.utils.ImageViewUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Heart on 2015/7/16.
 */
public class BaseApplication extends Application{

    @Override
    public void onCreate() {

        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        ImageViewUtil.initDefault(getApplicationContext());
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageViewUtil.getDefault().clearDefaultLoaderMemoryCache();
    }
}
