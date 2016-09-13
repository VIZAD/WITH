package com.example.vizax.with;

import android.app.Application;

import com.anupcowkur.reservoir.Reservoir;
import com.example.vizax.with.util.HttpLoggingInterceptor;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by prj on 2016/8/15.
 */

public class MyApplication extends Application {

    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 8;
    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;
    private OkHttpClient mOkHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        initReservoir();
        initOkHttpClient();
        initFrescoConfig();
    }

    private void initReservoir() {
        try {
            Reservoir.init(this, CACHE_DATA_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        mOkHttpClient = builder.build();
    }

    private void initFrescoConfig() {
        final MemoryCacheParams bitmapCacheParams =
                new MemoryCacheParams(MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                        Integer.MAX_VALUE,                     // Max entries in the cache
                        MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                        Integer.MAX_VALUE,                     // Max length of eviction queue
                        Integer.MAX_VALUE);

        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this,mOkHttpClient)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setBitmapMemoryCacheParamsSupplier(()->bitmapCacheParams)
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(this).setMaxCacheSize(MAX_DISK_CACHE_SIZE).build())
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this,config);
    }

}
