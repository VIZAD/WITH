package com.example.vizax.with.ui.demo;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.bean.BaseEmptyBean;
import com.example.vizax.with.bean.Test;
import com.example.vizax.with.bean.User;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by prj on 2016/9/14.
 */

public class DemoPresenter implements DemoContact.Presenter {

    private DemoContact.View demoView;
    private DemoModel demoModel;
    /*RxBus,不需要可以不用
    private Subscription mSubscription;*/

    //构造方法，可传入自己所需参数
    public DemoPresenter(){
        demoModel = new DemoModel();
    }

    @Override
    public void login(String username, String password) {

        demoView.showLoading();

        //大家装个插件  <GsonFormat>  用json直接生成bean类。。

        //获取list的json数据解析
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITATION_GETCONCERNEDUSERS ))
                .addParams("token","111")
                .addParams("typeId","111")
                .addParams("userId","111")
                .addParams("lastInvitationId","111")
                .addParams("limit","111")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //获取的data如果是list类型的话，调用该方法。否则调用下面的方法
                        Test test = GsonUtil.toListString(response,Test.class);
                        //Log.w("haha",test.getData().get(0).getHeadUrl());

                        //获取的data如果只有msg和code，用下面的解析
                        /*BaseEmptyBean baseEmptyBean = GsonUtil.toString(response);
                        Log.w("haha",baseEmptyBean.getMsg());*/
                    }
                });

        /*Okhttp的post请求，传入url、参数、然后执行回调接口
        Okhttp结合回调写起来代码有点长
        可以试试Rxjava/RxAndroid+retrofit2,配合lambda表达式(大家有时间了再去学)
        可以写出高效整洁的代码*/
        demoModel.login(username, password, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                demoView.dimissLoading();
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
