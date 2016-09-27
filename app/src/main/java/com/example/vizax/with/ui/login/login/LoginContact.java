package com.example.vizax.with.ui.login.login;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * 创建一个公共的接口。将view层和presenter的接口囊括其中
 * 故只需一个接口就能实现所有操作
 * 这样的话所有接口操作的修改都在该类里面进行修改
 * Created by prj on 2016/9/14.
 */

public interface LoginContact {

    interface View extends BaseView{
        void showLoading();

        void dimissLoading();

        void loginSuccess(String Msg);

        void loginFailure(String Error);
    }

    interface Modle{
        void login(String username, String password, StringCallback stringCallback);
    }

    interface Presenter extends BasePresenter<View>{
        void login(String username, String password);
    }
}
