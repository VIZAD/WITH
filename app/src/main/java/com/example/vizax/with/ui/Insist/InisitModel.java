package com.example.vizax.with.ui.Insist;

import com.example.vizax.with.App;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.ui.Insist.InsistContact;
import com.example.vizax.with.ui.demo.DemoContact;
import com.example.vizax.with.util.SharedUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/9/19.
 */
public class InisitModel implements InsistContact.InsistModle {

    @Override
    public void createTaskPost(String title, String content, String iconIndex, StringCallback stringCallback) {
        OkHttpUtils.get()
                .url(APIConstant.getApi(APIConstant.KEEP_CREATETASK))
                .addParams("token", SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("title",title)
                .addParams("content",content)
                .addParams("iconIndex", String.valueOf(iconIndex))
                .build()
                .execute(stringCallback) ;

    }

    @Override
    public void getTaskPost(StringCallback stringCallback) {
        OkHttpUtils.get()
                .url(APIConstant.getApi(APIConstant.KEEP_GETTASKS))
                .addParams("token",SharedUtil.getString(App.instance, FieldConstant.token))
                .build()
                .execute(stringCallback);
    }

    @Override
    public void TaskMessagesPost(String date, String taskId, StringCallback stringCallback) {
        OkHttpUtils.get()
                .url(APIConstant.getApi(APIConstant.KEEP_GETTASKMESSAGE))
                .addParams("token",SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("date",date)
                .addParams("taskId",taskId)
                .build()
                .execute(stringCallback);
    }

    @Override
    public void JourPunchPost(String taskId, StringCallback stringCallback) {
        OkHttpUtils.get()
                .url(APIConstant.getApi(APIConstant.KEEP_SIGNIN))
                .addParams("token",SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("taskId",taskId)
                .build()
                .execute(stringCallback);
    }

    @Override
    public void deleteTaskPost(String taskId, StringCallback stringCallback) {
        OkHttpUtils.get()
                .url(APIConstant.getApi(APIConstant.KEEP_DELETEASKMESSAGE))
                .addParams("token",SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("taskId",taskId)
                .build()
                .execute(stringCallback);
    }

    @Override
    public void JourEditPost(String taskId, String date, String remark,StringCallback stringCallback) {
        OkHttpUtils.get()
                .url(APIConstant.getApi(APIConstant.KEEP_EDITTASKMESSAGE))
                .addParams("token",SharedUtil.getString(App.instance, FieldConstant.token))
                .addParams("taskId",taskId)
                .addParams("date",date)
                .addParams("remark",remark)
                .build()
                .execute(stringCallback);

    }
}

