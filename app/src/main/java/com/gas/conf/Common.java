package com.gas.conf;

import com.gas.entity.User;

/**
 * Created by Heart on 2015/8/1
 * 保存共公变量.
 */
public class Common {
    private static class SingleInstance{
        public static Common INSTANCE = new Common();
    }
    public static Common getInstance(){
        return SingleInstance.INSTANCE;
    }
    public User user = new User();
    /** 服务器与本地的时间差值 ，校正方式为：获取当前时间+这个差值 **/
    public long serverToClientTime = 0;
    public int windowWidth = 0; // 屏幕宽度
    public int windowHeight = 0; // 屏幕高度

}
