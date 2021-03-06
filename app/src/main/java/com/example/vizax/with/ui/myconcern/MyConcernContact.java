package com.example.vizax.with.ui.myconcern;

import android.content.Context;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;

import java.util.List;

/**
 * Created by VIZAX on 2016/09/21.
 */

public interface MyConcernContact {

    interface View extends BaseView {
        void showErrorToast(String error);
        void stopRefresh();

        void setNewData(List<MyConcern.DataBean> mDatas);
        void addData(List<MyConcern.DataBean> mDatas);
        void startLoginActivity();

    }
    //Presenter操作接口，自己定义一个Presenter实现该接口
    interface Presenter extends BasePresenter<MyConcernContact.View> {
        void IsCocern(String userId);
        void onRefresh();
        void onloadMore(int lastConcernedUserId);
    }
}
