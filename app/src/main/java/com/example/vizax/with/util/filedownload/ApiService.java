package com.example.vizax.with.util.filedownload;

import android.database.Observable;
import android.graphics.Canvas;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by prj on 2016/9/26.
 */

interface ApiService {

    @Streaming
    @GET("")
    Call<ResponseBody> downloadWithDynamicUrl();
}
