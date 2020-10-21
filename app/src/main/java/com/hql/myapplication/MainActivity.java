package com.hql.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.SharedPreferencesCompat;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void pxToDp(View view) {
        final float scale = getResources().getDisplayMetrics().density;
        Log.d("hql","scale 大小："+scale+">>>"+ getResources().getDisplayMetrics().densityDpi);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 2400; i++) {
            //String s= "<dimen name=\"px_2_dp_dimen_"+i+">"+px2dip(i,scale)+"dp</dimen>";
            sb.append("<dimen name=\"px_2_dp_dimen_" + i + "\">" + px2dip(i, scale) + "dp</dimen>");
            //sb.append("<dimen name=\"px_2_dp_dimen_" + i + "\">" + px2dip(this, i) + "dp</dimen>");
        }
        writeTxtToFile(sb.toString(),"/sdcard/px2dp/","px_2_dp.text");


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


}
