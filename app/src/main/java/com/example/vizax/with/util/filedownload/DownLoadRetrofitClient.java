package com.example.vizax.with.util.filedownload;

import com.cm.retrofit2.converter.file.FileConverterFactory;
import com.cm.retrofit2.converter.file.body.HttpClientHelper;
import com.cm.retrofit2.converter.file.body.ProgressResponseListener;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by prj on 2016/9/26.
 */

public class DownLoadRetrofitClient {

    private static final String HOST = "http://fir.im/";

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(FileConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    /**
     * 创建带响应进度(下载进度)回调的service
     */
    public static <T> T createResponseService(Class<T> tClass, String url, ProgressResponseListener listener) {
        //通过HttpClientHelper获得已经添加了自定义ResponseBody的OkHttpClient
        OkHttpClient client = HttpClientHelper.addProgressResponseListener(new OkHttpClient.Builder(), listener).build();
        return builder
                .baseUrl(url)
                .client(client)
                .build()
                .create(tClass);
    }
}
