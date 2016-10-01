package com.example.vizax.with.util;

import android.util.Log;

import com.example.vizax.with.App;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

/**
 * Created by prj on 2016/10/1.
 */

public class PrjOkHttpUtil {

    public static PostFormBuilder addToken(){
        Log.w("haha",SharedUtil.getString(App.instance,"token"));
        return OkHttpUtils.post().addParams("token",SharedUtil.getString(App.instance,"token"));
    }
}
