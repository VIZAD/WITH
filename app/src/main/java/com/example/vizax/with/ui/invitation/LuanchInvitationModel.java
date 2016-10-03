package com.example.vizax.with.ui.invitation;

import com.example.vizax.with.App;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.util.SharedUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by hasee on 2016/9/22.
 */

public class LuanchInvitationModel implements LuanchInvitationContact.Modle{
    @Override
    public void commit(String token, String type, String titletext, String descriptiontext, String sex, String datetime, String site, String upper, Boolean hidenBoolean, StringCallback stringCallback) {
        System.out.println(token+"  "+type+"  "+titletext+"  "+descriptiontext+"  "+sex+"  "+datetime+"  "+site+"  "+upper+"  "+hidenBoolean);
        OkHttpUtils.get()
                .url(APIConstant.getApi(APIConstant.INVITATION_PUBLISHINVITATION))
                .addParams("token", SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("invitationTime",datetime+":00")
                .addParams("place",site)
                .addParams("totalNumber",upper)
                .addParams("title",titletext)
                .addParams("content",descriptiontext)
                .addParams("sexRequire",sex)
                .addParams("type",type)
                .addParams("hiden",""+hidenBoolean)
                .build()
                .execute(stringCallback);
    }
}
