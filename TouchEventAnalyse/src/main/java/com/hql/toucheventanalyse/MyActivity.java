package com.hql.toucheventanalyse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author ly-huangql
 * <br /> Create time : 2022/2/28
 * <br /> Description :Touch分发事件分析
 */
public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("test", "【老板】下达任务  dispatchTouchEvent：" + Util.actionToString(ev.getAction()) + "，找个人帮我完成，任务往下分发。");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean relust = false;
        Log.i("test", "【老板】完成任务  onTouchEvent：" + Util.actionToString(event.getAction()) + "，【经理】太差劲了，以后不再找你干活了，我自来搞定！是否解决：" + Util.canDoTaskTop(relust));
        return relust;
    }

}
