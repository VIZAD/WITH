package com.example.vizax.with.ui.demo;

import android.support.annotation.NonNull;

import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.bean.User;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.util.GsonUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import rx.Subscription;

/**
 * Created by prj on 2016/9/14.
 */

public class DemoPresenter implements DemoContact.Presenter {

    private DemoContact.View demoView;
    /*RxBus,不需要可以不用
    private Subscription mSubscription;*/

    //构造方法，可传入自己所需参数
    public DemoPresenter(){

    }

    @Override
    public void login(String username, String password) {

        demoView.showLoading();

        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.USER_LOGIN))
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        demoView.showLoading();
                        demoView.loginFailure(e+"");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BaseBean<User> baseBean = GsonUtil.toString(response,BaseBean.class);
                        if (baseBean.getCode().equals("200"))
                            demoView.loginSuccess(response);
                        else
                            demoView.loginFailure(baseBean.getMsg());
                        demoView.dimissLoading();
                    }
                });
    }

    @Override
    public void attachView(@NonNull DemoContact.View View) {
        demoView = View;
    }

    @Override
    public void detachView() {
        /*if(mSubscription!=null&&!mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }*/
        demoView = null;
    }
}
