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
    public static boolean uiRunning = false; // 标记程序是否已经进入UI运行模式（false表示处于后台service）
    private static float density;

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

    public static String decodeUnicode(String theString){
        char aChar;

        int len = theString.length();

        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len;) {

            aChar = theString.charAt(x++);

            if (aChar == '\\') {

                aChar = theString.charAt(x++);

                if (aChar == 'u') {

                    // Read the xxxx

                    int value = 0;

                    for (int i = 0; i < 4; i++) {

                        aChar = theString.charAt(x++);

                        switch (aChar) {

                            case '0':

                            case '1':

                            case '2':

                            case '3':

                            case '4':

                            case '5':

                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';

                    else if (aChar == 'n')

                        aChar = '\n';

                    else if (aChar == 'f')

                        aChar = '\f';

                    outBuffer.append(aChar);

                }

            } else

                outBuffer.append(aChar);

        }

        return outBuffer.toString();

    }

    /** dip转px */
    public static int dipToPx( Context context , int dip )
    {
        if ( density <= 0 )
        {
            density = context.getResources( ).getDisplayMetrics( ).density;
        }
        return ( int ) ( dip * density + 0.5f );
    }

    /** px转dip */
    public static int pxToDip( Context context , int px )
    {
        if ( density <= 0 )
        {
            density = context.getResources( ).getDisplayMetrics( ).density;
        }
        return ( int ) ( ( px - 0.5f ) / density );
    }
}
