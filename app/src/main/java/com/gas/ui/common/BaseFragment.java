package com.gas.ui.common;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gas.epiboly.R;
import com.google.gson.Gson;

/**
 * Created by Heart on 2015/7/21.
 */
public class BaseFragment extends Fragment {
    private Activity mActivity;

    private ProgressDialog mProgressDialog;

    protected int mScreenWidth;
    protected int mScreenHeight;
    protected Gson gson = new Gson();
    public BaseFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        mActivity = activity;
        DisplayMetrics metric = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void alertToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    public String rString(int stringId) {
        String str = "";
        str = this.getResources().getString(stringId);
        return str;
    }

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(mActivity, "",
                    rString(R.string.loading), false, true);
        } else {
            mProgressDialog.setMessage(rString(R.string.loading));
            mProgressDialog.show();
        }
    }

    public void showLoading(int stringId) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(mActivity, "",
                    rString(stringId), false, true);
        } else {
            mProgressDialog.setMessage(rString(stringId));
            mProgressDialog.show();
        }
    }

    public void showLoading(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(mActivity, "", message,
                    false, true);
        } else {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    public void closeLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
