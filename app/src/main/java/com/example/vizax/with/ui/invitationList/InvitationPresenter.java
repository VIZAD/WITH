package com.example.vizax.with.ui.invitationList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.bean.InvitationBaseBean;

/**
 * Created by Young on 2016/9/16.
 */
public class InvitationPresenter implements InvitationContact.InvitationPresenter {
    private InvitationContact.View mInvitationActivity;
    private InvitationContact.InvitationlModel mInvitationModel;
    private InvitationRecyclerViewAdapter mAdapter;
    public static InvitationBaseBean mInvitationBaseBean;
    private String type = "";
    private String finalItemId;

    /**
     * 设置recyclerView的adapter
     * @param context
     * @param recyclerView
     * @param visible 时间右边的操作按钮是否显示
     */
    @Override
    public void getDataAndSetAdapter(Context context, RecyclerView recyclerView, int visible, String typeId, String userId){
       mInvitationModel.getData(typeId, userId, new InvitationContact.InvitationCallBack() {
           @Override
           public void setAdapter(InvitationBaseBean lInvitationBaseBean) {
               mInvitationBaseBean = lInvitationBaseBean;
               mAdapter = new InvitationRecyclerViewAdapter(context, mInvitationBaseBean,visible);

               mAdapter.setOnItemClickListener(new InvitationRecyclerViewAdapter.ClickListerner() {
                   @Override
                   public void onItemClick(int position,InvitationBaseBean invitationBaseBean) {
                       mInvitationActivity.OpenDetail(position,invitationBaseBean);
                   }
               });
               mAdapter.setCallBack(new InvitationContact.InvitationCallBack() {
                   @Override
                   public void setAdapter(InvitationBaseBean invitationBaseBean) {

                   }

                   @Override
                   public void press(@Nullable String contents, int position, String type) {
                       InvitationPresenter.this.type = type;
                       if(contents == null)
                        mInvitationActivity.showDialog(true,null,position);
                       else
                           mInvitationActivity.showDialog(false,contents,position);
                   }
               });
               recyclerView.setLayoutManager(new LinearLayoutManager(context));
               recyclerView.setAdapter(mAdapter);
           }

           @Override
           public void press(@Nullable String contents, int position, String type) {

           }


       });

    }
    @Override
    public void attachView(@NonNull InvitationContact.View View) {
        mInvitationActivity = View;
        mInvitationModel = new InvitationModel();
    }

    @Override
    public void detachView() {

    }

    @Override
    public void onPositive(int position) {
       new  InvitationDetailModel().join(mInvitationBaseBean.getData().get(position), type, new InvitationDetailContact.ChangeBtn() {
            @Override
            public void setSrc() {

                //holder.itemInvitationJoinBtn.setImageResource(R.drawable.join_unselected);
                mInvitationBaseBean.getData().get(position).setJoin( mInvitationBaseBean.getData().get(position).isJoin()?false:true);
                System.out.println("!!!!!!"+ mInvitationBaseBean.getData().get(position).isJoin());
                mAdapter.notifyItemChanged(position);

            }
        });
    }


    public void setAdapter(Context context, RecyclerView recyclerView, InvitationBaseBean invitationBaseBean, int visible) {
        System.out.println("跟新");
        mAdapter = new InvitationRecyclerViewAdapter(context, mInvitationBaseBean,visible);
        mAdapter.setOnItemClickListener(new InvitationRecyclerViewAdapter.ClickListerner() {
            @Override
            public void onItemClick(int position,InvitationBaseBean invitationBaseBean) {
                mInvitationActivity.OpenDetail(position,invitationBaseBean);
            }
        });
        mAdapter.setCallBack(new InvitationContact.InvitationCallBack() {
            @Override
            public void setAdapter(InvitationBaseBean invitationBaseBean) {

            }

            @Override
            public void press(@Nullable String contents, int position, String type) {
                InvitationPresenter.this.type = type;
                if(contents == null)
                    mInvitationActivity.showDialog(true,null,position);
                else
                    mInvitationActivity.showDialog(false,contents,position);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void pullLoadMore() {
        int lLastIndex = mInvitationBaseBean.getData().size() - 1;
        finalItemId = mInvitationBaseBean.getData().get(lLastIndex).getInvitaionId();
        mInvitationBaseBean = mInvitationModel.addData(finalItemId, "10", mInvitationBaseBean, new InvitationModel.StopRefreshing() {
            @Override
            public void stopRefreshing() {
                mInvitationActivity.stopRefresh();
            }
        });
        mAdapter.notifyDataSetChanged();
    }
}
