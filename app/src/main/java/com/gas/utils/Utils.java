package com.gas.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.gas.conf.Config;

/**
 * Created by Heart on 2015/7/16.
 */
public class Utils {
    /** 打印log */
    public static void log( String tag , Object... msg )
    {
        if ( Config.DEBUG && msg != null )
        {
            StringBuilder sb = new StringBuilder( );
            int i = 0;
            for ( Object o : msg )
            {
                if ( i > 0 )
                {
                    sb.append( ',' );
                }
                sb.append( o == null ? "null" : o.toString( ) );
                i++;
            }

            int logStrLength = sb.length( );
            int maxLogSize = 1000;
            for ( i = 0 ; i <= logStrLength / maxLogSize ; i++ )
            {
                int start = i * maxLogSize;
                int end = ( i + 1 ) * maxLogSize;
                end = end > logStrLength ? logStrLength : end;
                if ( tag.equals( "sherlock" ) )
                {
                    Log.i(tag, sb.substring(start, end));
                }
                else
                {
                    Log.v( "DongZ_" + tag , sb.substring( start , end ) );
                }
            }
        }
    }

    /**
     * 判断字符串是否为空字符串、null或“null”字符串包括所有大小写情况
     *
     * @param str
     * @return 是否为空
     */
    public static boolean isEmptyOrNullStr( String str )
    {
        return TextUtils.isEmpty(str) || "".equals( str );
    }
    /**
     * @Title: toastMsg
     * @Description: Toast一条消息
     * @param context
     * @param sMsg
     *            String msg
     */
    public static void toastMsg( Context context , String sMsg )
    {
        Toast.makeText(context, sMsg, Toast.LENGTH_SHORT).show( );
    }

}
