package com.example.vizax.with.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vizax.with.App;
import com.example.vizax.with.R;
import com.example.vizax.with.bean.MembersBean;
import com.example.vizax.with.util.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Young on 2016/9/13.
 */
public class UserImgListecyclerViewAdapter extends RecyclerView.Adapter<UserImgListecyclerViewAdapter.MyViewHolder> {
    ArrayList<MembersBean> mMembersBean;
    Context context;

    public UserImgListecyclerViewAdapter(ArrayList<MembersBean> mMembersBean,Context context) {
        this.mMembersBean = mMembersBean;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.invitation_user_avatar_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.with(context)
                .load(mMembersBean.get(position).getHeadUrl())
                .placeholder(R.drawable.user0)
                .transform(new CircleTransformation())
                .into(holder.itemInvitationUserlistImg);
        /**
         * 临时数据
         */
      /*  if (position % 5 == 0)
            holder.itemInvitationUserlistImg.setImageResource(R.drawable.user0);
        if (position % 5 == 1)
            holder.itemInvitationUserlistImg.setImageResource(R.drawable.user1);
        if (position % 5 == 2)
            holder.itemInvitationUserlistImg.setImageResource(R.drawable.user2);
        if (position % 5 == 3)
            holder.itemInvitationUserlistImg.setImageResource(R.drawable.user3);
        if (position % 5 == 4)
            holder.itemInvitationUserlistImg.setImageResource(R.drawable.user4);
        if (position % 5 == 5)
            holder.itemInvitationUserlistImg.setImageResource(R.drawable.user5);*/


    }

    @Override
    public int getItemCount() {
        return mMembersBean.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_invitation_userlist_img)
        ImageView itemInvitationUserlistImg;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
