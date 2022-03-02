package com.hql.toucheventanalyse;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author ly-huangql
 * <br /> Create time : 2022/2/28
 * <br /> Description :
 */
public class MyTextView extends TextView {


    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("test", "【员工】下达任务  dispatchTouchEvent：" + Util.actionToString(event.getAction())  + "，我没手下了，唉~自己干吧");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean relust = false;
        Log.i("test", "【员工】完成任务  onTouchEvent：" + Util.actionToString(event.getAction()) + "，【员工】现在只能靠自己了！是否解决：" + Util.canDoTask(relust));
        return relust;
    }
}
