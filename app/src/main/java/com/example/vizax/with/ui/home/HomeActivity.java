package com.example.vizax.with.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.bean.MembersBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.ui.Insist.InsistActivity;
import com.example.vizax.with.ui.invitationList.InvitationActivity;
import com.example.vizax.with.ui.invitationList.InvitationDetailsActivity;
import com.example.vizax.with.ui.myconcern.MyConcernActivity;
import com.example.vizax.with.ui.userInformation.UserInformationActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeContact.View {

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.recyVi)
    RecyclerView recyVi;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.sidebar_menu)
    LinearLayout mSideBar;
    @BindView(R.id.drawerlayout_id)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.my_info_lLayout)
    LinearLayout myInfoLLayout;
    @BindView(R.id.my_invitation_txtVi)
    TextView myInvitationTxtVi;
    @BindView(R.id.my_participation_txtVi)
    TextView myParticipationTxtVi;
    @BindView(R.id.my_insist_txtVi)
    TextView myInsistTxtVi;
    @BindView(R.id.my_news_txtVi)
    TextView myNewsTxtVi;
    @BindView(R.id.my_concern_txtVi)
    TextView myConcernTxtVi;
    @BindView(R.id.my_setting_txtVi)
    TextView mySettingTxtVi;
    @BindView(R.id.my_update_txtVi)
    TextView myUpdateTxtVi;
    @BindView(R.id.my_changepassword_txtVi)
    TextView myChangepasswordTxtVi;
    private MaterialDialog mJoinDialog;
    private HomePresenter mhomePresenter;
    private LinearLayoutManager mManager;
    private HomeAdapter mHomeAdapter;
    private int lastId = 2;
    //private List<HomeInvitationBean.DataBean> mhomeBeanlists;
    private List<InvitationBean> mhomeBeanlists;
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
        //设置toolbar
        toolbar.setCenterText(getResources().getString(R.string.home_title));
        toolbar.setLeftIcon(getResources().getDrawable(R.drawable.user_img));
        toolbar.setLeftViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHomeToast("头像");
                mDrawerLayout.openDrawer(mSideBar);
            }
        });
        toolbar.setRightIcon(getResources().getDrawable(R.drawable.calenda_94dp));
        toolbar.setRightViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, InsistActivity.class);
                startActivity(intent);
            }
        });

        //新建Presenter
        mhomePresenter = new HomePresenter(this);

        //增加抽屉的活动范围
        mhomePresenter.addDrawerRange(mDrawerLayout);

        //初始化SwipeRefreshLayout
        mhomePresenter.initSwipe(swipeLayout);

        //mhomePresenter.addLoadAnimation(mHomeAdapter);

        //设置RecyclerView
        mManager = new LinearLayoutManager(this);
        recyVi.setLayoutManager(mManager);
        recyVi.setHasFixedSize(true);

        //初始化适配器
        mhomeBeanlists = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(this, mhomeBeanlists);
        recyVi.setAdapter(mHomeAdapter);

        //首次加载
        mhomePresenter.loadHomeData("zxw", 0, 0, 0, 10);

        //下拉刷新
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
                mhomePresenter.loadHomeData("zxw", 0, 0, 0, 10);
                //Log.i("HomeActivity1","onRefresh()");
            }
        });

        //上拉加载
        recyVi.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    //showHomeToast("上拉加载");
                    swipeLayout.setRefreshing(false);
                    mhomePresenter.loadHomeData("zxw", 0, 0, lastId, 10);
                }
                //Log.i("HomeActivity2","("+dx+","+dy+")");
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if (newState ==RecyclerView.SCROLL_STATE_IDLE && mManager.findLastVisibleItemPosition() + 1 ==mHomeAdapter.getItemCount()) {
//                    swipeLayout.setRefreshing(false);
//                    //showHomeToast(lastId+"最后一个id");
//                    mhomePresenter.loadHomeData("zxw",0,0,lastId,10);
//                    Log.i("HomeActivity1","onScrollStateChanged()");
//
//                }
            }
        });


        //内部点击事件
        mHomeAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (i == 0) {
                    mhomePresenter.setHomeHeadClick(baseQuickAdapter, view, i);
                } else {
                    mhomePresenter.setHomeItemClick(baseQuickAdapter, view, i);
                }
            }
        });

        //第三方上拉加载
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

    //首次加载，下拉刷新回调更改UI
    @Override
    public void loadHomeFirstData(List<InvitationBean> lists, int lastId) {
        //showHomeToast(lists.toString());
        mHomeAdapter.setNewData(lists);
        mHomeAdapter.notifyDataSetChanged();
        this.lastId = lastId;
    }

    //上拉加载回调更改UI
    @Override
    public void loadHomeDownData(List<InvitationBean> lists, int lastId) {
//        mHomeAdapter.addData(lists);
//        mHomeAdapter.notifyDataSetChanged();
//        mHomeAdapter.notifyDataChangedAfterLoadMore(lists, true);
//        nowcount=lists.size();
        mHomeAdapter.setNewData(lists);
        mHomeAdapter.notifyDataSetChanged();
        this.lastId = lastId;
    }

    //打印Toast
    @Override
    public void showHomeToast(String toast) {
        Toast toast1 = Toast.makeText(this, toast, Toast.LENGTH_SHORT);
        toast1.show();
    }

    //打开类别的详细界面
    @Override
    public void openHeadDetail(String string) {
        Intent intent = new Intent(this, InvitationActivity.class);
        intent.putExtra("type", string);
        startActivity(intent);
    }

    //打开下面邀约的详细界面
    @Override
    public void openOtherDetail(ArrayList<InvitationBean> mhomeBeanlists, int i) {
        Intent it = new Intent(this, InvitationDetailsActivity.class);
        Bundle memBundle = new Bundle();
        Bundle invitationBaseBeanBundle = new Bundle();
        memBundle.putParcelableArrayList("members", mhomeBeanlists.get(i).getMembers());
        invitationBaseBeanBundle.putParcelableArrayList("invitationlist", mhomeBeanlists);
        invitationBaseBeanBundle.putInt("index", i);
        it.putExtras(memBundle);
        it.putExtras(invitationBaseBeanBundle);
        startActivityForResult(it, 1);
    }

    //主界面参加或者取消活动更改UI
    @Override
    public void changeJoin() {
        mHomeAdapter.setNewData(mhomePresenter.mhomeBeanlists);
        mHomeAdapter.notifyDataSetChanged();
    }

    //接收下面邀约的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean join = data.getBooleanExtra("join", false);
        int index = data.getIntExtra("index", 0);
        MembersBean mem = data.getParcelableExtra("member");
        mhomePresenter.mhomeBeanlists.get(index).setJoin(join);
        mHomeAdapter.setNewData(mhomePresenter.mhomeBeanlists);
        mHomeAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.my_info_lLayout, R.id.my_invitation_txtVi, R.id.my_participation_txtVi, R.id.my_insist_txtVi, R.id.my_news_txtVi, R.id.my_concern_txtVi, R.id.my_setting_txtVi, R.id.my_update_txtVi, R.id.my_changepassword_txtVi} )
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.my_info_lLayout:
                showHomeToast("我的个人信息");
                break;
            case R.id.my_changepassword_txtVi:
                showHomeToast("修改密码");
                break;
            case R.id.my_invitation_txtVi:
                intent = new Intent(this, InvitationActivity.class);
                intent.putExtra("type", "我发起的");
                startActivity(intent);
                break;
            case R.id.my_participation_txtVi:
                intent = new Intent(this, InvitationActivity.class);
                intent.putExtra("type", "我参与的");
                startActivity(intent);
                break;
            case R.id.my_insist_txtVi:
                intent = new Intent(HomeActivity.this, InsistActivity.class);
                startActivity(intent);
                break;
            case R.id.my_news_txtVi:
                showHomeToast("我的信息");
                break;
            case R.id.my_concern_txtVi:
                intent = new Intent(HomeActivity.this, MyConcernActivity.class);
                startActivity(intent);
                break;
            case R.id.my_setting_txtVi:
                showHomeToast("设置");
                break;
            case R.id.my_update_txtVi:
                showHomeToast("检查更新");
                break;
        }
    }

    private void initDialog(@Nullable String contents, int position,int type) {
        mJoinDialog = new MaterialDialog.Builder(this)
                .content(contents)
                .positiveText("是")
                .negativeText("否")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mhomePresenter.onPositive("zxw",position,type);

                    }
                })
                .build();
    }

    @Override
    public void showDialog(@Nullable String contents, int position,int type) {
        initDialog(contents,position,type);
        mJoinDialog.show();
    }

    @Override
    public void OpenUserInfor(int position, UserInforBean userInforBean) {
        Intent it = new Intent(this, UserInformationActivity.class);
        Bundle lBundle = new Bundle();
        lBundle.putParcelable("userInforBean", new UserInforBean().getData());
        it.putExtras(lBundle);
        startActivity(it);
    }
    //hahaha
}
