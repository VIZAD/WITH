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
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.ui.userInformation.UserInformationActivity;
import com.example.vizax.with.util.SnackbarUtils;
import com.example.vizax.with.util.swipeback.ArrayUtil;

import net.mobctrl.views.SuperSwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvitationActivity extends AppCompatActivity implements InvitationContact.View {
    private static final String MY_INVITATION = "我发起的";
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
    private Intent it;
    private int visible;
    private InvitationPresenter mInvitationListPresenter;
    private int mMainClass;
    private SuperSwipeRefreshLayout refreshLayout;
    private MaterialDialog mEdit,mJoinDialog;
    public String token = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitation_activity);
        ButterKnife.bind(this);
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
    public void stopRefresh(){
        invitationRefresh.setLoadMore(false);
        invitationRefresh.setRefreshing(false);
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
                mInvitationListPresenter.pullLoadMore(InvitationActivity.this, mRecyclerView, visible, null, null);


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
        mMainClass = ArrayUtil.getArray(type == null?"足球":type);
        if (type != null) {
            if (type.equals(MY_INVITATION)) {
                visible = View.VISIBLE;
            } else {
                visible = View.GONE;
            }
        } else {
            type = "足球";
            visible = View.GONE;
        }

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
    public void OpenDetail(int position, InvitationBaseBean mData) {
        Intent it = new Intent(this, InvitationDetailsActivity.class);
        Bundle lBundle = new Bundle();
        Bundle memBundle = new Bundle();
        Bundle invitationBaseBeanBundle = new Bundle();
        memBundle.putParcelableArrayList("members",mData.getData().get(position).getMembers());
       // lBundle.putParcelable("users",mData.getData().get(position));
        invitationBaseBeanBundle.putParcelableArrayList("invitationlist", mData.getData());
        invitationBaseBeanBundle.putInt("index",position);
        //it.putExtras(lBundle);
        it.putExtras(memBundle);
        it.putExtras(invitationBaseBeanBundle);
        startActivityForResult(it,1);

    }

    @Override
    public void OpenUserInfor(int position, UserInforBean userInforBean) {
        Intent it = new Intent(this, UserInformationActivity.class);
        Bundle lBundle = new Bundle();
        lBundle.putParcelable("userInforBean", new UserInforBean().getData());
        it.putExtras(lBundle);
        startActivity(it);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean join = data.getBooleanExtra("join",false);
        int index = data.getIntExtra("index",0);
        mInvitationListPresenter.baseBean.getData().get(index).setJoin(join);
        mInvitationListPresenter.setNotifyChange();
       // mInvitationListPresenter.setAdapter(this, mRecyclerView, mInvitationListPresenter.baseBean,visible);
    }


}
