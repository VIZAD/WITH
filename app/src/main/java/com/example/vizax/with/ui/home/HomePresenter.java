package com.example.vizax.with.ui.home;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.ViewDragHelper;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
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
    public static final String IS_JOIN="是否退出活动";
    public static final String NOT_JOIN="是否参加活动";

    public HomePresenter(HomeActivity homeActivity){
        this.mHomeView=homeActivity;
        mHimeModel=new HomeModel();
        mhomeBeanlists=new ArrayList<InvitationBean>();
    }

    //添加头部类型的布局
    public void addHead(){
        InvitationBean invitationBean1=new InvitationBean();
        invitationBean1.setItemType(1);
        mhomeBeanlists.add(invitationBean1);
    }

    //发起网络请求获取数据并填充到mhomeBeanlists
    @Override
    public void loadHomeData(String token, int typeId, int userId, int lastInvitationId, int limit) {
        mHimeModel.loadHomeData(token, typeId, userId, lastInvitationId, limit, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {


                mHomeView.showHomeToast("值："+response);
                    mHomeInvitationBean= GsonUtil.toString(response,InvitationBaseBean.class);
                    if (mHomeInvitationBean!=null&&mHomeInvitationBean.getCode().equals("200")){
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

    //初始化SwipeRefreshLayout
    @Override
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
        homeAdapter.openLoadAnimation();
        homeAdapter.isFirstOnly(false);
    }

    //邀约的点击事件
    @Override
    public void setHomeItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i){
        switch (view.getId()){
            case R.id.item_invitation_originator_imagVi:
                setHomeItemPicClick(i);
                break;
            case R.id.item_invitation_root:
                setHomeItemAllClick(i);
                break;
            case R.id.item_invitation_join_btn:
                setHomeItemJoinClick(i);
                break;
        }
    }

    //增加抽屉的活动范围
    @Override
    public void addDrawerRange(DrawerLayout mDrawerLayout) {
        Field mDragger = null;
        try {
            mDragger = mDrawerLayout.getClass().getDeclaredField(
                    "mLeftDragger"); //mRightDragger for right obviously
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mDragger.setAccessible(true);
        ViewDragHelper draggerObj = null;
        try {
            draggerObj = (ViewDragHelper) mDragger
                    .get(mDrawerLayout);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Field mEdgeSize = null;
        try {
            mEdgeSize = draggerObj.getClass().getDeclaredField(
                    "mEdgeSize");
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mEdgeSize.setAccessible(true);
        int edge = 0;
        try {
            edge = mEdgeSize.getInt(draggerObj);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            mEdgeSize.setInt(draggerObj, edge * 5); //optimal value as for me, you may set any constant in dp
            //You can set it even to the value you want like mEdgeSize.setInt(draggerObj, 150); for 150dp
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //邀约点击发起者头像
    public void setHomeItemPicClick(int i){
        mHomeView.showHomeToast("第"+i+"个："+mhomeBeanlists.get(i).getOriginatorNickname());
    }

    //邀约点击整个Item
    public void setHomeItemAllClick(int i){
        mHomeView.openOtherDetail(mhomeBeanlists,i);
    }

    //邀约点击参加
    public void setHomeItemJoinClick(int i){
        if (mhomeBeanlists.get(i).isJoin()){
            mHomeView.showDialog(IS_JOIN,Integer.parseInt(mhomeBeanlists.get(i).getInvitaionId()),2);
        }else{
            mHomeView.showDialog(NOT_JOIN,i,0);
        }
        //mHomeView.showHomeToast("第"+i+"个："+mhomeBeanlists.get(i).getCurrentNumber()+"");
    }

    //头部类别的点击事件
    @Override
    public void setHomeHeadClick(BaseQuickAdapter baseQuickAdapter, View view, int i){
        mHomeView.openHeadDetail(view.getTag()+"");
    }

    public void onPositive(@Nullable String token, int position, int type){
        mHimeModel.loadJoinInvitation(token, position, type, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                //mHomeView.showHomeToast("加入退出");
                String code = "";
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    code=jsonObject.getString("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (code.equals("200")){
                    if (type==0){
                        mHomeView.showHomeToast("加入成功！");
                        mhomeBeanlists.get(position).setJoin(mhomeBeanlists.get(position).isJoin()?false:true);
                        mHomeView.changeJoin();
                    }else if(type==2){
                        mHomeView.showHomeToast("退出成功！");
                    }
                }else if(code.equals("401")){
                    mHomeView.showHomeToast("请登录！");
                }

            }
        });
    }

}
