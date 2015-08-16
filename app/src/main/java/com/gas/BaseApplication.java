package com.gas;

import android.app.Application;
import android.content.Context;

import com.gas.entity.User;
import com.gas.utils.BaiduLocationUtil;
import com.gas.utils.ImageViewUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Heart on 2015/7/16.
 */
public class BaseApplication extends Application{
    public static User user;
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        ImageViewUtil.initDefault(getApplicationContext());
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        this.mContext = getBaseContext();
        BaiduLocationUtil.getInstance(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageViewUtil.getDefault().clearDefaultLoaderMemoryCache();
    }
}
