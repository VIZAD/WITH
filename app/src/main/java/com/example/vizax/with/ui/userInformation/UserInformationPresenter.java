package com.example.vizax.with.ui.userInformation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.ui.invitationList.InvitationContact;
import com.example.vizax.with.ui.invitationList.InvitationPresenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Young on 2016/9/19.
 */
public class UserInformationPresenter  implements UserInformationContact.Presenter {
    private UserInformationActivity mUserInfoView;
    private UserInformationModuel mUserInfoModuel;
    private UserInforBean mUserInforBean;
    private boolean follow;

    @Override
    public void setAvatar(String avatarId,String url) {
            mUserInfoModuel.setUserAvatar(avatarId,url);
            mUserInfoModuel.setCallback(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mUserInfoView.upLoadFailure();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    mUserInfoView.upLoadSuccess();
                }
            });

    }

    @Override
    public boolean follow(String userId) {
       follow =  mUserInfoModuel.follow(userId);
        return follow;
    }


    @Override
    public void attachView(@NonNull UserInformationActivity View) {
        mUserInfoView = View;
        mUserInfoModuel = new UserInformationModuel();
    }

    @Override
    public void detachView() {

    }
}
