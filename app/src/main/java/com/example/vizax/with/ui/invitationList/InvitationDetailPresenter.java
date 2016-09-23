package com.example.vizax.with.ui.invitationList;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.bean.InvitationBean;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Young on 2016/9/18.
 */
public class InvitationDetailPresenter implements InvitationDetailContact.Presenter {
    InvitationDetailContact.View mInvitationDetailView;
    InvitationDetailModel mInvitationDetailModel;
    InvitationBean mInvitationBean;
    String type = "2" ;
    public int position;
    @Override
    public void attachView(@NonNull InvitationDetailContact.View View) {
        mInvitationDetailView = View;
        mInvitationDetailModel = new InvitationDetailModel();
    }

    @Override
    public void detachView() {

    }

    /**
     * 参加活动按钮点击事件
     * @param mInvitationBeen
     */
    @Override
    public void pressJoin(InvitationBean mInvitationBeen) {
        mInvitationBean = mInvitationBeen;
        if (mInvitationBean.isJoin()) {
            mInvitationDetailView.showDialog(InvitationDetailsActivity.QUIT);
            type = "2";
        } else {
            if (mInvitationBean.getCurrentNumber() == mInvitationBean.getTotalNumber()) {
                mInvitationDetailView.showDialog(InvitationDetailsActivity.JOIN2);
                type = "1";
            } else {
                mInvitationDetailView.showDialog(InvitationDetailsActivity.JOIN1);
                type = "0";
            }
        }
    }

    /**
     * Dialog确定按钮点击事件
     * @param mInvitationBeen
     */
    @Override
    public void onPositive(InvitationBean mInvitationBeen) {
        mInvitationDetailModel.join(mInvitationBean, type, new StringCallback() {
            //TODO 临时处理 待删除方法
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                mInvitationBean .setJoin(mInvitationBean.isJoin() ? (false):(true));
                mInvitationDetailView.changeBtnSrc();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("error");
            }
            @Override
            public void onResponse(String response, int id) {
                mInvitationBean .setJoin(mInvitationBean.isJoin() ? (false):(true));
                mInvitationDetailView.changeBtnSrc();
            }

        });
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

}


