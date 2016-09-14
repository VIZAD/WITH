package com.example.vizax.with.ui.demo;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;

/**
 * Created by Administrator on 2016/9/14.
 */

public interface DemoContact {

    interface View extends BaseView{
        void showLoading();

        void dimissLoading();

        void loginSuccess();

        void loginFailure(String Error);
    }

    interface Presenter extends BasePresenter<View>{
        void login(String username,String password);
    }

}
