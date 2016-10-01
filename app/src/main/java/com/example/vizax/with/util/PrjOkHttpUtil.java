package com.example.vizax.with.util;

import android.content.Context;
import android.util.Log;

import com.example.vizax.with.App;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

/**
 * Created by prj on 2016/10/1.
 */

public class PrjOkHttpUtil {

    public static PostFormBuilder addToken(Context context){
        return OkHttpUtils.post().addParams("token",SharedUtil.getString(context,"token"));
    }
}
