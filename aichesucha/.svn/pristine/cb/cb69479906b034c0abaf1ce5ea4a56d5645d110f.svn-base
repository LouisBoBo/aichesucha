package com.car.scth.mvp.mapapi;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.car.scth.mvp.mapapi.helper.NullStringToEmptyAdapterFactory;
import com.car.scth.mvp.model.api.Api;

/**
 * Created by ljb on 2017/5/16.
 */

public class NetworkMapManager {
    private static Retrofit mRetrofit;
    private static boolean isDebug = false;//是否输出日志addCallAdapterFactory

    public static Retrofit getInstance(Context context) {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Api.HOST_MAP)
                    .client(genericClient())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return mRetrofit;
        } else {
            return mRetrofit;
        }
    }

    public static OkHttpClient genericClient() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        final String date = dateFormat.format(currentTime);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        /**
         * 添加请求头信息
         * 判断网络连接是否正常，网络连接成功，则请求网络，网络连接失败，调用缓存
         * */
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                if (!com.blankj.utilcode.util.NetworkUtils.isConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();

                } else { // 请求头部
                    request = chain.request()
                            .newBuilder()
                            .addHeader("Accept", "application/json")
                            .build();
                }
                return chain.proceed(request);
            }

        });
        if (isDebug)
            //添加请求拦截器
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                    Request request = chain.request();
                    Log.e("request", String.format("发送请求: %s",
                            request.url()));
                    if (request.body() != null) {
                        if (request.body() instanceof FormBody) {
                            StringBuilder body = new StringBuilder("{");
                            int paramNum = ((FormBody) request.body()).size();
                            for (int i = 0; i < paramNum; i++) {
                                String name = ((FormBody) request.body()).name(i);
                                String value = ((FormBody) request.body()).value(i);
                                body.append(name)
                                        .append("=")
                                        .append(value);
                                if (i != paramNum - 1) {
                                    body.append(",");
                                }
                            }
                            body.append("}");
                            Log.e("request", String.format("发送请求: %s\n参数：%s",
                                    request.url(), body.toString()));
                        } else {
                            Log.e("request", String.format("发送请求: %s",
                                    request.url()));
                        }
                    }

                    return chain.proceed(request);
                }
            });
        //添加响应结果拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (TextUtils.isEmpty(message)) return;
                String s = message.substring(0, 1);
                // 如果收到想响应是json才打印
                if ("{".equals(s) || "[".equals(s)) {
                    Log.e("response", "收到响应: " + message);
                }
            }
        });
        if (isDebug) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(interceptor);
            httpClientBuilder.addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE);
        }
        return httpClientBuilder.build();
    }

    public static class NetworkInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //没有网络时，使用缓存
            if (!com.blankj.utilcode.util.NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (com.blankj.utilcode.util.NetworkUtils.isConnected()) {
                //有网络时设置缓存超时时间1分
                int maxAge = 0;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                //无网络时，设置缓存超时为1周
                int maxStale = 60 * 60 * 24 * 7;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, no-cached, max-stale=" + maxStale)
                        .build();
            }

            return response;
        }
    }

    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!com.blankj.utilcode.util.NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached")
                        .build();
            }
            return chain.proceed(request);
        }
    };
}
