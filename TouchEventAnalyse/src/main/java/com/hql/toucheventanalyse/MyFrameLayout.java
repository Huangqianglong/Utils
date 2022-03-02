package com.hql.toucheventanalyse;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * @author ly-huangql
 * <br /> Create time : 2022/2/28
 * <br /> Description :Touch分发事件分析
 */
public class MyFrameLayout extends FrameLayout {

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("test", "【经理】下达任务  dispatchTouchEvent：" + Util.actionToString(ev.getAction())  + "，找个人帮我完成，任务往下分发。");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean relust = false;
        Log.i("test", "【经理】是否拦截任务  onInterceptTouchEvent：" + Util.actionToString(ev.getAction())  + "，拦下来？" + relust);
        return relust;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean relust = true;
        Log.i("test", "【经理】完成任务  onTouchEvent：" + Util.actionToString(event.getAction()) + "，【组长】太差劲了，以后不再找你干活了，我自来搞定！是否解决：" + Util.canDoTask(relust));
        return relust;
    }
}
