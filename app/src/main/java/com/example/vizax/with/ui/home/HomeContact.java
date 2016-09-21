package com.example.vizax.with.ui.home;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.example.vizax.with.bean.HomeInvitationBean;
import com.example.vizax.with.bean.InvitationBean;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/17.
 */
public interface HomeContact {
    interface View extends BaseView {
        public void loadHomeFirstData(List<InvitationBean> lists, int lastId);
        //public void loadHomeUpData(List<HomeInvitationBean.DataBean> lists,int lastId);
        public void loadHomeDownData(List<InvitationBean> lists,int lastId);
        public void showHomeToast(String toast);
        public void openHeadDetail(String string);
        public void openOtherDetail(ArrayList<InvitationBean> mhomeBeanlists, int i);
    }

    interface Modle{
        public void loadHomeData(String token,int typeId,int userId,int lastInvitationId,int limit,StringCallback stringCallback);
    }

    interface Presenter extends BasePresenter<View> {
        public void loadHomeData(String token,int typeId,int userId,int lastInvitationId,int limit);
    }


}
