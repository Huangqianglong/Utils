package com.hql.netlib.result;

import com.google.gson.JsonObject;

import io.reactivex.Observer;


/**
 * @author ly-huangql
 * <br /> Create time : 2020/1/6
 * <br /> Description :
 */
public abstract class BaseSubscriber implements Observer<JsonObject> {
    public static final String RESULT_OK = "1";
    public static final String RESULT_ERROR = "0";
    public static final String ERROR_MSG = "";
    public static final String NO_MSG = "-1";
}
