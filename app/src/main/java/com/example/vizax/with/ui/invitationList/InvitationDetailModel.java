package com.example.vizax.with.ui.invitationList;

import com.example.vizax.with.App;
import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.util.SharedUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Young on 2016/9/16.
 */
public class InvitationDetailModel implements InvitationDetailContact.InvitationDetailModel{
    public InvitationDetailContact.ChangeBtn mChangeBtn;




    @Override
    public String getData(String token, String typeId, String userId, String lastInvitatioonId, String limit) {
      return null;
    }

    /**
     * 参与邀约的http请求
     * @param mInvitationBeen
     * @param type
     */
    @Override
    public void join(InvitationBean mInvitationBeen, String type, StringCallback stringCallback) {
        System.out.println("id="+mInvitationBeen.getInvitaionId()+type);
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITAION_PARTICIPATEINVITATION))
                .addParams("invitaionId",mInvitationBeen.getInvitaionId())
                .addParams("token", SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("type",type)
                .build()
                .execute(stringCallback);
    }



}
