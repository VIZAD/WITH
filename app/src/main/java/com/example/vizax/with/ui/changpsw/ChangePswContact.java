package com.example.vizax.with.ui.changpsw;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by xujianbo on 2016/9/22.
 *
 */
public interface ChangePswContact {



        interface View extends BaseView {
            void changepswSuccess(String Msg);

            void changepswFailure(String Error);

            void showLoading();

            void dimissloading();

            void passwordsetError();

        }

        interface changepswModel {
            void changepsw(String oldpassword, String newpassword, StringCallback stringCallback);
        }

        interface changepswPresent extends BasePresenter<View> {
            void changepsw(String oldpassword, String newpassword);
        }

}