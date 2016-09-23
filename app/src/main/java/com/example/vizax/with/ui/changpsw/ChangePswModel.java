package com.example.vizax.with.ui.changpsw;

import com.example.vizax.with.constant.APIConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by xujianbo on 2016/9/22.
 */
public class ChangePswModel implements ChangePswContact.changepswModel{


    @Override
    public void changepsw(String oldpassword, String newpassword, StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_CHANGPASSWORD ))
                .addParams("token","1")
                .addParams("oldPassword",oldpassword)
                .addParams("newPassword",newpassword)
                .build()
                .execute(stringCallback);
    }
}
