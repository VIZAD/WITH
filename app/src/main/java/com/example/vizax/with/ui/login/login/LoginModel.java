package com.example.vizax.with.ui.login.login;

import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.ui.demo.DemoContact;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class LoginModel implements LoginContact.Modle{

    @Override
    public void login(String username, String password, StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_LOGIN))
                .addParams("phone",username)
                .addParams("password",password)
                .addParams("token"," ")
                .build()
                .execute(stringCallback);
    }
}
