package com.hql.toucheventanalyse;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author ly-huangql
 * <br /> Create time : 2022/2/28
 * <br /> Description :
 */
public class MyLinearLayout extends LinearLayout implements View.OnTouchListener {

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("test", "【组长】下达任务  dispatchTouchEvent：" + Util.actionToString(ev.getAction())  + "，找个人帮我完成，任务往下分发。");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean relust = false;
        Log.i("test", "【组长】是否拦截任务  onInterceptTouchEvent：" + Util.actionToString(ev.getAction())  + "，拦下来？" + relust);
        return relust;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean relust = false;
        Log.i("test", "【组长】完成任务  onTouchEvent：" + Util.actionToString(event.getAction()) + "，【员工】太差劲了，以后不再找你干活了，我自来搞定！是否解决：" + Util.canDoTask(relust));
        return relust;
    }

    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        boolean relust = true;
        Log.i("test", "【组长】是否拦截任务  onTouch：" + Util.actionToString(ev.getAction())  + "，拦下来？" + relust);
        return relust;
    }
}
