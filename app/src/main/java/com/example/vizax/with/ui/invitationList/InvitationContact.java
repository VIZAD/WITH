package com.example.vizax.with.ui.invitationList;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.example.vizax.with.bean.InvitationBaseBean;

/**
 * Created by Young on 2016/9/18.
 */
public interface InvitationContact {

    interface View extends BaseView{
        //显示toolbar右边的icon
        void showRightIcon();

       //显示dialog type == true 显示操作dialog  type == false 显示joindialog
        void showDialog(boolean type,@Nullable String contents, int position);

        //获取数据失败事件
        void loadDataFailure();
    }
    interface InvitationlModel{
        void getData(String typeId, String userId,InvitationCallBack invitationCallBack);
        void addData(String finalItemId,String count,InvitationBaseBean invitationBaseBean);
    }
    interface InvitationPresenter extends BasePresenter<View>{
        void getDataAndSetAdapter(Context context, RecyclerView recyclerView, int visible, String typeId, String userId);
        void onPositive(int position);
        void setAdapter(Context context, RecyclerView recyclerView, int visible);
        void pullLoadMore();
    }
    interface InvitationCallBack {
        //获得json数据后回调setAdapter
        void setAdapter(InvitationBaseBean invitationBaseBean);

        //RecyclerView的操作按钮回调事件
        void press(@Nullable String contents, int position, String type);


    }


}
