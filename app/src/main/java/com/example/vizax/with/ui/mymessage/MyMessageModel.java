package com.example.vizax.with.ui.mymessage;

import com.example.vizax.with.constant.APIConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Xuhai on 2016/9/23.
 */

public class MyMessageModel implements MyMessageContact.Modle{

    @Override
    public void loadMyMessageData(String token, int lastMessageId, int limit, StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_GETMESSAGES))
                .addParams("token",token)
                .addParams("lastMessageId",lastMessageId+"")
                .addParams("limit",limit+"")
                .build()
                .execute(stringCallback);
    }

}
