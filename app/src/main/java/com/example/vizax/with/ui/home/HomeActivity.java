package com.example.vizax.with.ui.home;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.bean.HomeInvitationBean;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.util.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements HomeContact.View {

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.recyVi)
    RecyclerView recyVi;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private HomePresenter mhomePresenter;
    private LinearLayoutManager mManager;
    private HomeAdapter mHomeAdapter;
    private int lastId=2;
    private List<HomeInvitationBean.DataBean> mhomeBeanlists;
//    private int totalcount;
//    private int nowcount;

    @Override
    protected int initContentView() {
        return R.layout.home_activity;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        toolbar.setCenterText(getResources().getString(R.string.home_title));
        toolbar.setLeftIcon(getResources().getDrawable(R.drawable.user_img));
        toolbar.setRightIcon(getResources().getDrawable(R.drawable.calenda_94dp));
        mhomePresenter=new HomePresenter(this);
        mhomePresenter.initSwipe(swipeLayout);
        //mhomePresenter.addLoadAnimation(mHomeAdapter);
        mManager=new LinearLayoutManager(this);
        recyVi.setLayoutManager(mManager);
        recyVi.setHasFixedSize(true);
        mhomeBeanlists=new ArrayList<>();
        mHomeAdapter=new HomeAdapter(this,mhomeBeanlists);
        recyVi.setAdapter(mHomeAdapter);

        mhomePresenter.loadHomeData("zxw",0,0,0,10);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
                mhomePresenter.loadHomeData("zxw",0,0,0,10);
                //Log.i("HomeActivity1","onRefresh()");
            }
        });

        recyVi.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Log.i("HomeActivity2","("+dx+","+dy+")");
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && mManager.findLastVisibleItemPosition() + 1 ==mHomeAdapter.getItemCount()) {
                    swipeLayout.setRefreshing(false);
                    //showHomeToast(lastId+"最后一个id");
                    mhomePresenter.loadHomeData("zxw",0,0,lastId,10);
                    Log.i("HomeActivity1","onScrollStateChanged()");

                }
            }
        });

        mHomeAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if(i==0){
                    mhomePresenter.setHomeHeadClick(baseQuickAdapter,view,i);
                }else{
                    mhomePresenter.setHomeItemClick(baseQuickAdapter,view,i);
                }
            }
        });

//        mHomeAdapter.openLoadMore(true);
//        mHomeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                recyVi.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(nowcount>0){
//                            Log.i("load",">0");
//                            mHomeAdapter.notifyDataChangedAfterLoadMore(false);
//                        }else{
//                            Log.i("load","<0");
//                            mhomePresenter.loadHomeData("zxw", 0, 0, lastId, 10);
//                        }
//                    }
//                });
//            }
//        });
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    public void loadHomeFirstData(List<HomeInvitationBean.DataBean> lists,int lastId) {
        //showHomeToast(lists.toString());
        mHomeAdapter.setNewData(lists);
        mHomeAdapter.notifyDataSetChanged();
        this.lastId=lastId;
    }

    @Override
    public void loadHomeUpData(List<HomeInvitationBean.DataBean> lists,int lastId) {
        mHomeAdapter.setNewData(lists);
        mHomeAdapter.notifyDataSetChanged();
        this.lastId=lastId;
    }

    @Override
    public void loadHomeDownData3(List<HomeInvitationBean.DataBean> lists,int lastId) {
//        mHomeAdapter.addData(lists);
//        mHomeAdapter.notifyDataSetChanged();
//        mHomeAdapter.notifyDataChangedAfterLoadMore(lists, true);
//        nowcount=lists.size();
        mHomeAdapter.setNewData(lists);
        mHomeAdapter.notifyDataSetChanged();
        this.lastId=lastId;
    }

    @Override
    public void showHomeToast(String toast) {
        Toast toast1=Toast.makeText(this,toast,Toast.LENGTH_SHORT);
        toast1.show();
    }
}
