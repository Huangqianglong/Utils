package com.hql.netlib;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ly-huangql
 * <br /> Create time : 2020/12/2
 * <br /> Description :
 */
public class BaseApi {


    /**
     * 发送导航信息
     */
    public static final String MINIPROGRAM_SEND_NAV_INFO = "ly.nissan.platform.navi.gps.rec";
    /**
     * 获取加油站信息
     */
    public static final String MINIPROGRAM_GET_OIL_INFO = "ly.nissan.platform.navi.gasstList";

    public static String getBaseURI() {
        return BuildConfig.BASE_URL;
    }

    public static String encodeMd5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            byte messageDigest[] = md5.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 网络请求header装箱
     *
     * @param api
     * @param bodyParam
     * @return
     */
    public static Map<String, String> getHeader(String api, String bodyParam) {
        String timestamp = System.currentTimeMillis() + "";
        String noncestr = UUID.randomUUID().toString().replace("-", "");//BuildConfig.NONCESTR;// UUID.randomUUID().toString().replace("-", "");
        String sign = BaseApi.encodeMd5(BuildConfig.APP_ID + api + bodyParam + noncestr + timestamp + BuildConfig.APP_KEY);
        Map<String, String> params = new HashMap<>();
        params.put("api", api);
        params.put("content-type", "application/json; charset=UTF-8");
        params.put("appid", BuildConfig.APP_ID);
        params.put("noncestr", noncestr);
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        LoggerUtil.d("hql", "timestamp:" + timestamp + "\n sign:" + sign);
        return params;
    }
}
