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

       /*Okhttp的post请求，传入url、参数、然后执行回调接口
        Okhttp结合回调写起来代码有点长
        可以试试Rxjava/RxAndroid+retrofit2,配合lambda表达式(大家有时间了再去学)
        可以写出高效整洁的代码*/
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
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
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
    public void attachView(@NonNull DemoContact.View View)   {
        demoView = View;
    }

    @Override
    public void detachView() {
        /*if(mSubscription!=null&&!mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }*/

        //当activity被销毁时，回收视图，避免OOM(内存泄漏)
        demoView = null;
    }
}
