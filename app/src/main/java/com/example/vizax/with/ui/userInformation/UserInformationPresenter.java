package com.example.vizax.with.ui.userInformation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.vizax.with.App;
import com.example.vizax.with.EventBus.UserInfoMessage;
import com.example.vizax.with.R;
import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.bean.BaseEmptyBean;
import com.example.vizax.with.bean.FollowBean;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.ui.invitationList.InvitationContact;
import com.example.vizax.with.ui.invitationList.InvitationPresenter;
import com.example.vizax.with.util.FileUtil;
import com.example.vizax.with.util.FilesUtil;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.SharedUtil;
import com.example.vizax.with.util.UUIDUtil;
import com.facebook.common.file.FileUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

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
    public boolean follow;

    public static String destFileDir = Environment.getExternalStorageDirectory()
            .getAbsolutePath()+ File.separator+"pic";

    @Override
    public void setAvatar(String url) {
Log.e("url",url);
        mUserInfoView.showUploadDialog();
        mUserInfoModuel.setUserAvatar(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mUserInfoView.upLoadFailure();
                mUserInfoView.dimissUploadDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                //Log.w("haha!!!!",response);
                Log.w("haha",response);
                BaseBean baseBean = GsonUtil.toString(response,BaseBean.class);
                if(baseBean.getCode().equals("200")) {
                    SharedUtil.putString(App.instance, FieldConstant.userUrl,baseBean.getData().toString());
                    EventBus.getDefault().post(new UserInfoMessage(baseBean.getData().toString()));
                    mUserInfoView.upLoadSuccess();
                }
                mUserInfoView.dimissUploadDialog();
            }
        });

    }

    @Override
    public void follow(String userId) {
        mUserInfoModuel.follow(userId, new StringCallback() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if(follow){
                   mUserInfoView.concerned();
                }else {
                   mUserInfoView.disConcerned();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                follow = false;
            }

            @Override
            public void onResponse(String response, int id) {
                FollowBean followBean = GsonUtil.toListString(response,FollowBean.class);
                if(followBean.getData().isConcerned()) {
                    follow = true;
                    System.out.println("true");
                }
                else {
                    follow = false;
                    System.out.println("false");
                }
            }
        });
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
