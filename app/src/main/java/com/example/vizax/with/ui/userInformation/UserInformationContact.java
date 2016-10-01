package com.example.vizax.with.ui.userInformation;

import android.content.Context;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.ui.invitationList.InvitationContact;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Callback;

/**
 * Created by Young on 2016/9/19.
 */
public interface UserInformationContact {
    interface View extends BaseView{
        void setInfomation(boolean type);
        //修改头像
        void changeUserAvatar();
        //修改密码
        void changePassword();
        //修改头像成功
        void upLoadSuccess();
        //修改头像失败
        void upLoadFailure();

        void showUploadDialog();

        void dimissUploadDialog();

        Context getContext();

    }
    interface Moduel  {
        UserInforBean getUserInformation(String userId);
        //关注用户
        boolean follow(String userId);
        //http请求取用户最近活动的数据
        void getRecyclerViewData();
        //上传用户新头像
        void setUserAvatar(String url, StringCallback stringCallback);
    }
    interface Presenter extends BasePresenter<UserInformationActivity>{

        //设置头像
        void setAvatar(String url);
        //关注用户
        boolean follow(String userId);

    }
}
