package com.example.vizax.with.ui.invitationList;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.InvitationBean;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Young on 2016/9/18.
 */
public interface InvitationDetailContact {

    interface View extends BaseView{
        void showDialog(String contents);
        void changeBtnSrc();
        void removeMember(int position);
        void addMember(int position);

        //点击确定参加按钮后
        void showDialog();
        void dismissDialog();

        //toast
        void showToast(String string);

        //已参与人加减
        void addNum();
        void reduceNum();
    }
    interface InvitationDetailModel{
        String getData(String token,String typeId,String userId,String lastInvitatioonId,String limit);
        void join(InvitationBean mInvitationBeen, String type, StringCallback stringCallback);
    }
    interface Presenter extends BasePresenter<View>{
        void pressJoin(InvitationBean mInvitationBeen);
        void onPositive(InvitationBean mInvitationBeen);
        void setPosition(int position);//保存点击的item position

    }

    interface ChangeBtn{
        void setSrc();
    }
}
