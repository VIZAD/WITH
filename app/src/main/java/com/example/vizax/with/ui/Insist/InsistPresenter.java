package com.example.vizax.with.ui.Insist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.bean.Misson;
import com.example.vizax.with.bean.Misson_bg_colorSet;
import com.example.vizax.with.bean.TaskMsg;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.sidemenu.model.SlideMenuItem;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by VIZAX on 2016/09/16.
 */

public class InsistPresenter implements InsistContact.Presenter {


    private List<SlideMenuItem> list = new ArrayList<>();
    private Misson_bg_colorSet mMssion_bg_colorSet;
    private SharedPreferences sp;
    private InsistContact.View InsistView;
    private Activity activity;
    private ContentFragment contentFragment;
    private TaskMsg TaskMsg;

    public InsistPresenter() {
    }

    @Override
    public void createTask(String title,String content,int iconIndex) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.KEEP_CREATETASK))
                .addParams("token","111")
                .addParams("title",title)
                .addParams("content",content)
                .addParams("iconIndex", String.valueOf(iconIndex))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
                        BaseBean<Misson> baseBean = GsonUtil.toString(response,BaseBean.class);
//                        if (baseBean.getCode().equals("200")) {
//                            //System.out.println("number:"+baseBean.getData().getTaskNumber());
//                        }
//                        else {
//
//                        }
                        System.out.println("bean = "+baseBean.getCode());
                    }
                });

    }
    @Override
    public void getTask() {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.KEEP_GETTASKS))
                .addParams("token","111")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
                        Misson misson = GsonUtil.toString(response,Misson.class);
//                        if (baseBean.getCode().equals("200")) {
//                            //System.out.println("number:"+baseBean.getData().getTaskNumber());
//                        }
//                        else {
//                        }
                        System.out.println("code = "+misson.getData().getCurrTasks().size());
                        InsistView.setData(misson);
                    }
                });

    }


    @Override
    public void TaskMessages(String date,String taskId) {

        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.KEEP_GETTASKMESSAGE))
                .addParams("token","111")
                .addParams("date",date+"-1 9:20:21")
                .addParams("taskId",taskId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
                        TaskMsg = GsonUtil.toString(response,TaskMsg.class);

//                        if (baseBean.getCode().equals("200")) {
//                            //System.out.println("number:"+baseBean.getData().getTaskNumber());
//                        }
//                        else {
//                        }

                        for (int i = 0;i<TaskMsg.getData().getCalendar().size();i++) {
                            System.out.println("content = " + TaskMsg.getData().getCalendar().get(i).getDay());

                        }
                        InsistView.setClData(TaskMsg);


                    }
                });
    }

    @Override
    public void JourPunch(String taskId) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.KEEP_SIGNIN))
                .addParams("token","111")
                .addParams("taskId",taskId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
                        TaskMsg = GsonUtil.toString(response,TaskMsg.class);

//                        if (baseBean.getCode().equals("200")) {
//                            //System.out.println("number:"+baseBean.getData().getTaskNumber());
//                        }
//                        else {
//                        }
                        System.out.println("签到:"+TaskMsg.getCode());



                    }
                });

    }

    @Override
    public void JourEdit() {

    }

    @Override
    public void attachView(@NonNull InsistContact.View View) {
        InsistView = View;

    }

    @Override
    public void detachView() {

    }
}


