package com.example.vizax.with.ui.login;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.example.vizax.with.ui.login.bean.UserBean;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Administrator on 2016/10/1.
 */
public interface MainContact {
    interface View extends BaseView {
        void startActivity();
    }

    interface Modle{
    }

    interface Presenter extends BasePresenter<View> {
        boolean isFirstLogin();
        boolean isHadLogin();
    }
}
