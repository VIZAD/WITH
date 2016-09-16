package com.example.vizax.with.ui.demo;

import com.example.vizax.with.constant.APIConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import okhttp3.Call;

/**
 * API有三个类型。可以分成三个MODEL模块，一个人把所有接口都写完，那么就不用
 * Created by prj on 2016/9/16.
 */

public class DemoModel implements DemoContact.DemoIModle{

    @Override
    public void login(String username, String password, StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_LOGIN))
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(stringCallback);
    }
}
