package com.gas.connector.protocol;

import android.content.Context;

import com.gas.connector.ConnectorManage;
import com.gas.connector.HttpCallBack;

import java.util.LinkedHashMap;

/**
 * Created by Heart on 2015/7/19.
 */
public class HttpProtocol {
    public static long Post(Context context, String url,
                            LinkedHashMap<String, Object> map, HttpCallBack callback) {
        return ConnectorManage.getInstance(context).Post(url, map, callback);
    }

    public static long test(Context context,HttpCallBack callback){
        LinkedHashMap< String , Object > entity = new LinkedHashMap< String , Object >( );
        entity.put( "tn" , "myie2dg");
        return Post(context,"https://www.baidu.com/",entity,callback);
    }

}
