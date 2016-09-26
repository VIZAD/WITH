package com.example.vizax.with.ui.Insist;

import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.ui.Insist.InsistContact;
import com.example.vizax.with.ui.demo.DemoContact;
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
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.KEEP_CREATETASK))
                .addParams("token","1")
                .addParams("title",title)
                .addParams("content",content)
                .addParams("iconIndex", String.valueOf(iconIndex))
                .build()
                .execute(stringCallback) ;

    }

    @Override
    public void getTaskPost(StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.KEEP_GETTASKS))
                .addParams("token","1")
                .build()
                .execute(stringCallback);
    }

    @Override
    public void TaskMessagesPost(String date, String taskId, StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.KEEP_GETTASKMESSAGE))
                .addParams("token","1")
                .addParams("date",date)
                .addParams("taskId",taskId)
                .build()
                .execute(stringCallback);
    }

    @Override
    public void JourPunchPost(String taskId, StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.KEEP_SIGNIN))
                .addParams("token","1")
                .addParams("taskId",taskId)
                .build()
                .execute(stringCallback);
    }

    @Override
    public void deleteTaskPost(String taskId, StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.KEEP_DELETEASKMESSAGE))
                .addParams("token","1")
                .addParams("taskId",taskId)
                .build()
                .execute(stringCallback);
    }

    @Override
    public void JourEditPost(String taskId, String date, String remark,StringCallback stringCallback) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.KEEP_EDITTASKMESSAGE))
                .addParams("token","1")
                .addParams("taskId",taskId)
                .addParams("date",date)
                .addParams("remark",remark)
                .build()
                .execute(stringCallback);

    }
}

