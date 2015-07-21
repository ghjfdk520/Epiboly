package com.gas.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.gas.conf.Config;

import java.io.File;
import java.util.Calendar;

/**
 * Created by Heart on 2015/7/16.
 */
public class Utils {

    public static String getSDPath( )
    {
        String sdDir = "";
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED ); // 判断sd卡是否存在
        if ( sdCardExist )
        {
            sdDir = Environment.getExternalStorageDirectory( ) + "/gas/";// 获取跟目录

        }
        else
        {
            sdDir = "/data/data/com.gas/";
            // sdDir = Environment.getDataDirectory() + "/iAround/";
        }

        File file = new File( sdDir );
        if ( !file.exists( ) )
        {
            file.mkdirs( );
            if ( !file.exists( ) )
            {
                sdDir = "/data/data/com.gas/";
                File tryfile = new File( sdDir );
                if ( !tryfile.exists( ) )
                {
                    tryfile.mkdirs( );
                }
            }
        }
        return sdDir;
    }

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

    public static void dumpLogcat( )
    {
        if ( !Config.DEBUG )
        {
            try
            {
                File uncaughtExceptionLogFolder = new File( getSDPath( )
                        + "UncaughtExceptions/" );
                if ( !uncaughtExceptionLogFolder.exists( ) )
                {
                    uncaughtExceptionLogFolder.mkdirs( );
                }
                String fileName = TimeFormat.convertTimeLong2String(
                        System.currentTimeMillis( ) , Calendar.SECOND )
                        + ".txt";
                String dumpFile = uncaughtExceptionLogFolder.getAbsolutePath( ) + "/"
                        + fileName;
                Process pDump = Runtime.getRuntime( )
                        .exec( "logcat -v time -d -f " + dumpFile );
                pDump.waitFor( );
                Utils.log( "System.err" , "Log file has been dump to \"" + dumpFile
                        + "\"" );
            }
            catch ( Exception e )
            {
                e.printStackTrace( );
            }
        }
    }
}
