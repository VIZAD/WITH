package com.example.vizax.with.ui.Insist;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.example.vizax.with.bean.Misson;
import com.example.vizax.with.bean.TaskMsg;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by VIZAX on 2016/09/16.
 */

public interface InsistContact {


    interface View extends BaseView {
        void setData(Misson misson);
        void setClData(TaskMsg taskMsg);


    }

    interface InsistModle{
        void createTaskPost(String title,String content,int iconIndex, StringCallback stringCallback);
        void getTaskPost(StringCallback stringCallback);
        void  TaskMessagesPost(String date,String taskId,StringCallback stringCallback);
        void  JourPunchPost(String taskId,StringCallback stringCallback);
        void deleteTaskPost(String taskId,StringCallback stringCallback);
    }


    //Presenter操作接口，自己定义一个Presenter实现该接口
    interface Presenter extends BasePresenter<InsistContact.View> {
        void  createTask(String title,String content,int iconIndex);
        void  getTask();
        void TaskMessages(String date,String taskId);
        void JourPunch(String taskId);
        void JourEdit();
        void deleteTask(String taskId);
    }
}
