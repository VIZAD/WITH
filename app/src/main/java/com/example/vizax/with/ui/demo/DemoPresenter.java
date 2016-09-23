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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

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
    public DemoPresenter() {
        demoModel = new DemoModel();
    }


    public static String encryptToSHA(String info) {
        byte[] digesta = null;
        try {
// 得到一个SHA-1的消息摘要
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
// 添加要进行计算摘要的信息
            alga.update(info.getBytes());
// 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
// 将摘要转为字符串
        String rs = byte2hex(digesta);
        return rs;// + key;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }
    @Override
    public void login(String username, String password) {

        demoView.showLoading();
        String appid = "A6929971395350";
        String uz = "UZ";
        String appkey = "85D15BCA-0AB4-D173-3C2A-94DDD6EB7BC0";
        long now = new Date().getTime();
        String sha1 = encryptToSHA(appid + uz + appkey + uz + now)+"."+now;
        System.out.println(sha1);
        OkHttpUtils.post()
                .url("https://d.apicloud.com/mcm/api/message")//APIConstant.getApi(APIConstant.USER_LOGIN)
                .addParams("msg", "aaaa1")
                .addParams("msg_type", "1")
                .addHeader("X-APICloud-AppId", "A6929971395350")
                .addHeader("X-APICloud-AppKey", sha1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        demoView.dimissLoading();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.w("test", response);;
                        demoView.dimissLoading();
                    }
                });


        //大家装个插件  <GsonFormat>  用json直接生成bean类。。

        //获取list的json数据解析
//        OkHttpUtils.post()
//                .url(APIConstant.getApi(APIConstant.INVITATION_GETCONCERNEDUSERS ))
//                .addParams("token","111")
//                .addParams("typeId","111")
//                .addParams("userId","111")
//                .addParams("lastInvitationId","111")
//                .addParams("limit","111")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        //获取的data如果是list类型的话，调用该方法。否则调用下面的方法
//                        Test test = GsonUtil.toListString(response,Test.class);
//                        //Log.w("haha",test.getData().get(0).getHeadUrl());
//
//                        //获取的data如果只有msg和code，用下面的解析
//                        /*BaseEmptyBean baseEmptyBean = GsonUtil.toString(response);
//                        Log.w("haha",baseEmptyBean.getMsg());*/
//                    }
//                });

        /*Okhttp的post请求，传入url、参数、然后执行回调接口
        Okhttp结合回调写起来代码有点长
        可以试试Rxjava/RxAndroid+retrofit2,配合lambda表达式(大家有时间了再去学)
        可以写出高效整洁的代码*/
//        demoModel.login(username, password, new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                demoView.dimissLoading();
//                demoView.loginFailure(e+"");
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                /*Gson解析已经封装，下次把User改成自己对应的Bean即可
//                  默认状态码200为成功*/
//                BaseBean<User> baseBean = GsonUtil.toString(response,BaseBean.class);
//                if (baseBean.getCode().equals("200"))
//                    demoView.loginSuccess(response);
//                else
//                    demoView.loginFailure(baseBean.getMsg());
//                demoView.dimissLoading();
//            }
//        });
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

        //当activity被销毁时，回收视图，避免OOM(内存泄漏)
        demoView = null;
    }
}
