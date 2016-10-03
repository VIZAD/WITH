package com.example.vizax.with.ui.invitationList;

import com.example.vizax.with.App;
import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.SharedUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Young on 2016/9/16.
 */
public class InvitationModel implements InvitationContact.InvitationlModel {
    private InvitationRecyclerViewAdapter mAdapter;
    private InvitationBaseBean  mData;
    @Override
    public void getData(String typeId, String userId,StringCallback stringCallback) {
        //System.out.println("type="+typeId+"userId="+userId);

        PostFormBuilder builder =OkHttpUtils .post()
                .url(APIConstant.getApi(APIConstant.INVITATION_GETINVITATIONS));
        if(typeId != null&& !typeId.equals("")){
            builder. addParams("typeId",typeId);
        }
        if(userId != null&& !userId.equals("")){
            builder.addParams("userId",userId);
        }


        builder .addParams("token",SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("limit","10")
                .addParams("lastInvitationId","0")
                .build()
                .execute(stringCallback);

    }

    @Override
    public void addData(String finalItemId, String count,StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITATION_GETINVITATIONS))
                .addParams("lastInvitationId",finalItemId)
                .addParams("token", SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("limit",count)
                .build()
                .execute(stringCallback);

    }

    @Override
    public void deleteData( String invitationId,StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITATION_DELETEINVITATION))
                .addParams("token",SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("invitaionId",invitationId)
                .build()
                .execute(stringCallback);
    }

    /*interface StopRefreshing{
        void stopRefreshing();
    }*/


}

