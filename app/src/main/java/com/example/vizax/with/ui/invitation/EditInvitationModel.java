package com.example.vizax.with.ui.invitation;

import com.example.vizax.with.App;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.util.PrjOkHttpUtil;
import com.example.vizax.with.util.SharedUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by hasee on 2016/9/22.
 */

public class EditInvitationModel implements EditInvitationContact.Modle{

    @Override
    public void editInvitation(String invitationId, String invitationTime, String content, String totalNumber,
                               boolean hiden, String sexRequire, String place,StringCallback stringCallback) {
        OkHttpUtils.get().addParams("token",SharedUtil.getString(App.instance,"token"))
                .url(APIConstant.getApi(APIConstant.INVITAION_ALTERINVITATION))
                .addParams("invitationId",invitationId)
                .addParams("invitationTime",invitationTime+":00")
                .addParams("content",content)
                .addParams("totalNumber",totalNumber)
                .addParams("hiden",hiden+"")
                .addParams("sexRequire",sexRequire)
                .addParams("place",""+place)
                .build()
                .execute(stringCallback);
    }

}
