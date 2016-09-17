package com.example.vizax.with.ui.invitationList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;

/**
 * Created by Young on 2016/9/16.
 */
public class InvitationListPresenter implements BasePresenter {
    private InvitationActivity mMyfocusActivity;
    private InvitationModel mInvitationModel;
    private InvitationRecyclerViewAdapter mAdapter;
    public InvitationListPresenter(InvitationActivity mMyfocusActivity) {
        this.mMyfocusActivity = mMyfocusActivity;
        mInvitationModel = new InvitationModel();
    }

    /**
     * 设置recyclerView的adapter
     * @param context
     * @param recyclerView
     * @param visible 时间右边的操作按钮是否显示
     */
    public void setAdapter(Context context,RecyclerView recyclerView,int visible,String typeId,String userId){
       mInvitationModel.getData(context,recyclerView,visible,typeId,userId);

    }
    @Override
    public void attachView(@NonNull BaseView View) {

    }

    @Override
    public void detachView() {

    }
}
