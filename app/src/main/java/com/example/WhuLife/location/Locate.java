package com.example.WhuLife.location;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;


import java.util.List;

import static com.amap.api.location.CoordinateConverter.calculateLineDistance;

public class Locate extends AppCompatActivity {
    public static final double ACCURACY_LIMIT = 30;
    public static final double DISTANCE_LIMIT = 50;
    //the location you are
    public double Latitude;
    public double Longtitude;

    //target location
    public double t_latitude, t_longtitude;
    public static double distance;
    public static double accuracy;

    //mainactivity context
    private static Context context;

    private SQLiteDatabase db;
    private int i=0;
    //for location
    private AMapLocationClient mapLocationClient = null;
    private AMapLocationClientOption mapLocationClientOption = null;

    public Locate(Context context, SQLiteDatabase db){
        this.context = context;
        this.db = db;
    }

    //init
    public void initLocation(AMapLocationClient mapLocationClient){
        //初始化client
        this.mapLocationClient = mapLocationClient;
        mapLocationClientOption = getDefaultOption();
        //设置定位参数
        mapLocationClient.setLocationOption(mapLocationClientOption);
        // 设置定位监听
        mapLocationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     * @return
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);
        //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(5000);
        //可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);
        //可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);
        //可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);
        //可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true);
        //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true);
        //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);
        //可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    //Toast.makeText(context, "locate ",Toast.LENGTH_SHORT).show();
                    Latitude = location.getLatitude();
                    Longtitude = location.getLongitude();
                    DPoint myPoint = new DPoint(Latitude, Longtitude);
                    accuracy = location.getAccuracy();
                    Cursor cursor = db.query("target", null,null,null,null,null, null);
                    if(cursor.moveToFirst()) {
                        do {
                            t_latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                            t_longtitude = cursor.getDouble(cursor.getColumnIndex("longtitude"));
                            DPoint t_Point = new DPoint(t_latitude, t_longtitude);
                            distance = calculateLineDistance(t_Point, myPoint);
                            if(isNear() == false){
                                Toast.makeText(context, "locate "+t_longtitude,Toast.LENGTH_SHORT).show();
                            }
                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                }
            } else {
                Log.e("error", "locate error");
            }
        }
    };

    //set target location
    public void setLocation(double latitude, double longtitude){
        ContentValues values = new ContentValues();
        values.put("latitude", latitude);
        values.put("longtitude", longtitude);
        db.insert("target", null, values);
        values.clear();
    }


    //判断是否足够接近目标地点
    public static boolean isNear(){
        if(accuracy <= ACCURACY_LIMIT){
            if(distance <= DISTANCE_LIMIT){
                //Toast.makeText(context, "Near!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                //Toast.makeText(context, "too far!", Toast.LENGTH_SHORT).show();
            }
        } else {
            //Toast.makeText(context, "not accurate!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    public void startLocation(){
        // 启动定位
        mapLocationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    public void stopLocation(){
        // 停止定位
        mapLocationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    public void destroyLocation(){
        if (null != mapLocationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            mapLocationClient.onDestroy();
            mapLocationClient = null;
            mapLocationClientOption = null;
        }
    }
}


