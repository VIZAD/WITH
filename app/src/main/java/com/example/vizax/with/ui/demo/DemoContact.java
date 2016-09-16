package com.example.vizax.with.ui.demo;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

/**
 * 创建一个公共的接口。将view层和presenter的接口囊括其中
 * 故只需一个接口就能实现所有操作
 * 这样的话所有接口操作的修改都在该类里面进行修改
 * Created by prj on 2016/9/14.
 */

public interface DemoContact {

    /*View接口，在activity实现该接口，实现对视图的操作
    以登陆界面来说，view层所涉及到的，大概有显示loading的dialog，关闭dialog
    或者显示账号密码为空之类的操作。这里不做详解，列举了dialog的相关操作
    可自己根据需要进行拓展*/
    interface View extends BaseView{
        void showLoading();

        void dimissLoading();

        void loginSuccess(String Msg);

        void loginFailure(String Error);
    }

    interface DemoIModle{
        void login(String username, String password,StringCallback stringCallback);
    }

    //Presenter操作接口，自己定义一个Presenter实现该接口
    interface Presenter extends BasePresenter<View>{
        void login(String username,String password);
    }
}
