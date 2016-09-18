package com.example.vizax.with.ui.home;

import com.example.vizax.with.constant.APIConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Administrator on 2016/9/17.
 */
public class HomeModel implements HomeContact.Modle {
    @Override
    public void loadHomeData(String token, int typeId, int userId, int lastInvitationId, int limit, StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.HOME_INVITATION_GETINVITATIONS))
                .addParams("token",token)
                .addParams("lastInvitationId",lastInvitationId+"")
                .addParams("limit",limit+"")
                .build()
                .execute(stringCallback);
    }
}
