package com.example.vizax.with.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vizax.with.R;
import com.example.vizax.with.bean.MembersBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Young on 2016/9/13.
 */
public class InvitationDetailsRecyclerViewAdapter extends RecyclerView.Adapter<InvitationDetailsRecyclerViewAdapter.MyViewHolder> {


    private ArrayList<MembersBean> mMembersBean;
    private Context context;

    public InvitationDetailsRecyclerViewAdapter(Context context, ArrayList<MembersBean> mMembersBean) {
        this.mMembersBean = mMembersBean;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_applicants, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (position == 0) {
            holder.itemApplicantsOrginatorDetail.setVisibility(View.VISIBLE);
            holder.getItemApplicantsUsersDetail.setVisibility(View.VISIBLE);
        } else {
            holder.itemApplicantsRelativelayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (holder.remove.getVisibility() == View.GONE)
                        holder.remove.setVisibility(View.VISIBLE);
                    else
                        holder.remove.setVisibility(View.GONE);
                    return true;
                }
            });
        }
        holder.itemApplicantsUsername.setText(mMembersBean.get(position).getRealName());
        holder.itemApplicantsUsersex.setText(mMembersBean.get(position).getSex());
        holder.itemApplicantsUserphone.setText(mMembersBean.get(position).getPhone());
       // holder.itemApplicantsUserimg.setImageURI(Uri.parse(mMembersBean.get(position).getHeadUrl()));

    }

    @Override
    public int getItemCount() {
        return mMembersBean.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_applicants_userimg)
        ImageView itemApplicantsUserimg;
        @BindView(R.id.item_applicants_username)
        TextView itemApplicantsUsername;
        @BindView(R.id.item_applicants_usersex)
        TextView itemApplicantsUsersex;
        @BindView(R.id.item_applicants_userphone)
        TextView itemApplicantsUserphone;
        @BindView(R.id.item_applicants_orginator_detail)
        TextView itemApplicantsOrginatorDetail;
        @BindView(R.id.item_applicants_usersdetail)
        TextView getItemApplicantsUsersDetail;
        @BindView(R.id.item_applicants_relativelayout)
        RelativeLayout itemApplicantsRelativelayout;
        @BindView(R.id.tiren)
        ImageView remove;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
