package com.example.vizax.with.ui.userInformation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.vizax.with.bean.BaseEmptyBean;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.ui.invitationList.InvitationContact;
import com.example.vizax.with.ui.invitationList.InvitationPresenter;
import com.example.vizax.with.util.FileUtil;
import com.example.vizax.with.util.FilesUtil;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.UUIDUtil;
import com.facebook.common.file.FileUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
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

    public static String destFileDir = Environment.getExternalStorageDirectory()
            .getAbsolutePath()+ File.separator+"pic";

    @Override
    public void setAvatar(String url) {

        mUserInfoView.showUploadDialog();
        mUserInfoModuel.setUserAvatar(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mUserInfoView.upLoadFailure();
                mUserInfoView.dimissUploadDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                mUserInfoView.upLoadSuccess();
                mUserInfoView.dimissUploadDialog();
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
