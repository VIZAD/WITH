package com.example.vizax.with.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vizax.with.App;
import com.example.vizax.with.R;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.ui.invitationList.InvitationContact;
import com.example.vizax.with.util.SharedUtil;
import com.example.vizax.with.util.StringUtil;
import com.example.vizax.with.util.TimeUtil;

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

    private  InvitationContact.InvitationCallBack mInvitationCallBack;
    private ClickListerner clickListerner;
    private SetMemberNum setMemberNum;

    public InvitationRecyclerViewAdapter(Context context, InvitationBaseBean mData,int visible) {
        this.context = context;
        this.mData = mData;
        this.visible = visible;
    }

    public void setOnItemClickListener(ClickListerner clickListerner){

        this.clickListerner = clickListerner;
    }

    public void setMembersNum(SetMemberNum setNum){
        setMemberNum = setNum;
    }

    //回调接口
    public void setCallBack(InvitationContact.InvitationCallBack invitationCallBack){
        mInvitationCallBack = invitationCallBack;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.invitation_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String sex = getSexrequire(mData.getData().get(position).getSexRequire());
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
      //  mData.getData().get(position).setContent("[肇庆学院航空杯]2015年即将结束，我们将迎来本学期最后一场狼人杀比赛--航空杯狼人杀比赛，希望广大杀友踊跃报名！");//临时数据
        String contents = StringUtil.cutContents(mData.getData().get(position).getContent(),57);
        holder.itemInvitationContents.setText(contents);
        holder.itemInvitationInvitationTime.setText("活动时间:"+mData.getData().get(position).getInvitationTime());
        holder.itemInvitationPublishTime.setText(TimeUtil.getTime(mData.getData().get(position).getPublishTime()));
        holder.itemInvitationPlace.setText("活动地点:"+mData.getData().get(position).getPlace());
        holder.itemInvitationSexRequire.setText("性别要求:"+sex);
        holder.itemInvitationNumber.setText(mData.getData().get(position).getCurrentNumber()+"/"+mData.getData().get(position).getTotalNumber());

        /**
         * 参与人头像横向recyclerView
         */
        mAdapter = new UserImgListecyclerViewAdapter(mData.getData().get(position).getMembers());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        holder.mRecyclerView.setAdapter(mAdapter);

        holder.expend.setVisibility(visible);
        holder.expend.setOnClickListener(v -> mInvitationCallBack.press(null,position,null));

        holder.itemInvitationJoinBtn.setOnClickListener(v -> {
            String  type;
            String contents1 = "null";
            //如果符合性别要求
            if(Integer.parseInt(mData.getData().get(position).getSexRequire()) == 2 || SharedUtil.getInt(App.instance, FieldConstant.sex) == Integer.parseInt(mData.getData().get(position).getSexRequire())) {
                if (join) {
                    mInvitationCallBack.press("是否退出该活动", position, "2");

                } else {
                    if (mData.getData().get(position).getCurrentNumber() == mData.getData().get(position).getTotalNumber()) {
                        type = "0";
                        contents1 = "该活动已满人，是否请求发起者特批允许参加？";
                    } else {
                        type = "1";
                        contents1 = "是否参加该活动?";
                    }
                    mInvitationCallBack.press(contents1, position, type);
                    //setMemberNum.setNum(position);
                }
            }else {
                //TODO
            }

        });
        holder.itemInvitationOriginatorImagVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListerner != null)
                clickListerner.onAvatarOnclik(position,mData);
                else
                    System.out.println("null!!!!!!!!!!!");
            }
        });

        holder.itemInvitationRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListerner != null)
                clickListerner.onItemClick(position,mData);
                else
                    System.out.println("null!!!!!!!!!!!");
            }
        });



    }

    private String getSexrequire(String sexRequire) {
        String result = "不限";
        switch (sexRequire){
            case "0":
                result = "男生";
                break;
            case "1":
                result = "女生";
                break;
            case "2":
                result = "不限";
                break;
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return mData.getData() == null ? 0:mData.getData().size();
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
        @BindView(R.id.item_invitation_root)
        LinearLayout itemInvitationRoot;
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
    public interface ClickListerner{
        void onItemClick(int position,InvitationBaseBean invitationBaseBean);
        void onAvatarOnclik(int position,InvitationBaseBean invitationBaseBean);
    }
    public interface SetMemberNum {
        void setNum(int position);
    }
}
