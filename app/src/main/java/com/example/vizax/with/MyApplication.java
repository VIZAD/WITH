package com.example.vizax.with;

import android.app.Application;

import com.anupcowkur.reservoir.Reservoir;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by prj on 2016/8/15.
 */

public class MyApplication extends Application {

    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;

    @Override
    public void onCreate() {
        super.onCreate();
        initReservoir();
        initOkHttpClient();
    }

    private void initReservoir() {
        try {
            Reservoir.init(this, CACHE_DATA_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("WITH"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .writeTimeout(10000L,TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

}
