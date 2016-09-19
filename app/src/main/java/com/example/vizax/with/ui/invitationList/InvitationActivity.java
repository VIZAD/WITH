package com.example.vizax.with.ui.invitationList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.util.SnackbarUtils;

import net.mobctrl.views.SuperSwipeRefreshLayout;

import java.sql.SQLOutput;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvitationActivity extends AppCompatActivity implements InvitationContact.View {
    private static final String MY_INVITATION = "my";
    @BindView(R.id.baseToolBar)
    BaseToolBar mBaseToolBar;
    @BindView(R.id.activity_my_focus_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.invitation_root)
    LinearLayout mRoot;
    @BindView(R.id.invitation_refresh)
    SuperSwipeRefreshLayout invitationRefresh;
    private InvitationRecyclerViewAdapter mAdapter;
    private String type;
    private String centerTxt;
    private Intent it;
    private int visible;
    private InvitationPresenter mInvitationListPresenter;
    private int mMainClass;
    private SuperSwipeRefreshLayout refreshLayout;
    private MaterialDialog mEdit,mJoinDialog;
    private boolean haveOnCreate = true;     //标记是否onCreate() ,程序执行onCreate后标记true，程序执行onResume()后标记false,当程序返回该界面时刷新界面setadapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitation_activity);
        ButterKnife.bind(this);
        haveOnCreate = true;
        mInvitationListPresenter = new InvitationPresenter();
        mInvitationListPresenter.attachView(this);
        //初始化dialog
        initDialog(null,-1);
        //设置邀约列表的类型
        setType();
        //初始化recyclerView
        mInvitationListPresenter.getDataAndSetAdapter(this, mRecyclerView, visible, null, null);
        //初始化toolbar
        initToolbar();
        //初始化superSwipeRefreshLayout
        initSuperSwipeRefresh();
    }

    //初始化dialog
    private void initDialog(@Nullable String contents, int position) {
        mEdit = new MaterialDialog.Builder(this)
                .items("编辑","删除")
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        switch (which){
                            case 0:
                                break;
                            case 1:
                                break;
                        }
                    }
                }).build();
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

    }

    private void initSuperSwipeRefresh() {
        invitationRefresh.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                mInvitationListPresenter.getDataAndSetAdapter(InvitationActivity.this, mRecyclerView, visible, null, null);
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
                mInvitationListPresenter.pullLoadMore();
                invitationRefresh.setLoadMore(false);
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
        it = getIntent();
        type = it.getStringExtra("type");
        mMainClass = it.getIntExtra("mainClass", 0);
        if (type != null) {
            if (type.equals(MY_INVITATION)) {
                centerTxt = "我发起的";
                visible = View.VISIBLE;
            } else {
                centerTxt = type;
                visible = View.GONE;
            }
        } else {
            centerTxt = "我发起的";
            visible = View.VISIBLE;
        }

    }

    private void initToolbar() {
        mBaseToolBar.setCenterText(centerTxt);
        if (visible == View.VISIBLE) {
            showRightIcon();
        }
    }

    @Override
    public void showRightIcon() {
        mBaseToolBar.setRightIcon(R.drawable.ic_menu);
        mBaseToolBar.setRightViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainClass = R.array.sports;
                new MaterialDialog.Builder(InvitationActivity.this)
                        .items(mMainClass)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                            }
                        })
                        .show();
            }
        });

    }

    @Override
    public void showDialog(boolean type,@Nullable String contents, int position) {
        if (type)
            mEdit.show();
        else {
            initDialog(contents,position);
            mJoinDialog.show();
        }
    }


    @Override
    public void loadDataFailure() {
        SnackbarUtils.show(mRoot, "获取数据失败，请检查网络", 0, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("---resume---");
        if(!haveOnCreate) {
            System.out.println("更新");
            mInvitationListPresenter.setAdapter(this, mRecyclerView,visible);
        }else {
            System.out.println("不更新");
        }
        haveOnCreate = false;
    }
}
