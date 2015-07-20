package com.gas.ui.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.gas.CloseAllActivity;
 import com.gas.connector.HttpCallBack;
import com.gas.utils.Utils;

/**
 * Created by Heart on 2015/7/20.
 */
public class SuperActivity extends Activity implements HttpCallBack, Thread.UncaughtExceptionHandler {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, true);
    }

    protected void onCreate(Bundle savedInstanceState, boolean addToStack) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(this);

        mContext = this;

        if (addToStack)
            CloseAllActivity.getInstance().addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.log("SuperActivity", "onStart");
        setComponentListener(this);
    }

    /**
     * 设置视图组件事件监听器
     *
     * @param context 环境对象
     */

    protected void setComponentListener(SuperActivity context) {

    }

    /**
     * 把软键盘隐藏
     */
    public boolean hiddenKeyBoard(Activity mActivity) {
        // 点击屏幕任何地方则把软键盘隐藏
        if (mActivity.getCurrentFocus() != null) {
            ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        Utils.dumpLogcat();
         try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CloseAllActivity.getInstance().close();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    // 保存数据
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // 恢复数据
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    /**
     * 获取string
     *
     * @param id
     * @return
     */
    public String getResString(int id) {
        return getResources().getString(id);
    }

    /**
     * 获取图片资源
     *
     * @param id
     * @return
     */
    public Drawable getResDrawable(int id) {
        return getResources().getDrawable(id);
    }

    /**
     * 获取color值
     *
     * @param id
     * @return
     */
    public int getResColor(int id) {
        return getResources().getColor(id);
    }

    @Override
    public void onGeneralSuccess(String result, long flag) {
    }

    @Override
    public void onGeneralError(String e, long flag) {
    }


    public Context mContext;
}
