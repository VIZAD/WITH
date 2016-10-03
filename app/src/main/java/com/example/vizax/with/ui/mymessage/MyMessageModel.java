package com.example.vizax.with.ui.mymessage;

import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.util.SharedUtil;
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

    @Override
    public void deleteMessage(String token,int messageId, StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_DELETEMESSAGE))
                .addParams("token", token)
                .addParams("messageId",messageId+"")
                .build()
                .execute(stringCallback);

    }

    @Override
    public void readMessage(String token, int messageId, StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_READMESSAGE))
                .addParams("token", token)
                .addParams("messageId",messageId+"")
                .build()
                .execute(stringCallback);
    }

    @Override
    public void agreeMessage(String token, int messageId,boolean isAccept,int invitationId, int applyUserId,StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_ACCEPTMESSAGE))
                .addParams("token", token)
                .addParams("applyUserId", applyUserId+"")
                .addParams("isAccept", isAccept+"")
                .addParams("messageId",messageId+"")
                .addParams("invitationId",invitationId+"")
                .build()
                .execute(stringCallback);
    }



}
