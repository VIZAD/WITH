package com.example.vizax.with.ui.invitationList;

import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Young on 2016/9/16.
 */
public class InvitationModel implements InvitationContact.InvitationlModel {
    private InvitationRecyclerViewAdapter mAdapter;
    private InvitationBaseBean  mData;
    @Override
    public void getData(String typeId, String userId, String token,StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITATION_GETINVITATIONS))
               /* .addParams("typeId",typeId)
                .addParams("userId",userId)*/
                .addParams("token",token)
                .addParams("limit","10")
                .addParams("lastInvitationId","0")
                .build()
                .execute(stringCallback);

    }

    @Override
    public void addData(String finalItemId, String count,String token,StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITATION_GETINVITATIONS))
                .addParams("lastInvitaionId",finalItemId)
                .addParams("token",token)
                .addParams("limit",count)
                .build()
                .execute(stringCallback);

    }

    @Override
    public void deleteData(String token, String invitationId,StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITATION_DELETEINVITATION))
                .addParams("token",token)
                .addParams("invitaionId",invitationId)
                .build()
                .execute(stringCallback);
    }

    /*interface StopRefreshing{
        void stopRefreshing();
    }*/


}

