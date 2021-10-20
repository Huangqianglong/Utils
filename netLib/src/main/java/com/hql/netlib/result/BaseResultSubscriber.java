package com.hql.netlib.result;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hql.netlib.LoggerUtil;

import java.lang.reflect.Type;

import io.reactivex.disposables.Disposable;

/**
 * @author ly-huangql
 * <br /> Create time : 2019/5/31
 * <br /> Description :
 */
public abstract class BaseResultSubscriber<T extends BaseResultBean> extends BaseSubscriber {
    Gson mGson = new Gson();
    Disposable d;
    T mResult;
    @Override
    public void onError(Throwable e) {
        LoggerUtil.d("hql >>Throwable>" + e.toString());
        e.printStackTrace();
        //onError(RESULT_ERROR, ERROR_MSG);
        d.dispose();
    }

    @Override
    public void onComplete() {
        d.dispose();
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(JsonObject jsonObject) {
        String result = RESULT_ERROR;
        String msg = NO_MSG;
        try {
            result = jsonObject.get("result").getAsString();
            msg = jsonObject.get("msg").getAsString();
        } catch (Exception e) {
            LoggerUtil.d("hql >onNext>Exception jsonObject>" + jsonObject.toString());
            e.printStackTrace();
            onError(RESULT_ERROR, NO_MSG);
        }
        if (RESULT_OK.equals(result)) {
            //mGson.fromJson(result, (Type)mResult);
            onSuccess(mGson.fromJson(result, (Type)mResult));
        } else {
            LoggerUtil.d("hql >onNext> erro jsonObject>" + jsonObject.toString());
            onError(RESULT_ERROR, msg);
        }
    }

    protected abstract void onSuccess(T result);

    /**
     * @param result
     * @param errorMsg 空 ：无网络 -1 有网络查询返回但是抛出出错
     */
    protected abstract void onError(String result, String errorMsg);
}
