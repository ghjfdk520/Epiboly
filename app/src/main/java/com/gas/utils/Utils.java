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


    public static boolean isEmptyOrNullStr( String str )
    {
        return TextUtils.isEmpty(str) || "".equals( str );
    }

    public static void toastMsg( Context context , String sMsg )
    {
        Toast.makeText(context, sMsg, Toast.LENGTH_SHORT).show( );
    }

}
