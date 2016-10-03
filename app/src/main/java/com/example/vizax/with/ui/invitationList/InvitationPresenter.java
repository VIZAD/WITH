package com.example.vizax.with.ui.invitationList;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vizax.with.App;
import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.MembersBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.ui.userInformation.UserInformationModuel;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.SharedUtil;
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
    private String token ;


    /**
     * 设置recyclerView的adapter
     * @param context
     * @param recyclerView
     * @param visible 时间右边的操作按钮是否显示
     */
    @Override
    public void getDataAndSetAdapter(Context context, RecyclerView recyclerView,int visible, String typeId, String userId){

        mInvitationModel.getData(typeId, userId,new StringCallback() {

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
                    setAdapter(context,recyclerView,baseBean,visible);
                    mInvitationActivity.showToast(baseBean.getMsg());
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
        mInvitationActivity.showDiaolog();
        new  InvitationDetailModel().join(baseBean.getData().get(position), type, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mInvitationActivity.dismissDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                BaseBean join = GsonUtil.toString(response,BaseBean.class);
                System.out.println("msg="+join.getMsg());
                if (join.getCode().equals("200")) {
                    if(type.equals("1")||type.equals("2")) {
                        baseBean.getData().get(position).setJoin(baseBean.getData().get(position).isJoin() ? false : true);
                        if (baseBean.getData().get(position).isJoin()) {
                            membersAdd(position);
                        } else {
                            membersReduce(position);
                        }
                        mAdapter.notifyItemChanged(position);
                    }else {
                        mInvitationActivity.showToast("已发送特批信息");
                    }

                }else{
                    mInvitationActivity.showToast(join.getMsg());
                }
                mInvitationActivity.dismissDialog();
            }
        });
    }

    private void membersAdd(int position) {
        MembersBean newMember = new MembersBean();
        newMember.setUserId(String.valueOf(SharedUtil.getInt(App.instance,FieldConstant.userId)));
        newMember.setRealName(SharedUtil.getString(App.instance,FieldConstant.realName));
        newMember.setPhone(SharedUtil.getString(App.instance,FieldConstant.phone));
        baseBean.getData().get(position).getMembers().add(newMember);
        int num = Integer.parseInt(baseBean.getData().get(position).getCurrentNumber()) + 1;
        baseBean.getData().get(position).setCurrentNumber(String.valueOf(num));
    }
    private void membersReduce(int position) {
        for(int i = 1;i <  baseBean.getData().get(position).getMembers().size();i++){
            if (baseBean.getData().get(position).getMembers().get(i).getUserId().equals(SharedUtil.getString(App.instance,FieldConstant.userId))){
                baseBean.getData().get(position).getMembers().remove(i);
                break;
            }
        }
        int num = Integer.parseInt(baseBean.getData().get(position).getCurrentNumber()) - 1;
        baseBean.getData().get(position).setCurrentNumber(String.valueOf(num));
    }

    public void setAdapter(Context context, RecyclerView recyclerView, InvitationBaseBean invitationBaseBean, int visible) {
        mAdapter = new InvitationRecyclerViewAdapter(context, invitationBaseBean,visible);
        mAdapter.setOnItemClickListener(new InvitationRecyclerViewAdapter.ClickListerner() {
            @Override
            public void onItemClick(int position,InvitationBaseBean invitationBaseBean) {
                mInvitationActivity.OpenDetail(position,invitationBaseBean);
            }

            @Override
            public void onAvatarOnclik(int position, InvitationBaseBean invitationBaseBean) {

                getUserInfor(invitationBaseBean.getData().get(position).getMembers().get(0).getUserId(),invitationBaseBean.getData().get(position).getInvitaionId());

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
        mInvitationModel.addData(finalItemId, "10", new StringCallback() {
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
    public void getUserInfor(String id,String invitationId) {
        mUserinforModuel.getUserInformation(id, invitationId, new StringCallback() {
            @Override
                public void onError(Call call, Exception e, int id) {
                    System.out.println("NULL!!!!");
                }

                @Override
                public void onResponse(String response, int id) {
                    mUserInforBean = GsonUtil.toString(response, UserInforBean.class);
                    if(mUserInforBean.getCode() == 200){
                        System.out.println("用户id="+mUserInforBean.getData().getUserId());
                        mInvitationActivity.OpenUserInfor(mUserInforBean);
                    }else {
                        mInvitationActivity.showToast("未参加活动无法查看发起人信息");
                    }

                }
            });


    }

    @Override
    public void setNotifyChange() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteInvitation(int position) {
        mInvitationActivity.showDiaolog();
        mInvitationModel.deleteData(baseBean.getData().get(position).getInvitaionId(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mInvitationActivity.dismissDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                //  baseBean.getData().remove(position);
                mAdapter.notifyItemRemoved(position);
                mInvitationActivity.dismissDialog();
            }
        });
    }
}
