package com.hql.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.hql.common.LoggerUtil;

import java.util.List;
import java.util.Random;


/**
 * @author ly-huangql
 * <br /> Create time : 2021/10/28
 * <br /> Description :
 */
public class LocationHelper {
    private final static String TAG = "LocationHelper_hql";
    private LocationManager mLocationManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    /**
     * 经纬度
     */
    private double mLongitude = 114.09105624555885, mLatitude = 22.542505499323852;

    private static class LocationHolder {
        private static LocationHelper mInstance = new LocationHelper();
    }

    public static LocationHelper getInstance() {
        return LocationHolder.mInstance;
    }

    public void initLocation(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //showToast("Toast 弹出测试");
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            getLocation();
            //gps已打开
        } else {
            toggleGPS();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getLocation();
                }
            }, 2000);

        }
    }

    @SuppressLint("MissingPermission")
    private void toggleGPS() {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));

        Location location1 = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location1 != null) {
            mLatitude = location1.getLatitude();
            mLongitude = location1.getLongitude();
        }

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LoggerUtil.d(TAG, ">>>>>>当前位置信息" + location);
        if (location != null) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
        } else {
            LoggerUtil.d(TAG, "获取网络定位");
            List<String> list = mLocationManager.getAllProviders();
            for (String gps : list) {
                LoggerUtil.d(TAG, "本机支持：" + gps);
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 10, locationListener);

        }
        if (null != myLocationListener && null!= location) {
            myLocationListener.onLocationChanged(location);
        } //info.setText("纬度：" + latitude + "\n" + "经度：" + longitude);
    }

    LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
            LoggerUtil.d(TAG, provider);
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
            LoggerUtil.d(TAG, provider);
        }

        // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            LoggerUtil.i(TAG, "onLocationChanged : Lat: " + location);
            if (location != null) {
                LoggerUtil.i(TAG, "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
                //showToast("Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
                mLatitude = location.getLatitude(); // 经度
                mLongitude = location.getLongitude(); // 纬度
                if (null != myLocationListener) {
                    myLocationListener.onLocationChanged(location);
                }
            }
        }
    };

    public double getLongitude() {
        return mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    Toast mToast;

    public void showToast(Context context, String msg) {
        if (null == mToast) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        mToast.cancel();
        mToast.show();
    }

    public void randomGPS() {
        //if (BuildConfig.RADOM_GPS) {
        Random random = new Random();
        mLatitude = 22.542505499323852 + random.nextInt(9) / 100d;
        mLongitude = 114.09105624555885 + random.nextInt(9) / 100d;
        //}

    }

    public interface MyLocationListener {
        void onLocationChanged(Location location);
    }

    private MyLocationListener myLocationListener;

    public void setMyLocationListener(MyLocationListener listener) {
        myLocationListener = listener;
    }
}
