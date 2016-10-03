package com.example.vizax.with.ui.invitationList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.ui.invitation.LuanchInvitationActivity;
import com.example.vizax.with.ui.userInformation.UserInformationActivity;
import com.example.vizax.with.util.SnackbarUtils;
import com.example.vizax.with.util.ArrayUtil;
import com.example.vizax.with.util.StringUtil;

import net.mobctrl.views.SuperSwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class InvitationActivity extends SwipeBackActivity implements InvitationContact.View {
    private static final String MY_INVITATION = "我发起的";
    @BindView(R.id.baseToolBar)
    BaseToolBar mBaseToolBar;
    @BindView(R.id.activity_my_focus_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.invitation_root)
    LinearLayout mRoot;
    @BindView(R.id.invitation_refresh)
    SuperSwipeRefreshLayout invitationRefresh;
    @BindView(R.id.invitation_fab)
    FloatingActionButton fab;
    private InvitationRecyclerViewAdapter mAdapter;
    private String type,typeId;
    private Intent it;
    private int visible;
    private InvitationPresenter mInvitationListPresenter;
    private int mMainClass;
    private SuperSwipeRefreshLayout refreshLayout;
    private MaterialDialog mEdit, mJoinDialog, mJoing;
    public String token = "2";
    private SwipeBackLayout mSwipeBackLayout;
    private int position;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitation_activity);
        ButterKnife.bind(this);
        mInvitationListPresenter = new InvitationPresenter();
        mInvitationListPresenter.attachView(this);
        //初始化dialog
        initDialog(null, -1);
        //设置邀约列表的类型
        setType();
        //初始化recyclerView
        // token  = User.token;
        mInvitationListPresenter.getDataAndSetAdapter(this, mRecyclerView, visible, typeId, null);
        //初始化toolbar
        initToolbar();
        //初始化superSwipeRefreshLayout
        initSuperSwipeRefresh();

        //设置swipback参数
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
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
                mInvitationListPresenter.getDataAndSetAdapter(InvitationActivity.this, mRecyclerView, visible, typeId, null);
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
        mMainClass = ArrayUtil.getArray(type);
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
    typeId = StringUtil.invitationIdUtil(String.valueOf(type));
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
                                mBaseToolBar.setCenterText(text);
                                typeId = StringUtil.invitationIdUtil(String.valueOf(text));
                                mInvitationListPresenter.getDataAndSetAdapter(InvitationActivity.this, mRecyclerView, visible,typeId,null);
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
                    .itemsCallback((dialog, itemView, which, text) -> {
                        switch (which) {
                            case 0:
                                openEdit();
                                break;
                            case 1:
                                mInvitationListPresenter.deleteInvitation(position);
                                break;
                        }
                    }).build();
            mEdit.show();
        }
        else {
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
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
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
    public void OpenUserInfor( UserInforBean userInforBean) {
        Intent it = new Intent(this, UserInformationActivity.class);
        Bundle lBundle = new Bundle();
        System.out.println("1="+userInforBean.getData().getName()+userInforBean.getData().getPhone());
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
        Toast.makeText(InvitationActivity.this,"dianji",Toast.LENGTH_SHORT).show();
    }
    //发起活动
    @Override
    public void openLaunch() {
        Intent it = new Intent(this, LuanchInvitationActivity.class);
        it.putExtra("typeId", StringUtil.invitationIdUtil(type));
        startActivity(it);
    }
}
