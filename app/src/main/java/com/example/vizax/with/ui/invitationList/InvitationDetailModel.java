package com.example.vizax.with.ui.invitationList;

import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.constant.APIConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Young on 2016/9/16.
 */
public class InvitationDetailModel implements InvitationDetailContact.InvitationDetailModel{
    public InvitationDetailContact.ChangeBtn mChangeBtn;

    @Override
    public String getData(InvitationDetailContact.View view) {
        return null;
    }

    /**
     * 参与邀约的http请求
     * @param mInvitationBeen
     * @param type
     * @param changeBtn
     */
    @Override
    public void join(InvitationBean mInvitationBeen, String type, InvitationDetailContact.ChangeBtn changeBtn) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITAION_PARTICIPATEINVITATION))
                .addParams("invitationId",mInvitationBeen.getInvitaionId())
                .addParams("type",type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        mChangeBtn = changeBtn;
                        mChangeBtn.setSrc();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("erro!!!");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println("success!!!");
                    }
                });
    }



}
