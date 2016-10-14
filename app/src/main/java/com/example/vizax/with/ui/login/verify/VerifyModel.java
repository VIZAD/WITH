package com.example.vizax.with.ui.login.verify;

import android.os.Build;

import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.ui.demo.DemoContact;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


public class VerifyModel implements VerifyContact.Modle {

    @Override
    public void verify(String usernum, String username, String sex, StringCallback stringCallback) {
        OkHttpUtils.get()
                .url(APIConstant.getApi(APIConstant.USER_REGISTERVERTIFICATION))
                .addParams("studentNumber", usernum)
                .addParams("realName", username)
                .addParams("sex", "" + sex)
                .addParams("token", " ")
                .build().execute(stringCallback);
    }

    @Override
    public void register(String userphone, String verifynum, String password, String studentnum, StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_REGISTER))
                .addParams("phone", userphone)
                .addParams("vertificationCode", verifynum)
                .addParams("password",  password)
                .addParams("studentNumber",studentnum)
                .addParams("token", " ")
                .build().execute(stringCallback);
    }
}
