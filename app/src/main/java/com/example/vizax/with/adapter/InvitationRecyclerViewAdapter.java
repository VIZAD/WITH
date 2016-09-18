package com.example.vizax.with.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.ui.invitationList.InvitationDetailsActivity;
import com.example.vizax.with.ui.invitationList.Join;
import com.example.vizax.with.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Young on 2016/9/13.
 */
public class InvitationRecyclerViewAdapter extends RecyclerView.Adapter<InvitationRecyclerViewAdapter.MyViewHolder> {
    Context context;
    UserImgListecyclerViewAdapter mAdapter;
    int visible = View.GONE;
    private InvitationBaseBean mData;

    public InvitationRecyclerViewAdapter(Context context, InvitationBaseBean mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_invitation, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        boolean join = mData.getData().get(position).isJoin();
        if(join) {
            System.out.println("已参加");
            holder.itemInvitationJoinBtn.setImageResource(R.drawable.join_selected);
        }
        else {
            System.out.println("未参加");
            holder.itemInvitationJoinBtn.setImageResource(R.drawable.join_unselected);
        }
        holder.itemInvitationOriginatorName.setText(mData.getData().get(position).getOriginatorNickname());
        holder.itemInvitationTitle.setText(mData.getData().get(position).getTitle());
        mData.getData().get(position).setContent("[肇庆学院航空杯]2015年即将结束，我们将迎来本学期最后一场狼人杀比赛--航空杯狼人杀比赛，希望广大杀友踊跃报名！");//临时数据
        String contents = StringUtil.cutContents(mData.getData().get(position).getContent(),57);
        holder.itemInvitationContents.setText(contents);
        holder.itemInvitationInvitationTime.setText(mData.getData().get(position).getInvitationTime());
        holder.itemInvitationPublishTime.setText(mData.getData().get(position).getPublishTime());
        holder.itemInvitationPlace.setText(mData.getData().get(position).getPlace());
        holder.itemInvitationSexRequire.setText(mData.getData().get(position).getSexRequire());
        holder.itemInvitationNumber.setText(mData.getData().get(position).getCurrentNumber()+"/"+mData.getData().get(position).getTotalNumber());
        mAdapter = new UserImgListecyclerViewAdapter(mData.getData().get(position).getMembers());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        holder.mRecyclerView.setAdapter(mAdapter);
        holder.expend.setVisibility(visible);
        holder.expend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
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
                        })
                        .show();
            }
        });

        holder.itemInvitationJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  type;
                String contents = "null";
                    if(join){
                        new MaterialDialog.Builder(context)
                                .content("是否退出该活动")
                                .positiveText("是")
                                .negativeText("否")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Join.join(mData.getData().get(position).getInvitaionId(), "2", new Join.changebtnsrc() {
                                            @Override
                                            public void setsrc() {
                                                //holder.itemInvitationJoinBtn.setImageResource(R.drawable.join_unselected);
                                                mData.getData().get(position).setJoin(false);
                                                System.out.println("!!!!!!"+mData.getData().get(position).isJoin());
                                                notifyItemChanged(position);
                                            }
                                        });
                                    }
                                }).show();

                    }else {
                        if(mData.getData().get(position).getCurrentNumber() == mData.getData().get(position).getTotalNumber()) {
                            type = "1";
                            contents = "该活动已满人，是否请求发起者特批允许参加？";
                        }
                        else {
                            type = "0";
                            contents = "是否参加该活动?";
                        }
                        new MaterialDialog.Builder(context)
                                .content(contents)
                                .positiveText("是")
                                .negativeText("否")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    Join.join(mData.getData().get(position).getInvitaionId(), type, new Join.changebtnsrc() {
                                                        @Override
                                                        public void setsrc() {
                                                            // holder.itemInvitationJoinBtn.setImageResource(R.drawable.join_selected);
                                                            mData.getData().get(position).setJoin(true);
                                                            notifyItemChanged(position);
                                                        }
                                                    });
                                                }
                                            }).show();
                    }


            }
        });


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, InvitationDetailsActivity.class);
                Bundle lBundle = new Bundle();
                Bundle memBundle = new Bundle();
                memBundle.putParcelableArrayList("members",mData.getData().get(position).getMembers());
                lBundle.putParcelable("users",mData.getData().get(position));
                it.putExtras(lBundle);
                it.putExtras(memBundle);
                context.startActivity(it);
            }
        });



    }

    public void setExpend(int visible) {
        this.visible = visible;
    }

    @Override
    public int getItemCount() {

        return mData.getData().size();

    }

    @OnClick({R.id.item_invitation_originator_imagVi, R.id.item_invitation_join_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_invitation_originator_imagVi:
                break;
            case R.id.item_invitation_join_btn:
                break;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView mRecyclerView;
        LinearLayout linearLayout;
        ImageView expend;
        @BindView(R.id.item_invitation_title)
        TextView itemInvitationTitle;
        @BindView(R.id.item_invitation_originator_imagVi)
        ImageView itemInvitationOriginatorImagVi;
        @BindView(R.id.item_invitation_originator_name)
        TextView itemInvitationOriginatorName;
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
        @BindView(R.id.item_invitation_join_btn)
        ImageView itemInvitationJoinBtn;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_invitation_userimglist);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_invitation_card);
            expend = (ImageView) itemView.findViewById(R.id.item_invitation_expend);
        }
    }


}