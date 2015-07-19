package com.gas.epiboly;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.map.MapView;
import com.gas.connector.HttpCallBack;
import com.gas.connector.protocol.HttpProtocol;
import com.gas.map.BaiduLocationUtil;
import com.gas.map.BaiduLocationUtil.BaiduCallBack;
import com.gas.utils.Utils;
public class MainActivity extends Activity implements HttpCallBack
{
    MapView mMapView = null;
    // 百度地位模块
    private BaiduLocationUtil mBaiduLocationutil;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.bmapView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLocation();
            }
        });
        initLocation();
        HttpProtocol.test(this,this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 定位功能！！
    private void initLocation() {
        Utils.log("czd", "Start");
        Log.d("czd","start2");
        mBaiduLocationutil = BaiduLocationUtil.getInstance(this);

        mBaiduLocationutil.startBaiduListener(new BaiduCallBack() {

            public void updateBaidu(int type, int lat, int lng, String address,
                                    String simpleAddress) {
                // TODO Auto-generated method stub
                Utils.log("czd", type + " " + lat / 1e6 + " " + lng/ 1e6 + " "
                        + address);
            }
        }, 1);
    }

    @Override
    public void onGeneralSuccess(String result, long flag) {
           Log.d("czd",result);
    }

    @Override
    public void onGeneralError(String e, long flag) {

    }
}
