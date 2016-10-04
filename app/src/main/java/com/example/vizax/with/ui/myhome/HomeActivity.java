package com.example.vizax.with.ui.myhome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.App;
import com.example.vizax.with.R;
import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.ui.Insist.InsistActivity;
import com.example.vizax.with.ui.changpsw.ChangePswActivity;
import com.example.vizax.with.ui.invitation.LuanchInvitationActivity;
import com.example.vizax.with.ui.invitationList.InvitationActivity;
import com.example.vizax.with.ui.invitationList.InvitationContact;
import com.example.vizax.with.ui.invitationList.InvitationDetailsActivity;
import com.example.vizax.with.ui.invitationList.InvitationPresenter;
import com.example.vizax.with.ui.myconcern.MyConcernActivity;
import com.example.vizax.with.ui.mymessage.MyMessageActivity;
import com.example.vizax.with.ui.userInformation.UserInformationActivity;
import com.example.vizax.with.util.LoadMoreRecyclerView;
import com.example.vizax.with.util.SnackbarUtils;
import com.example.vizax.with.util.StringUtil;
import com.example.vizax.with.util.filedownload.UpdateManager;

import net.mobctrl.views.SuperSwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements InvitationContact.View {
    private static final String MY_INVITATION = "我发起的";
    @BindView(R.id.toolbar)
    BaseToolBar mBaseToolBar;
    @BindView(R.id.sidebar_menu)
    LinearLayout mSideBar;
    @BindView(R.id.activity_my_focus_recyclerview)
    LoadMoreRecyclerView mRecyclerView;
    @BindView(R.id.invitation_root)
    LinearLayout mRoot;
    @BindView(R.id.invitation_refresh)
    SuperSwipeRefreshLayout invitationRefresh;
    @BindView(R.id.invitation_fab)
    FloatingActionButton fab;
    @BindView(R.id.drawerlayout_id)
    DrawerLayout mDrawerLayout;
    private InvitationRecyclerViewAdapter mAdapter;
    private String type, typeId = null;
    private int visible;
    private InvitationPresenter mInvitationListPresenter;
    private int mMainClass;
    private SuperSwipeRefreshLayout refreshLayout;
    private MaterialDialog mEdit, mJoinDialog, mJoing;
    public String token = "2";
    // private SwipeBackLayout mSwipeBackLayout;
    private int position;
    private SharedPreferences sp;
    private LinearLayout linearLayout_head;
    private HeadViewHolder headViewHolder;
    private SlideBarViewHolder slideBarViewHolder;


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
        linearLayout_head = (LinearLayout) getLayoutInflater().inflate(R.layout.home_head_item, null);
        headViewHolder = new HeadViewHolder(linearLayout_head);
        slideBarViewHolder = new SlideBarViewHolder(mSideBar);

        mRecyclerView.addHeaderView(linearLayout_head);
        mInvitationListPresenter = new InvitationPresenter();
        mInvitationListPresenter.attachView(this);
        //初始化dialog
        initDialog(null, -1);
        //设置邀约列表的类型

        mBaseToolBar.setCenterText(getResources().getString(R.string.home_title));
        mBaseToolBar.setLeftIcon(getResources().getDrawable(R.drawable.user_img));
        mBaseToolBar.setLeftViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showHomeToast("头像");
                mDrawerLayout.openDrawer(mSideBar);
            }
        });
        mBaseToolBar.setRightIcon(getResources().getDrawable(R.drawable.calenda_94dp));
        mBaseToolBar.setRightViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, InsistActivity.class);
                startActivity(intent);
            }
        });

        //初始化recyclerView
        // token  = User.token;
        mInvitationListPresenter.getDataAndSetAdapter(this, mRecyclerView, visible, null, null);
        //初始化toolbar
        initToolbar();
        //初始化superSwipeRefreshLayout
        initSuperSwipeRefresh();

        //设置swipback参数
        //mSwipeBackLayout = getSwipeBackLayout();
        //mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    //初始化dialog
    private void initDialog(@Nullable String contents, int position) {

        mJoinDialog = new MaterialDialog.Builder(this)
                .content(contents)
                .positiveText("是")
                .negativeText("否")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mInvitationListPresenter.onPositive(position);

                    }
                })
                .build();
        mJoing = new MaterialDialog.Builder(this)
                .content("正在处理请稍等...")
                .progress(true, 0)
                .build();

    }

    @Override
    public void stopRefresh() {
        invitationRefresh.setLoadMore(false);
        invitationRefresh.setRefreshing(false);
    }

    private void initSuperSwipeRefresh() {
        invitationRefresh.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                mInvitationListPresenter.getDataAndSetAdapter(HomeActivity.this, mRecyclerView, visible, typeId, null);
                invitationRefresh.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        invitationRefresh.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mInvitationListPresenter.pullLoadMore(HomeActivity.this, mRecyclerView, visible, null, null);


            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
    }

    private void setType() {
        typeId = 0 + "";
    }

    private void initToolbar() {
        mBaseToolBar.setCenterText(type);
        if (visible == View.GONE) {
            showRightIcon();
        }
    }

    @Override
    public void showRightIcon() {
        mBaseToolBar.setRightIcon(R.drawable.ic_menu);
        mBaseToolBar.setRightViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(HomeActivity.this)
                        .items(mMainClass)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                mBaseToolBar.setCenterText(text);
                                typeId = StringUtil.invitationIdUtil(String.valueOf(text));
                                mInvitationListPresenter.getDataAndSetAdapter(HomeActivity.this, mRecyclerView, visible, typeId, null);
                            }
                        })
                        .show();
            }
        });

    }

    @Override
    public void showDialog(boolean type, @Nullable String contents, int position) {
        this.position = position;
        if (type) {
            mEdit = new MaterialDialog.Builder(this)
                    .items("编辑", "删除")
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            switch (which) {
                                case 0:
                                    openEdit();
                                    break;
                                case 1:
                                    mInvitationListPresenter.deleteInvitation(position);
                                    break;
                            }
                        }
                    }).build();
            mEdit.show();
        } else {
            initDialog(contents, position);
            mJoinDialog.show();
        }
    }

    @Override
    public void showDiaolog() {
        mJoing.show();
    }

    @Override
    public void dismissDialog() {
        mJoing.dismiss();
    }


    @Override
    public void loadDataFailure() {
        SnackbarUtils.show(mRoot, "获取数据失败，请检查网络", 0, null);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openEdit() {
        InvitationBean invitationBean = mInvitationListPresenter.baseBean.getData().get(position);
        Intent it = new Intent(this, LuanchInvitationActivity.class);
        Bundle lBundle = new Bundle();
        lBundle.putParcelable("invitationBean", invitationBean);
        it.putExtras(lBundle);
        startActivity(it);
    }

    @Override
    public void OpenDetail(int position, InvitationBaseBean mData) {
        Intent it = new Intent(this, InvitationDetailsActivity.class);
        Bundle lBundle = new Bundle();
        Bundle memBundle = new Bundle();
        Bundle invitationBaseBeanBundle = new Bundle();
        memBundle.putParcelableArrayList("members", mData.getData().get(position).getMembers());
        // lBundle.putParcelable("users",mData.getData().get(position));
        invitationBaseBeanBundle.putParcelableArrayList("invitationlist", mData.getData());
        invitationBaseBeanBundle.putInt("index", position);
        //it.putExtras(lBundle);
        it.putExtras(memBundle);
        it.putExtras(invitationBaseBeanBundle);
        startActivityForResult(it, 1);

    }

    @Override
    public void OpenUserInfor(UserInforBean userInforBean) {
        Intent it = new Intent(this, UserInformationActivity.class);
        Bundle lBundle = new Bundle();
        System.out.println("1=" + userInforBean.getData().getName() + userInforBean.getData().getPhone());
        lBundle.putParcelable("userInforBean", userInforBean.getData());
        it.putExtras(lBundle);
        startActivity(it);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean join = data.getBooleanExtra("join", false);
        int index = data.getIntExtra("index", 0);
        mInvitationListPresenter.baseBean.getData().get(index).setMembers(data.getParcelableArrayListExtra("members"));
        mInvitationListPresenter.baseBean.getData().get(index).setJoin(join);
        mInvitationListPresenter.setNotifyChange();
        // mInvitationListPresenter.setAdapter(this, mRecyclerView, mInvitationListPresenter.baseBean,visible);
    }


    @OnClick(R.id.invitation_fab)
    public void onClick() {
        openLaunch();
        Toast.makeText(HomeActivity.this, "dianji", Toast.LENGTH_SHORT).show();
    }

    //发起活动
    @Override
    public void openLaunch() {
        Intent it = new Intent(this, LuanchInvitationActivity.class);
        it.putExtra("typeId", StringUtil.invitationIdUtil(type));
        startActivity(it);
    }


    //打开类别的详细界面
    @Override
    public void openHeadDetail(String string) {
        Intent intent = new Intent(this, InvitationActivity.class);
        intent.putExtra("type", string);
        startActivity(intent);
    }

     class HeadViewHolder {

        @BindView(R.id.iv_sport1)
        ImageView ivSport1;
        @BindView(R.id.iv_sport2)
        ImageView ivSport2;
        @BindView(R.id.iv_sport3)
        ImageView ivSport3;
        @BindView(R.id.ll_sport)
        LinearLayout llSport;
        @BindView(R.id.iv_study1)
        ImageView ivStudy1;
        @BindView(R.id.iv_study2)
        ImageView ivStudy2;
        @BindView(R.id.iv_study3)
        ImageView ivStudy3;
        @BindView(R.id.ll_study)
        LinearLayout llStudy;
        @BindView(R.id.iv_board_game1)
        ImageView ivBoardGame1;
        @BindView(R.id.iv_board_game2)
        ImageView ivBoardGame2;
        @BindView(R.id.iv_board_game3)
        ImageView ivBoardGame3;
        @BindView(R.id.ll_board_game)
        LinearLayout llBoardGame;
        @BindView(R.id.iv_online_game1)
        ImageView ivOnlineGame1;
        @BindView(R.id.iv_online_game2)
        ImageView ivOnlineGame2;
        @BindView(R.id.iv_online_game3)
        ImageView ivOnlineGame3;
        @BindView(R.id.ll_online_game)
        LinearLayout llOnlineGame;
        @BindView(R.id.iv_date1)
        ImageView ivDate1;
        @BindView(R.id.iv_date2)
        ImageView ivDate2;
        @BindView(R.id.iv_date3)
        ImageView ivDate3;
        @BindView(R.id.ll_date)
        LinearLayout llDate;
        @BindView(R.id.ll_other)
        LinearLayout llOther;

        HeadViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.iv_sport1, R.id.iv_sport2, R.id.iv_sport3, R.id.ll_sport, R.id.iv_study1, R.id.iv_study2, R.id.iv_study3, R.id.ll_study, R.id.iv_board_game1, R.id.iv_board_game2, R.id.iv_board_game3, R.id.ll_board_game, R.id.iv_online_game1, R.id.iv_online_game2, R.id.iv_online_game3, R.id.ll_online_game, R.id.iv_date1, R.id.iv_date2, R.id.iv_date3, R.id.ll_date, R.id.ll_other})
        public void onClick(View view) {
            HomeActivity.this.showToast("onclick" + view.getTag().toString());
            HomeActivity.this.openHeadDetail(view.getTag().toString());
        }

    }


    class SlideBarViewHolder {
        @BindView(R.id.my_info_lLayout)
        LinearLayout myInfoLLayout;
        @BindView(R.id.my_changepassword_txtVi)
        TextView myChangepasswordTxtVi;
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

        SlideBarViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        @OnClick({R.id.my_info_lLayout, R.id.my_invitation_txtVi, R.id.my_participation_txtVi, R.id.my_insist_txtVi, R.id.my_news_txtVi, R.id.my_concern_txtVi, R.id.my_setting_txtVi, R.id.my_update_txtVi, R.id.my_changepassword_txtVi} )
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {
                case R.id.my_info_lLayout:
                    HomeActivity.this.showToast("我的个人信息");
                    break;
                case R.id.my_changepassword_txtVi:
                    intent = new Intent(App.instance, ChangePswActivity.class);
                    startActivity(intent);
                    //showHomeToast("修改密码");
                    break;
                case R.id.my_invitation_txtVi:
                    intent = new Intent(HomeActivity.this, InvitationActivity.class);
                    intent.putExtra("type", "我发起的");
                    startActivity(intent);
                    break;
                case R.id.my_participation_txtVi:
                    intent = new Intent(HomeActivity.this, InvitationActivity.class);
                    intent.putExtra("type", "我参与的");
                    startActivity(intent);
                    break;
                case R.id.my_insist_txtVi:
                    intent = new Intent(HomeActivity.this, InsistActivity.class);
                    startActivity(intent);
                    break;
                case R.id.my_news_txtVi:
                    HomeActivity.this.showToast("我的信息");
                    intent = new Intent(HomeActivity.this, MyMessageActivity.class);
                    startActivity(intent);
                    break;
                case R.id.my_concern_txtVi:
                    intent = new Intent(HomeActivity.this, MyConcernActivity.class);
                    startActivity(intent);
                    break;
                case R.id.my_setting_txtVi:
                    HomeActivity.this.showToast("设置");
                    break;
                case R.id.my_update_txtVi:
                    //showHomeToast("检查更新");
                    new UpdateManager(HomeActivity.this).checkUpdate();
                    break;
            }
        }
    }
}