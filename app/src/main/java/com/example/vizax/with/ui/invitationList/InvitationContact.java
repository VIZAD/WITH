package com.example.vizax.with.ui.invitationList;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.bean.UserInforBean;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Young on 2016/9/18.
 */
public interface InvitationContact {

    interface View extends BaseView{
        //显示toolbar右边的icon
        void showRightIcon();

       //显示dialog type == true 显示操作dialog  type == false 显示joindialog
        void showDialog(boolean type,@Nullable String contents, int position);

        void showDiaolog();
        void dismissDialog();
        //获取数据失败事件
        void loadDataFailure();
        //toast显示信息
        void showToast(String string);

        //打开我的互动编辑页面
        void  openEdit();
        //打开发起活动界面
        void openLaunch();
        //item点击事件
        void OpenDetail(int position, InvitationBaseBean invitationBean);
        void OpenUserInfor(UserInforBean userInforBean);
        void stopRefresh();

        void openHeadDetail(String string);
    }
    interface InvitationlModel{
        void getData(String typeId, String userId, StringCallback stringCallback);
        void addData(String finalItemId, String count,StringCallback stringCallback);
        void deleteData(String invitationId,StringCallback stringCallback);
    }
    interface InvitationPresenter extends BasePresenter<View>{
        void getDataAndSetAdapter(Context context, RecyclerView recyclerView, int visible, String typeId, String userId);
        void onPositive(int position);
        void setAdapter(Context context, RecyclerView recyclerView,InvitationBaseBean invitationBaseBean, int visible);
        void pullLoadMore(Context context, RecyclerView recyclerView, int visible, String typeId, String userId);
        void getUserInfor(String userId,String inviationId);
        void setNotifyChange();
        void deleteInvitation(int position);

    }
    interface InvitationCallBack {
        //获得json数据后回调setAdapter
        void setAdapter(InvitationBaseBean invitationBaseBean);

        //RecyclerView的操作按钮回调事件
        void press(@Nullable String contents, int position, String type);


    }


}
