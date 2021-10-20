package com.hql.netlib;


import com.hql.netlib.result.BaseResultSubscriber;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author ly-huangql
 * <br /> Create time : 2019/5/31
 * <br /> Description :
 */
public interface NetworkRequest<T extends BaseResultSubscriber> {
//    /**http请求
//     * @param headers
//     * @param jsonData
//     * @return
//     */
//    @POST("gw/gw")
//        //Observable<JsonObject> request(@FieldMap Map<String, Object> paraMap);
//        //Observable<JsonObject> request(@Query("type") String type, @Query("postid") String postid);
//        //Observable<JsonObject> request(@HeaderMap Map<String, String> headers, @Body Map<String, Object> paraMap);
//    Observable<JsonObject> request(@HeaderMap Map<String, String> headers, @Body RequestBody jsonData);
    /**http请求
     * @param headers
     * @param jsonData
     * @return
     */
    @POST("gw/gw")
    Observable<T> request(@HeaderMap Map<String, String> headers, @Body RequestBody jsonData);
}
