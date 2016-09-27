package com.example.vizax.with.ui.login.login;

import android.support.annotation.NonNull;

import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.bean.Test;

import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.ui.login.User;
import com.example.vizax.with.ui.login.bean.UserBean;
import com.example.vizax.with.util.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by prj on 2016/9/14.
 */

public class LoginPresenter implements LoginContact.Presenter {

    private LoginContact.View mLoginView;
    private LoginModel mLoginModel;
    /*RxBus,不需要可以不用
    private Subscription mSubscription;*/

    //构造方法，可传入自己所需参数
    public LoginPresenter() {
        mLoginModel = new LoginModel();
    }

    @Override
    public void login(String username, String password) {

        mLoginView.showLoading();


        mLoginModel.login(username, password, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mLoginView.dimissLoading();
                mLoginView.loginFailure(e + "");
            }

            @Override
            public void onResponse(String response, int id) {
                UserBean lUserBean = GsonUtil.toString(response, UserBean.class);
                /*
                Gson lGson = new Gson();
                java.lang.reflect.Type type = new TypeToken<UserBean>() {}.getType();
                lGson.fromJson(response, type);
                */
                //baseBean对象，保存token
                if (lUserBean.getCode() == 200) {
                    mLoginView.loginSuccess(response);
                    UserBean.DataBean data = lUserBean.getData();
                    User.phone = data.getPhone();
                    User.sex = data.getSex();
                    User.unReadedNumber = data.getUnReadedNumber();
                    User.token = data.getToken();
                    User.nickName = data.getNickName();
                    User.userUrl = data.getUserUrl();
                    User.userId = data.getUserId();
                    User.classX = data.getClassX();
                    User.studentID = data.getStudentID();
                    User.realName = data.getRealName();
                    User.qq = data.getQq();
                } else
                    mLoginView.loginFailure(lUserBean.getMsg());
                mLoginView.dimissLoading();
            }
        });
    }

    @Override
    public void attachView(@NonNull LoginContact.View View) {
        mLoginView = View;
    }


    @Override
    public void detachView() {
        //当activity被销毁时，回收视图，避免OOM(内存泄漏)
        mLoginView = null;
    }
}