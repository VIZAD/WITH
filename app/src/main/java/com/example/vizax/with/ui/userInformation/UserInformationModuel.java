package com.example.vizax.with.ui.userInformation;

import android.os.Handler;
import android.util.Log;

import com.example.vizax.with.App;
import com.example.vizax.with.bean.BaseEmptyBean;
import com.example.vizax.with.bean.FollowBean;
import com.example.vizax.with.bean.Test;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.util.FilesUtil;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.PrjOkHttpUtil;
import com.example.vizax.with.util.SharedUtil;
import com.example.vizax.with.util.UUIDUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.security.PrivilegedAction;

import okhttp3.Call;
import okhttp3.Callback;


/**
 * Created by Young on 2016/9/19.
 */
public class UserInformationModuel implements UserInformationContact.Moduel {
    private UserInforBean mUserInforBean;
    public Callback callback;
    private  boolean follow;

    @Override
    public void getUserInformation(String userId,String invitationId,StringCallback stringCallback) {

        //获取用户信息的json数据解析
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_GETUSERINFO ))
                .addParams("token",SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("aimUserId",userId)
                .addParams("invitationId",invitationId)
                .build()
                .execute(stringCallback);
       // System.out.println(mUserInforBean.getMsg()+mUserInforBean.getData().getStudentId());
    }

    @Override
    public void follow(String userId,StringCallback stringCallback) {
        //查询是否关注该用户
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITATION_CONCERNUSER))
                .addParams("token",SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("concernedUserId",userId)
                .build()
                .execute(stringCallback);
    }

    @Override
    public void getRecyclerViewData() {

    }

    @Override
    public void setUserAvatar(String url,StringCallback stringCallback) {

        String fileName = UUIDUtil.createUUID()+ "max.jpg";
        File f=new File(url);
        PrjOkHttpUtil.addToken()
                .url(APIConstant.getApi(APIConstant.USER_UPLOADHEADPIC ))
                .addFile("file",fileName,f)
                .build()
                .execute(stringCallback);
    }

    public void setCallback(Callback call){
        this.callback = call;
    }

}
