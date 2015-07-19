package com.gas.connector;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gas.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Heart on 2015/7/16.
 */
public class ConnectorManage {
    private AtomicLong mHttpCount = new AtomicLong(0);
    private final String TAG = ConnectorManage.class.getName();
    private RequestQueue rQueue;
    private Context mContext;
    private Map<Long,Request>  requestMap = new HashMap<Long,Request>();


    private static ConnectorManage sInstance;

    private ConnectorManage(Context context) {
        mContext = context;
        rQueue = Volley.newRequestQueue(context);
    }

    public static ConnectorManage getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ConnectorManage.class) {
                if (sInstance == null)
                    sInstance = new ConnectorManage(context);
            }
        }
        return sInstance;
    }

    public long Post(final String url, final Map map,
                     final HttpCallBack callback) {
        final long flag = mHttpCount.incrementAndGet();
        if(map!=null)
        Utils.log("request  " + flag, url + " " + map.toString());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        requestMap.remove(flag);
                        Utils.log("RESULT  " + flag, response);
                        if (response == null) {
                            if (callback != null) {
                                callback.onGeneralError(response, flag);
                            }
                        } else {
                            if (callback != null) {
                                callback.onGeneralSuccess(response, flag);
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestMap.remove(flag);
                if (error == null || !error.getMessage().equals("")) {
                    Utils.toastMsg(mContext, error.getMessage());
                }
                Utils.log("RESULT Error", error.getMessage());
                if (callback != null) {
                    callback.onGeneralError(error.getMessage(), flag);
                }
                requestMap.remove(flag);

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // TODO Auto-generated method stub
                return map;
            }

        };

        Request r = rQueue.add(stringRequest);
        requestMap.put(flag, r);
        return flag;
    }

    public void cancleRequest(long flag){
        Request r = requestMap.get(flag);
        requestMap.remove(flag);
        r.cancel();
    }
}
