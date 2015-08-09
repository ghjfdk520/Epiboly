package com.gas.connector.protocol;

import com.gas.conf.Config;
import com.gas.connector.ConnectorManage;
import com.gas.connector.HttpCallBack;
import com.gas.entity.User;

import java.util.LinkedHashMap;
/**
 * Created by Heart on 2015/7/31.
 */
public class BusinessHttpProtocol {
    public static long Post(String url,
                            LinkedHashMap<String, Object> map, HttpCallBack callback) {
        return ConnectorManage.getInstance().Post(url, map, callback);
    }


    //打卡
    public static long ClockIn(HttpCallBack callback,String driver_id,String latitude,String longitude,String address,String time,int status){
        LinkedHashMap< String , Object > entity = new LinkedHashMap< String , Object >( );
        entity.put( "address" , address);
        entity.put( "time" ,time);
        entity.put( "longitude" , longitude);
        entity.put( "latitude" ,latitude);
        entity.put( "driver_id" ,driver_id);
        entity.put("status",status+"");
        return Post(Config.clockInUrl, entity, callback);
    }

    //获取考勤信息
    public static long checkingInfo(HttpCallBack callback,String id){
        LinkedHashMap< String , Object > entity = new LinkedHashMap< String , Object >( );
        entity.put("id",id);
        return Post(Config.checkInfo, entity, callback);
    }

    //派送历史订单
    public static  long deliveryHisOrder(HttpCallBack callback,User user,int Id,String status ){
        LinkedHashMap< String , Object > entity = new LinkedHashMap< String , Object >( );
        entity.put("depot_id",user.getDepot_id()+"");
        entity.put("id",Id+"");
        entity.put("status", status);
        return Post(Config.deliveryHistoryOrder, entity, callback);
    }
}
