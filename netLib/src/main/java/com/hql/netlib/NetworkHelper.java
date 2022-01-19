package com.hql.netlib;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hql.common.LoggerUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ly-huangql
 * <br /> Create time : 2020/12/2
 * <br /> Description :
 */
public class NetworkHelper {
    private final static String TAG = "NetworkHelper" + "hql";
    private final static boolean DEBUG = false;
    /**
     * 设置连接超时的值
     */
    private static final int TIMEOUT = 10;
    private static final int TIMEOUT_DELAY = 20;

    /**
     //声明NetworkManager对象
     */
    private volatile static NetworkHelper mNetworkHelper;

    private static OkHttpClient mOkHttpClient;

    public static void initHelper(Context context) {
        if (null != mOkHttpClient) {
            return;
        }
        File cacheDir = new File(context.getCacheDir().getAbsoluteFile(), "networkCache");
        //实例化okhttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置基本配置
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT_DELAY, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT_DELAY, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.cache(new Cache(cacheDir, 10 * 1024 * 1024));
        builder.addInterceptor(new CustomInterceptor());
        mOkHttpClient = builder.build();
    }

    public static <T> T createApi(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                //基本网络地址
                .baseUrl(BaseApi.getBaseURI())
                .client(mOkHttpClient)
                //.addConverterFactory(ScalarsConverterFactory.create())//如果网络访问返回的是json字符串，使用gson转换器
                //.addConverterFactory(new NullOnEmptyConverterFactory())
                //此处顺序不能和上面对调，否则不能同时兼容普通字符串和Json格式字符串
                .addConverterFactory(GsonConverterFactory.create())
                //为了支持rxjava,需要添加下面这个把 Retrofit 转成RxJava可用的适配类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    static class CustomInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
//                    .addHeader("Content-Type", "application/json; charset=UTF-8")
//                    .addHeader("Connection", "keep-alive")
//                    .addHeader("Accept", "*/*")
//                    .header("Cache-Control", String.format("public, max-age=%d", 60))
                    .build();

            String method = request.method();
            String requestValue = "";
            String api = "";
            if ("POST".equals(method)) {
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        if (body.encodedName(i).equals("api")) {
                            api = body.encodedValue(i);
                        }
                        requestValue += body.name(i) + " = " + body.value(i) + "\n";
                    }
                }
                if (request.body() instanceof RequestBody) {
                    requestValue = printParams(request.body());
                }

            }
            if (DEBUG) {
               LoggerUtil.d(TAG, "hql 网络请求 headers :" + "\n" + request.headers().toString());
                LoggerUtil.d(TAG, "hql 网络请求 body :" + "\n" + requestValue);
            }
            Response response = chain.proceed(request);
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            if (DEBUG) {
                LoggerUtil.d(TAG, "网络返回" + api + "\n" + content);
            }
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    }

    private static String printParams(RequestBody body) {
        Buffer buffer = new Buffer();
        String params = null;
        try {
            body.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset();
            }
            params = buffer.readString(charset);
            // LoggerUtil.e(TAG, "请求参数： | " + params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return params;
    }

    public static NetworkHelper getInstance() {
        if (mNetworkHelper == null) {
            synchronized (NetworkHelper.class) {
                if (mNetworkHelper == null) {
                    mNetworkHelper = new NetworkHelper();
                }
            }
        }
        return mNetworkHelper;
    }

//    public void doHttpRequest(Map<String, String> headers, String body, Observer<JsonObject> pSubscriber) {
//        //io.reactivex.Observable observable
//        Observable<JsonObject> observable = createApi(NetworkRequest.class).request(headers,
//                RequestBody.create(MediaType.parse("text/plain"), body)
//        );
//        observable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .retry(2)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(pSubscriber)
//        ;
//        //observable.subscribe(pSubscriber);
//    }
    public<J extends Observer<JsonObject>> void doHttpRequest(Map<String, String> headers, String body, J pSubscriber) {
        //io.reactivex.Observable observable
        Observable<JsonObject> observable = createApi(NetworkRequest.class).request(headers,
                RequestBody.create(MediaType.parse("text/plain"), body)
        );
        observable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pSubscriber)
        ;
        //observable.subscribe(pSubscriber);
    }
}
