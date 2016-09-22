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

    }
    interface InvitationDetailModel{
        String getData(View view);
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
