package com.example.vizax.with.ui.invitationList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.MembersBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.ui.userInformation.UserInformationContact;
import com.example.vizax.with.ui.userInformation.UserInformationModuel;
import com.example.vizax.with.util.GsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Young on 2016/9/16.
 */
public class InvitationPresenter implements InvitationContact.InvitationPresenter {
    private InvitationContact.View mInvitationActivity;
    private InvitationContact.InvitationlModel mInvitationModel;
    private UserInformationModuel mUserinforModuel;
    public InvitationRecyclerViewAdapter mAdapter;
    public static InvitationBaseBean mInvitationBaseBean;
    private String type = "";
    private String finalItemId;
    private UserInforBean mUserInforBean;
    public InvitationBaseBean baseBean;
    private String token;

    /**
     * 设置recyclerView的adapter
     * @param context
     * @param recyclerView
     * @param visible 时间右边的操作按钮是否显示
     */
    @Override
    public void getDataAndSetAdapter(Context context, RecyclerView recyclerView,String token,int visible, String typeId, String userId){

        this.token = token;
        mInvitationModel.getData(typeId, userId,token,new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

                baseBean = GsonUtil.toString(response,InvitationBaseBean.class);

                if (baseBean.getCode().equals("200")){
                    setAdapter(context,recyclerView,baseBean,visible);
                }
                else{
                    System.out.println("null!!!");
                }


            }
        });

    }
    @Override
    public void attachView(@NonNull InvitationContact.View View) {
        mInvitationActivity = View;
        mInvitationModel = new InvitationModel();
        mUserinforModuel = new UserInformationModuel();
    }


    @Override
    public void detachView() {

    }

    @Override
    public void onPositive(int position) {
        new  InvitationDetailModel().join(baseBean.getData().get(position), type, new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                baseBean.getData().get(position).setJoin( baseBean.getData().get(position).isJoin()?false:true);
                if( baseBean.getData().get(position).isJoin()){
                    membersAdd(position);
                }else {
                    membersReduce(position);
                }
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    private void membersAdd(int position) {
        //TODO 后面用登录的静态类User
        MembersBean newMember = new MembersBean();
        newMember.setUserId("2");
        newMember.setRealName("潘大爷");
        newMember.setPhone("1831876465");
        baseBean.getData().get(position).getMembers().add(newMember);
    }
    private void membersReduce(int position) {
        for(int i = 1;i <  baseBean.getData().get(position).getMembers().size();i++){
            //TODO 后面用登录的静态类User.UserID
            if (baseBean.getData().get(position).getMembers().get(i).getUserId().equals("2")){
                baseBean.getData().get(position).getMembers().remove(i);
                break;
            }
        }
    }

    public void setAdapter(Context context, RecyclerView recyclerView, InvitationBaseBean invitationBaseBean, int visible) {
        mAdapter = new InvitationRecyclerViewAdapter(context, invitationBaseBean,visible);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new InvitationRecyclerViewAdapter.ClickListerner() {
            @Override
            public void onItemClick(int position,InvitationBaseBean invitationBaseBean) {
                mInvitationActivity.OpenDetail(position,invitationBaseBean);
            }

            @Override
            public void onAvatarOnclik(int position, InvitationBaseBean invitationBaseBean) {

                UserInforBean lUserInforBean = getUserInfor(invitationBaseBean.getData().get(position).getMembers().get(0).getUserId());
                mInvitationActivity.OpenUserInfor(position,lUserInforBean);
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
    public void pullLoadMore(Context context, RecyclerView recyclerView, int visible, String typeId, String userId) {
        int lLastIndex = baseBean.getData().size() - 1;
        finalItemId = baseBean.getData().get(lLastIndex).getInvitaionId();
        mInvitationModel.addData("17", "10",token, new StringCallback() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                mInvitationActivity.stopRefresh();
                mAdapter.notifyDataSetChanged();
                //setAdapter(context,recyclerView,baseBean,visible);

            }
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                InvitationBaseBean  loadBean = GsonUtil.toString(response, InvitationBaseBean.class);
                System.out.println("更新前的数据="+response);
                for(int i = 0;i<loadBean.getData().size();i++){
                    baseBean.getData().add(loadBean.getData().get(i));
                }
                System.out.println("更新后的数据="+baseBean.getData().size());
                if (loadBean.getCode().equals("200")){
                    System.out.println("success!!!");
                }
                else{
                    System.out.println("null!!!");
                }
            }
        });

    }

    @Override
    public UserInforBean getUserInfor(String id) {
        mUserInforBean =  mUserinforModuel.getUserInformation(id);
        return mUserInforBean;
    }

    @Override
    public void setNotifyChange() {
        mAdapter.notifyDataSetChanged();
    }
}
