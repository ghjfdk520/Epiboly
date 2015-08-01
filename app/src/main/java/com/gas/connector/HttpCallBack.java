package com.gas.connector;

import org.json.JSONException;

/**
 * Created by Heart on 2015/7/19.
 */
public interface HttpCallBack {
    public void onGeneralSuccess( String result , long flag ) throws JSONException;
    public void onGeneralError( String e , long flag );
}
