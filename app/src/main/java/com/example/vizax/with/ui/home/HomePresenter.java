package com.example.vizax.with.ui.home;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.MenuItemHoverListener;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.vizax.with.R;
import com.example.vizax.with.bean.HomeInvitationBean;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.ui.invitationList.InvitationActivity;
import com.example.vizax.with.util.GsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/9/17.
 */
public class HomePresenter implements HomeContact.Presenter{

    private HomeContact.View mHomeView;
    private HomeModel mHimeModel;
    private InvitationBaseBean mHomeInvitationBean;
    public ArrayList<InvitationBean> mhomeBeanlists;
    private int lastId;

    public HomePresenter(HomeActivity homeActivity){
        this.mHomeView=homeActivity;
        mHimeModel=new HomeModel();
        mhomeBeanlists=new ArrayList<InvitationBean>();
    }

    public void addHead(){
        InvitationBean invitationBean1=new InvitationBean();
        invitationBean1.setItemType(1);
        mhomeBeanlists.add(invitationBean1);
    }

    @Override
    public void loadHomeData(String token, int typeId, int userId, int lastInvitationId, int limit) {
        mHimeModel.loadHomeData(token, typeId, userId, lastInvitationId, limit, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {


                //mHomeView.showHomeToast("值："+lastInvitationId);
                    mHomeInvitationBean= GsonUtil.toString(response,InvitationBaseBean.class);
                    if (mHomeInvitationBean.getCode().equals("200")){
                        //mHomeView.showHomeToast(mHomeInvitationBean.getMsg());
                        if(lastInvitationId==0){
                            //mHomeView.showHomeToast("下拉："+mHomeInvitationBean.getData().size());
                            mhomeBeanlists.clear();
                            addHead();
                            for (int i=0;i<mHomeInvitationBean.getData().size();i++){
                                InvitationBean dataBean=mHomeInvitationBean.getData().get(i);
                                dataBean.setItemType(2);
                                mhomeBeanlists.add(dataBean);
                                if(i==mHomeInvitationBean.getData().size()-1){
                                    lastId=Integer.parseInt(dataBean.getInvitaionId());
                                }
                            }
                            mHomeView.loadHomeFirstData(mhomeBeanlists,lastId);
                            //mHomeView.showHomeToast("下拉："+mhomeBeanlists.size());
                        }else{
                            //mHomeView.showHomeToast("上拉："+mHomeInvitationBean.getData().size());
                            for (int i=0;i<mHomeInvitationBean.getData().size();i++){
                                InvitationBean dataBean=mHomeInvitationBean.getData().get(i);
                                dataBean.setItemType(2);
                                mhomeBeanlists.add(dataBean);
                                if(i==mHomeInvitationBean.getData().size()-1){
                                    lastId=Integer.parseInt(dataBean.getInvitaionId());
                                }
                            }
                            mHomeView.loadHomeDownData(mhomeBeanlists,lastId);
                            //mHomeView.showHomeToast("上拉："+mhomeBeanlists.size());
                        }
                    }else{

                    }
            }
        });
    }

    @Override
    public void attachView(@NonNull HomeContact.View View) {
        mHomeView=View;
    }

    @Override
    public void detachView() {
        mHomeView=null;
    }

    public void initSwipe(SwipeRefreshLayout swipeRefreshLayout){
            swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                    android.R.color.holo_red_light,android.R.color.holo_orange_light,
                    android.R.color.holo_green_light);
            swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, ((HomeActivity)mHomeView).getResources()
                        .getDisplayMetrics()));
    }

    public void addLoadAnimation(HomeAdapter homeAdapter){
        homeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    public void setHomeItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i){
        switch (view.getId()){
            case R.id.item_invitation_originator_imagVi:
                setHomeItemPicClick(i);
                break;
            case R.id.item_invitation_contents:
                setHomeItemAllClick(i);
                break;
            case R.id.item_invitation_join_btn:
                setHomeItemJoinClick(i);
                break;
        }
    }

    public void setHomeItemAllClick(int i){
        mHomeView.openOtherDetail(mhomeBeanlists,i);
    }

    public void setHomeItemPicClick(int i){
        mHomeView.showHomeToast("第"+i+"个："+mhomeBeanlists.get(i));
    }

    public void setHomeItemJoinClick(int i){
        mHomeView.showHomeToast("第"+i+"个："+mhomeBeanlists.get(i).getCurrentNumber()+"");
    }

    public void setHomeHeadClick(BaseQuickAdapter baseQuickAdapter, View view, int i){
        mHomeView.openHeadDetail(view.getTag()+"");
    }

}
