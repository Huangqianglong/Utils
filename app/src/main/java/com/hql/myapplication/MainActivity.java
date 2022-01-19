package com.hql.myapplication;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hql.common.LoggerUtil;
import com.hql.location.LocationHelper;
import com.hql.netlib.NetworkHelper;
import com.hql.netlib.miniprogram.WeatherResultBean;
import com.hql.netlib.miniprogram.WeatherResultSubscriber;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private TextView mLocationTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocationTv = findViewById(R.id.tv_location);
        LocationHelper.getInstance().setMyLocationListener(new LocationHelper.MyLocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLocationTv.setText("经度：" + location.getLatitude()
                        + "\n纬度：" + location.getLongitude());
            }
        });
        LocationHelper.getInstance().initLocation(this);
    }

    public void pxToDp(View view) {
        final float scale = getResources().getDisplayMetrics().density;
        LoggerUtil.d("hql", "scale 大小：" + scale + ">>>" + getResources().getDisplayMetrics().densityDpi);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 2400; i++) {
            //String s= "<dimen name=\"px_2_dp_dimen_"+i+">"+px2dip(i,scale)+"dp</dimen>";
            sb.append("<dimen name=\"px_2_dp_dimen_" + i + "\">" + px2dip(i, scale) + "dp</dimen>");
            //sb.append("<dimen name=\"px_2_dp_dimen_" + i + "\">" + px2dip(this, i) + "dp</dimen>");
        }
        writeTxtToFile(sb.toString(), "/sdcard/px2dp/", "px_2_dp.text");


    }

    /**
     * 生成文件夹
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }


    /**
     * 生成文件
     *
     * @param filePath 文件地址
     * @param fileName 文件名
     */
    private static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 字符串写入本地txt
     *
     * @param strcontent 文件内容
     * @param filePath   文件地址
     * @param fileName   文件名
     * @return 写入结果
     */
    private static boolean writeTxtToFile(String strcontent, String filePath, String fileName) {
        boolean isSavaFile = false;
        makeFilePath(filePath, fileName);
        String strFilePath = filePath + fileName;
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
            isSavaFile = true;
        } catch (Exception e) {
            isSavaFile = false;
            Log.e("TestFile", "Error on write File:" + e);
        }
        return isSavaFile;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue, final float scale) {
        return (int) (pxValue / scale + 0.5f);
    }

    public void requestWeather() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "key1");
        map.put("body", "body");
        NetworkHelper.getInstance().doHttpRequest(map, map.get("body"), new WeatherResultSubscriber() {
            @Override
            protected void onSuccess(WeatherResultBean result) {

            }

            @Override
            protected void onError(String result, String errorMsg) {

            }
        });
    }

}
