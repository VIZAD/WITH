package com.example.vizax.with.ui.changpsw;

import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.util.PrjOkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by xujianbo on 2016/9/22.
 */
public class ChangePswModel implements ChangePswContact.changepswModel{


    @Override
    public void changepsw(String oldpassword, String newpassword, StringCallback stringCallback) {
        PrjOkHttpUtil.addToken()
                .url(APIConstant.getApi(APIConstant.USER_CHANGPASSWORD ))
                .addParams("oldPassword",oldpassword)
                .addParams("newPassword",newpassword)
                .build()
                .execute(stringCallback);
    }
}
