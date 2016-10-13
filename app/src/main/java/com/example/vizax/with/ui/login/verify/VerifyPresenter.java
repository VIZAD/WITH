package com.example.vizax.with.ui.login.verify;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.vizax.with.ui.login.bean.RegBean;
import com.example.vizax.with.ui.login.bean.UserBean;
import com.example.vizax.with.ui.login.bean.VerifyBean;
import com.example.vizax.with.util.GsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;

/**
 * Created by prj on 2016/9/14.
 */

public class VerifyPresenter implements VerifyContact.Presenter {

    private VerifyContact.View mVerifyView;
    private VerifyModel mVerifyModel;

    //构造方法，可传入自己所需参数
    public VerifyPresenter() {
        mVerifyModel = new VerifyModel();
    }

    @Override
    public void attachView(@NonNull VerifyContact.View View) {
        mVerifyView = View;
    }

    @Override
    public void detachView() {
        //当activity被销毁时，回收视图，避免OOM(内存泄漏)
        mVerifyView = null;
        SMSSDK.unregisterAllEventHandler();//解除注册短信
    }

    @Override
    public void verify(String usernum, String username, String sex) {
        mVerifyView.showLoading();
        mVerifyModel.verify(usernum, username, sex, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mVerifyView.dimissLoading();
                mVerifyView.verifyFailure("未知错误");
            }

            @Override
            public void onResponse(String response, int id) {
                VerifyBean lVerifyBean = GsonUtil.toString(response, VerifyBean.class);
                /*
                Gson lGson = new Gson();
                java.lang.reflect.Type type = new TypeToken<UserBean>() {}.getType();
                lGson.fromJson(response, type);
                */
                if (lVerifyBean.getCode() == 200)
                    mVerifyView.verifySuccess(lVerifyBean.getMsg());
                else if (lVerifyBean.getCode() == 410) {
                    VerifyBean.DataBean data = lVerifyBean.getData();
                    if (data != null)
                        mVerifyView.showDialog(data.getPhone());
                }else {
                    mVerifyView.verifyFailure(lVerifyBean.getMsg());
                }
                mVerifyView.dimissLoading();

            }
        });

    }


    @Override
    public void register(String userphone, String verifynum, String password, String studentnum) {
        mVerifyView.showRegLoading();
        mVerifyModel.register(userphone, verifynum, password, studentnum, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mVerifyView.dimissRegLoading();
                mVerifyView.verifyFailure("未知错误");
            }

            @Override
            public void onResponse(String response, int id) {
                RegBean lRegBean = GsonUtil.toString(response, RegBean.class);
                if (lRegBean.getCode() == 200)
                    mVerifyView.RegSuccess(lRegBean.getMsg());
                else{
                    mVerifyView.RegFailure(lRegBean.getMsg());
                }
                mVerifyView.dimissRegLoading();
            }
        });
    }

    @Override
    public void msgRegister(Handler handler, String phone) {

        EventHandler eh = new EventHandler() {


            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    handler.sendEmptyMessage(1);
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        handler.sendEmptyMessage(2);

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        handler.sendEmptyMessage(3);

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        handler.sendEmptyMessage(4);
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
        //请求获取短信验证码
        SMSSDK.getVerificationCode("86", phone);




    }
}
