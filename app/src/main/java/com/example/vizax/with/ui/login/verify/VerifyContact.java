package com.example.vizax.with.ui.login.verify;

import android.os.Handler;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.zhy.http.okhttp.callback.StringCallback;


public interface VerifyContact {

    interface View extends BaseView {
        void showLoading();

        void dimissLoading();

        void showDialog(String phone);

        void verifySuccess(String Msg);

        void verifyFailure(String Error);

        /***************
         * 注册模块
         ******************/
        void showRegLoading();

        void dimissRegLoading();

        void regVerifySuccess(String Msg);

        void regVerigyFailure(String Msg);

        void RegSuccess(String Msg);

        void RegFailure(String Error);
    }

    interface Modle {
        void verify(String usernum, String username, String sex, StringCallback stringCallback);

        void register(String userphone, String verifynum, String password, String studentnum, StringCallback stringCallback);
    }

    //Presenter操作接口，自己定义一个Presenter实现该接口
    interface Presenter extends BasePresenter<View> {
        void verify(String usernum, String username, String sex);

        void register(String userphone, String verifynum, String password, String studentnum);

        void msgRegister(Handler handler, String phone);
    }
}
