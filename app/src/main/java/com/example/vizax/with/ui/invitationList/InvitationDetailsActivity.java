package com.example.vizax.with.ui.invitationList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.adapter.InvitationDetailsRecyclerViewAdapter;
import com.example.vizax.with.adapter.UserImgListecyclerViewAdapter;
import com.example.vizax.with.bean.HomeInvitationBean;
import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.bean.MembersBean;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.util.TimeUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Young
 * 邀约详情页
 */

public class InvitationDetailsActivity extends SwipeBackActivity implements InvitationDetailContact.View {
    @BindView(R.id.item_invitation_originator_imagVi)
    ImageView itemInvitationImagVi;
    @BindView(R.id.item_invitation_join_btn)
    ImageView itemInvitationJoinBtn;
    @BindView(R.id.item_invitation_originator_name)
    TextView itemInvitationOriginatorName;
    @BindView(R.id.item_invitation_title)
    TextView itemInvitationTitle;
    @BindView(R.id.item_invitation_publish_time)
    TextView itemInvitationPublishTime;
    @BindView(R.id.item_invitation_expend)
    ImageView itemInvitationExpend;
    @BindView(R.id.item_invitation_contents)
    TextView itemInvitationContents;
    @BindView(R.id.item_invitation_icon)
    ImageView itemInvitationIcon;
    @BindView(R.id.item_invitation_invitation_time)
    TextView itemInvitationInvitationTime;
    @BindView(R.id.item_invitation_place)
    TextView itemInvitationPlace;
    @BindView(R.id.item_invitation_sex_require)
    TextView itemInvitationSexRequire;
    @BindView(R.id.item_invitation_card)
    LinearLayout itemInvitationCard;
    @BindView(R.id.item_invitation_userimglist)
    RecyclerView itemInvitationUserimglist;
    @BindView(R.id.item_invitation_number)
    TextView itemInvitationNumber;
    @BindView(R.id.event_details_recyclerview)
    RecyclerView eventDetailsRecyclerview;
    @BindView(R.id.baseToolBar)
    BaseToolBar baseToolBar;
    @BindView(R.id.yibaoming)
    TextView yibaoming;
    public final static String JOIN1 = "是否参加该活动?";
    public final static String QUIT = "是否退出该活动?";
    public final static String JOIN2 = "该活动已满人，是否请求发起者特批允许参加?";
    private RecyclerView mRecyclerView;
    private InvitationDetailsRecyclerViewAdapter mAdapter;
    private UserImgListecyclerViewAdapter mUserImgAdapter;
    private RecyclerView mUserImgRecyclerView;
    private String mKeyTrackingMode;
    private SwipeBackLayout mSwipeBackLayout;
   // public InvitationBean mInvitationBeen;
    public  ArrayList<InvitationBean> mInvitationBeanList;
    public  ArrayList<MembersBean> mMemberBean;
    private InvitationDetailContact.Presenter mPresenter;
    private MaterialDialog mMaterialDialog,mJoin;
    private int index;
    private HomeInvitationBean mHomeInvitationBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitation_detail_activity);
        ButterKnife.bind(this);
        //获取list界面传过来的活动参与人信息
        getData();
        mPresenter = new InvitationDetailPresenter(mMemberBean);
        mPresenter.attachView(this);
        //设置点击的item的index
        mPresenter.setPosition(index);
        //参与人头像list
        mUserImgRecyclerView = (RecyclerView) findViewById(R.id.item_invitation_userimglist);
        mUserImgAdapter = new UserImgListecyclerViewAdapter(mMemberBean);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mUserImgRecyclerView.setLayoutManager(linearLayoutManager);
        mUserImgRecyclerView.setAdapter(mUserImgAdapter);

        //参与人list
        mRecyclerView = (RecyclerView) findViewById(R.id.event_details_recyclerview);
        mAdapter = new InvitationDetailsRecyclerViewAdapter(this, mMemberBean);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);


        //设置swipback参数
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        initView();
        setResultData();
    }

    private void initView() {

        itemInvitationOriginatorName.setText(mInvitationBeanList.get(index).getOriginatorrealName());
        itemInvitationInvitationTime.setText(mInvitationBeanList.get(index).getInvitationTime());
        itemInvitationPublishTime.setText(TimeUtil.getTime(mInvitationBeanList.get(index).getPublishTime()));
        itemInvitationContents.setText(mInvitationBeanList.get(index).getContent());
        itemInvitationPlace.setText(mInvitationBeanList.get(index).getPlace());
        itemInvitationSexRequire.setText(mInvitationBeanList.get(index).getSexRequire());
        itemInvitationNumber.setText(mInvitationBeanList.get(index).getCurrentNumber() + "/" + mInvitationBeanList.get(index).getTotalNumber());
        itemInvitationSexRequire.setText(mInvitationBeanList.get(index).getSexRequire());

        mJoin = new MaterialDialog.Builder(this)
                .content("正在处理请稍后")
                .progress(true,0)
                .build();

        if (mInvitationBeanList.get(index).isJoin()) {
            itemInvitationJoinBtn.setImageResource(R.drawable.join_selected);
            //System.out.println("true!");
        }
        else {
            itemInvitationJoinBtn.setImageResource(R.drawable.join_unselected);
           // System.out.println("false!");
        }
        initToolbar();
    }
    //初始化toolbar
    private void initToolbar() {
        baseToolBar.setCenterText("活动详情");
        baseToolBar.setLeftViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvitationDetailsActivity.this.finish();
            }
        });
    }


    //获取list界面传过来的活动参与人信息
    public void getData() {
        Intent it = getIntent();
        if (it != null) {
            mInvitationBeanList = it.getParcelableArrayListExtra("invitationlist");
            index = it.getIntExtra("index",0);
            mMemberBean = it.getParcelableArrayListExtra("members");
        }
    }


    @OnClick({R.id.item_invitation_originator_imagVi, R.id.item_invitation_join_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_invitation_originator_imagVi:
                break;
            case R.id.item_invitation_join_btn:
                mPresenter.pressJoin(mInvitationBeanList.get(index));
                break;
        }
    }

    /**
     * 点击参加邀约显示dialog
     * @param contents
     */
    @Override
    public void showDialog(String contents) {
        mMaterialDialog = new MaterialDialog.Builder(this)
                .content(contents)
                .positiveText("是")
                .negativeText("否")
                .onPositive((dialog, which) -> mPresenter.onPositive(mInvitationBeanList.get(index)))
                .show();
    }

    /**
     * 改变参加邀约按钮背景色
     */
    @Override
    public void changeBtnSrc() {
        //InvitationPresenter.mInvitationBaseBean.getData().get(position).setJoin( InvitationPresenter.mInvitationBaseBean.getData().get(position).isJoin() ? (false):(true));
        itemInvitationJoinBtn.setImageResource(((mInvitationBeanList.get(index).isJoin()) ? R.drawable.join_selected:R.drawable.join_unselected));
       // mInvitationBeanList.get(index).setJoin(mInvitationBeanList.get(index).isJoin()?false:true);
        setResultData();
    }

    @Override
    public void removeMember(int position) {
        mAdapter.notifyItemRemoved(position);
        mUserImgAdapter.notifyItemRemoved(position);
    }

    @Override
    public void addMember(int position) {
        mAdapter.notifyItemChanged(position);
        mUserImgAdapter.notifyItemChanged(position);
    }

    @Override
    public void showDialog() {
        mJoin.show();
    }

    @Override
    public void dismissDialog() {
        mJoin.dismiss();
    }

    //已参与人数加一
    @Override
    public void addNum() {
        int num = Integer.parseInt(mInvitationBeanList.get(index).getCurrentNumber()) + 1;
        itemInvitationNumber.setText(num + "/" + mInvitationBeanList.get(index).getTotalNumber());

    }

    //已参与人数减一
    @Override
    public void reduceNum() {
        int num = Integer.parseInt(mInvitationBeanList.get(index).getCurrentNumber()) - 1;
        itemInvitationNumber.setText(num + "/" + mInvitationBeanList.get(index).getTotalNumber());
    }

    private void setResultData() {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean("join", mInvitationBeanList.get(index).isJoin());
        bundle.putInt("index",index);
        bundle.putParcelableArrayList("members",mMemberBean);
        intent.putExtras(bundle);
        setResult(1, intent);
    }


}
