package com.example.vizax.with.ui.Insist;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.bean.Misson;
import com.example.vizax.with.bean.Misson_bg_colorSet;
import com.example.vizax.with.bean.TaskMsg;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.sidemenu.model.SlideMenuItem;
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
    private TaskMsg TaskMsg;
    private InsistContact.InsistModle Model;
    public InsistPresenter() {
        Model = new InisitModel();
    }

    @Override
    public void createTask(String title,String content,String iconIndex) {
        Model.createTaskPost(title,content,iconIndex,new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(String response, int id) {
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
                BaseBean<Misson> baseBean = GsonUtil.toString(response,BaseBean.class);
                        if (baseBean.getCode().equals("200")) {
                            System.out.println("bean = "+baseBean.getCode());
                            InsistView.setTitle_Content(title,content);
                        }
                        else {
                        }

            }
        });
    }
    @Override
    public void getTask() {
        Model.getTaskPost( new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(String response, int id) {
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
                Misson misson = GsonUtil.toString(response,Misson.class);
                InsistView.dimissLoading();
                if(misson.getCode().equals("200")) {
                    Log.w("haha",response);
                    System.out.println("code = "+misson.getData().getCurrTasks().size());
                    if(misson.getData().getCurrTasks()!=null) {
                        InsistView.setData(misson);
                    }

                } else {
                    Toast.makeText((Context) InsistView,misson.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void TaskMessages(String date,String taskId) {
        Model.TaskMessagesPost(date,taskId,new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(String response, int id) {
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
                TaskMsg = GsonUtil.toString(response,TaskMsg.class);
                        if (TaskMsg.getCode().equals("200")) {
                            if(TaskMsg.getData().getCalendar()!=null) {
                                System.out.println("size = "+TaskMsg.getData().getCalendar());
                                for (int i = 0;i<TaskMsg.getData().getCalendar().size();i++) {
                                    System.out.println("day = " + TaskMsg.getData().getCalendar().get(i).getDay());
                                    System.out.println("content = " + TaskMsg.getData().getCalendar().get(i).getRemark());
                                }
                            }
                            InsistView.setClData(TaskMsg);
                            InsistView.dimissLoading();
                        }
                        else {
                        }
            }
        });

    }

    @Override
    public void JourPunch(String taskId) {
        Model.JourPunchPost(taskId,new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
                TaskMsg = GsonUtil.toString(response,TaskMsg.class);
                        if (TaskMsg.getCode().equals("200")) {
                            System.out.println("签到:"+TaskMsg.getMsg());
                            InsistView.dimissLoading();
                        }
                        else {
                        }

            }
        });
    }
    @Override
    public void JourEdit(String taskId, String date, String remark) {
         Model.JourEditPost(taskId,date,remark,new StringCallback() {
             @Override
             public void onError(Call call, Exception e, int id) {
                 e.printStackTrace();
             }

             @Override
             public void onResponse(String response, int id) {
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
                 TaskMsg = GsonUtil.toString(response,TaskMsg.class);
                        if (TaskMsg.getCode().equals("200")) {
                            System.out.println("编辑成功:"+TaskMsg.getMsg());
                            Toast.makeText((Context) InsistView,"已保存",Toast.LENGTH_SHORT).show();
                            InsistView.setFootText(TaskMsg,remark);
                            InsistView.dimissLoading();
                        }
                        else {
                       }

             }
         });
    }


    //删除
    @Override
    public void deleteTask(String taskId) {
    Model.deleteTaskPost(taskId,  new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            //e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id) {
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
            TaskMsg = GsonUtil.toString(response,TaskMsg.class);
                        if (TaskMsg.getCode().equals("200")) {
                            InsistView.showToast("删除成功");
                        }
                        else {
                        }
        }
    });
    }

    @Override
    public void attachView(@NonNull InsistContact.View View) {
        InsistView = View;
    }
    @Override
    public void detachView() {

    }
}


