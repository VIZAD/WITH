package com.example.vizax.with.ui.invitationList;

import android.support.annotation.NonNull;

import com.example.vizax.with.App;
import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.bean.MembersBean;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.SharedUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Young on 2016/9/18.
 */
public class InvitationDetailPresenter implements InvitationDetailContact.Presenter {
    InvitationDetailContact.View mInvitationDetailView;
    InvitationDetailModel mInvitationDetailModel;
    InvitationBean mInvitationBean;
    String type = "2" ;
    ArrayList<MembersBean> membersBean;
    public int position;

    public InvitationDetailPresenter(ArrayList<MembersBean> membersBean) {
        this.membersBean = membersBean;
    }

    @Override
    public void attachView(@NonNull InvitationDetailContact.View View) {
        mInvitationDetailView = View;
        mInvitationDetailModel = new InvitationDetailModel();
    }

    @Override
    public void detachView() {
        mInvitationDetailView = null;
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
            type = "2";//传2为退出
        } else {
            if (mInvitationBean.getCurrentNumber() == mInvitationBean.getTotalNumber()) {
                mInvitationDetailView.showDialog(InvitationDetailsActivity.JOIN2);
                type = "0";//传0为特批
            } else {
                mInvitationDetailView.showDialog(InvitationDetailsActivity.JOIN1);
                type = "1";//传1为加入
            }
        }
    }

    /**
     * Dialog确定按钮点击事件
     * @param mInvitationBeen
     */
    @Override
    public void onPositive(InvitationBean mInvitationBeen) {
        mInvitationDetailView.showDialog();
        mInvitationDetailModel.join(mInvitationBean, type, new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                mInvitationDetailView.dismissDialog();
            }
            @Override
            public void onResponse(String response, int id) {

                BaseBean baseBean = GsonUtil.toString(response,BaseBean.class);
                if(baseBean.getCode().equals("200")) {
                   if( mInvitationBean.isJoin()){
                       quitInvitation();
                   }else {
                       joinInitation();
                   }
                    mInvitationDetailView.changeBtnSrc();
                }else {
                    mInvitationDetailView.showToast(baseBean.getMsg());
                }
                mInvitationDetailView.dismissDialog();
            }
        });
    }

    private void quitInvitation() {
        mInvitationBean.setJoin(false);
        for(int i = 1;i < membersBean.size();i++){
            if (membersBean.get(i).getUserId().equals(String.valueOf(SharedUtil.getInt(App.instance, FieldConstant.userId)))){
                membersBean.remove(i);
                mInvitationDetailView.removeMember(i);
                break;
            }
        }
        mInvitationDetailView.reduceNum();


    }
    private void joinInitation() {
        mInvitationBean.setJoin(true);
        MembersBean newMember = new MembersBean();
        newMember.setUserId(String.valueOf(SharedUtil.getInt(App.instance, FieldConstant.userId)));
        newMember.setRealName(SharedUtil.getString(App.instance,FieldConstant.realName));
        newMember.setPhone(SharedUtil.getString(App.instance,FieldConstant.phone));
        newMember.setHeadUrl(SharedUtil.getString(App.instance,FieldConstant.userUrl));
        membersBean.add(newMember);
        mInvitationDetailView.addMember(membersBean.size());
        mInvitationDetailView.addNum();

    }
    @Override
    public void setPosition(int position) {
        this.position = position;
    }

}


