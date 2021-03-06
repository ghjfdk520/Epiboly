package com.gas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.TelephonyManager;

import com.gas.conf.Common;
import com.gas.database.UserWorker;
import com.gas.entity.User;
import com.gas.epiboly.StartActity;
import com.gas.utils.BaiduLocationUtil;
import com.gas.utils.ImageViewUtil;
import com.gas.utils.StringEncrypt;
import com.pgyersdk.crash.PgyCrashManager;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Heart on 2015/7/16.
 */
public class BaseApplication extends Application {
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
        PgyCrashManager.register(this, "66a0cbb2f41686b59a52833aa45d0442");

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageViewUtil.getDefault().clearDefaultLoaderMemoryCache();
    }


    /**
     * 获得设备id
     *
     * @return
     */
    public static String getDeviceId() {
        String deviceId = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        return StringEncrypt.encodeByAsymmetric(deviceId, StringEncrypt.EncodeType.MD5, StringEncrypt.Case.LOWER);
    }

    public static void showLogoutDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您的账号在其他登陆，请重新登陆。");
        builder.setTitle("提示");
        builder.setCancelable(false);

        Set<String> tagSet = new LinkedHashSet<String>();
        JPushInterface.setAliasAndTags(mContext, "", tagSet, null);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                new UserWorker(mContext).removeAll(Common.getInstance().user.getId());
                Common.getInstance().user = null;
                CloseAllActivity.getInstance().close();
                StartActity.launchActivity((Activity) context);
            }
        });

        builder.create().show();
    }

}
