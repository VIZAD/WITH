package com.example.vizax.with.ui.userInformation;

import android.os.Handler;

import com.example.vizax.with.bean.BaseEmptyBean;
import com.example.vizax.with.bean.FollowBean;
import com.example.vizax.with.bean.Test;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.util.GsonUtil;
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
    public UserInforBean getUserInformation(String userId) {
        System.out.println("11111111111"+userId);
        //获取用户信息的json数据解析
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_GETUSERINFO ))
                .addParams("token","1")
                .addParams("aimUserId",userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                         mUserInforBean = GsonUtil.toListString(response,UserInforBean.class);

                    }
                });
        return mUserInforBean;
    }

    @Override
    public boolean follow(String userId) {
        //查询是否关注该用户
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITATION_CONCERNUSER))
                .addParams("token","1")
                .addParams("concernedUserId",userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        follow = false;
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        FollowBean followBean = GsonUtil.toListString(response,FollowBean.class);
                        if(followBean.getData().isIsConcerned())
                            follow = true;
                        else
                            follow = false;

                    }
                });
        return follow;
    }

    @Override
    public void getRecyclerViewData() {

    }

    @Override
    public void setUserAvatar(String avatarId, String url) {
        File f=new File(url);
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_UPLOADHEADPIC ))
                .addFile("file",avatarId,f)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onFailure(call,(IOException) e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BaseEmptyBean baseEmptyBean = GsonUtil.toString(response);
                        if(baseEmptyBean.getCode().equals("200")){
                            try {
                                callback.onResponse(null,null);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
    public void setCallback(Callback call){
        this.callback = call;
    }

}
