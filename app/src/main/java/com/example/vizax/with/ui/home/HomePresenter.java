package com.example.vizax.with.ui.home;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.vizax.with.bean.HomeInvitationBean;
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
    private HomeInvitationBean mHomeInvitationBean;
    private List<HomeInvitationBean.DataBean> mhomeBeanlists;

    public HomePresenter(HomeActivity homeActivity){
        this.mHomeView=homeActivity;
        mHimeModel=new HomeModel();
        mhomeBeanlists=new ArrayList<HomeInvitationBean.DataBean>();
    }

    public void addHead(){
        mhomeBeanlists.add(new HomeInvitationBean.DataBean(1));
    }

    @Override
    public void loadHomeData(String token, int typeId, int userId, int lastInvitationId, int limit) {
        mHimeModel.loadHomeData(token, typeId, userId, lastInvitationId, limit, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {


                mHomeView.showHomeToast("值："+lastInvitationId);
                    mHomeInvitationBean= GsonUtil.toString(response,HomeInvitationBean.class);
                    if (mHomeInvitationBean.getCode()==200){
                        //mHomeView.showHomeToast(mHomeInvitationBean.getMsg());
                        if(lastInvitationId==0){
                            //mHomeView.showHomeToast("下拉："+mHomeInvitationBean.getData().size());
                            mhomeBeanlists.clear();
                            addHead();
                            for (int i=0;i<mHomeInvitationBean.getData().size();i++){
                                HomeInvitationBean.DataBean dataBean=mHomeInvitationBean.getData().get(i);
                                dataBean.setItemType(2);
                                mhomeBeanlists.add(dataBean);
                            }
                            //mHomeView.showHomeToast("下拉："+mhomeBeanlists.size());
                        }else{
                            //mHomeView.showHomeToast("上拉："+mHomeInvitationBean.getData().size());
                            for (int i=0;i<mHomeInvitationBean.getData().size();i++){
                                HomeInvitationBean.DataBean dataBean=mHomeInvitationBean.getData().get(i);
                                dataBean.setItemType(2);
                                mhomeBeanlists.add(dataBean);
                            }
                            //mHomeView.showHomeToast("上拉："+mhomeBeanlists.size());
                        }
                        mHomeView.loadHomeFirstData(mhomeBeanlists);
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

}
