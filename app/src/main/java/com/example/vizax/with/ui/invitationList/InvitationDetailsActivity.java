package com.example.vizax.with.ui.invitationList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.adapter.InvitationDetailsRecyclerViewAdapter;
import com.example.vizax.with.adapter.UserImgListecyclerViewAdapter;
import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.bean.MembersBean;

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

public class InvitationDetailsActivity extends SwipeBackActivity {
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
    private RecyclerView mRecyclerView;
    private InvitationDetailsRecyclerViewAdapter mAdapter;
    private UserImgListecyclerViewAdapter mUserImgAdapter;
    private RecyclerView mUserImgRecyclerView;
    private String mKeyTrackingMode;
    private SwipeBackLayout mSwipeBackLayout;
    private InvitationBean mInvitationBeen;
    private ArrayList<MembersBean> mMemberBean;
    private boolean join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        //获取list界面传过来的活动参与人信息
        getData();

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
    }

    private void initView() {
        itemInvitationOriginatorName.setText(mInvitationBeen.getOriginatorrealName());
        itemInvitationInvitationTime.setText(mInvitationBeen.getInvitationTime());
        itemInvitationPublishTime.setText(mInvitationBeen.getPublishTime());
        itemInvitationContents.setText(mInvitationBeen.getContent());
        itemInvitationPlace.setText(mInvitationBeen.getPlace());
        itemInvitationSexRequire.setText(mInvitationBeen.getSexRequire());
        itemInvitationNumber.setText(mInvitationBeen.getCurrentNumber()+"/"+mInvitationBeen.getTotalNumber());
        itemInvitationSexRequire.setText(mInvitationBeen.getSexRequire());
        join = mInvitationBeen.isJoin();
        if(join)
            itemInvitationJoinBtn.setImageResource(R.drawable.join_selected);
        else
            itemInvitationJoinBtn.setImageResource(R.drawable.join_unselected);
    }


    //获取list界面传过来的活动参与人信息
    public void getData() {
        Intent it = getIntent();
        if (it != null) {
            mInvitationBeen = it.getParcelableExtra("users");
            mMemberBean = it.getParcelableArrayListExtra("members");
        }
    }


    @OnClick({R.id.item_invitation_originator_imagVi, R.id.item_invitation_join_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_invitation_originator_imagVi:
                break;
            case R.id.item_invitation_join_btn:
                String  type;
                String contents = "null";
                if(join){
                    new MaterialDialog.Builder(InvitationDetailsActivity.this)
                            .content("是否退出该活动")
                            .positiveText("是")
                            .negativeText("否")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    Join.join(mInvitationBeen.getInvitaionId(), "2", new Join.changebtnsrc() {
                                        @Override
                                        public void setsrc() {
                                            mInvitationBeen.setJoin(false);
                                            itemInvitationJoinBtn.setImageResource(R.drawable.join_unselected);
                                        }
                                    });
                                }
                            }).show();

                }else {
                    if(mInvitationBeen.getCurrentNumber() == mInvitationBeen.getTotalNumber()) {
                        type = "1";
                        contents = "该活动已满人，是否请求发起者特批允许参加？";
                    }
                    else {
                        type = "0";
                        contents = "是否参加该活动?";
                    }
                    new MaterialDialog.Builder(InvitationDetailsActivity.this)
                            .content(contents)
                            .positiveText("是")
                            .negativeText("否")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    Join.join(mInvitationBeen.getInvitaionId(), type, new Join.changebtnsrc() {
                                        @Override
                                        public void setsrc() {
                                            mInvitationBeen.setJoin(true);
                                            itemInvitationJoinBtn.setImageResource(R.drawable.join_selected);
                                        }
                                    });
                                }
                            }).show();
                }
                    break;
        }
    }
}
