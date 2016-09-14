package com.example.vizax.with.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vizax.with.R;

/**
 * Created by Young on 2016/9/13.
 */
public class UserImgListecyclerViewAdapter extends RecyclerView.Adapter<UserImgListecyclerViewAdapter.MyViewHolder> {
    public UserImgListecyclerViewAdapter()
    {

    }
    @Override
    public UserImgListecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_invitation_user_img, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(UserImgListecyclerViewAdapter.MyViewHolder holder, int position) {
        if(position%5 == 0)
            holder.userimg.setImageResource(R.drawable.user0);
        if(position%5 == 1)
            holder.userimg.setImageResource(R.drawable.user1);
        if(position%5 == 2)
            holder.userimg.setImageResource(R.drawable.user2);
        if(position%5 == 3)
            holder.userimg.setImageResource(R.drawable.user3);
        if(position%5 == 4)
            holder.userimg.setImageResource(R.drawable.user4);
        if(position%5 == 5)
            holder.userimg.setImageResource(R.drawable.user5);



    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView userimg;

        public MyViewHolder(View itemView) {
            super(itemView);
            userimg = (ImageView) itemView.findViewById(R.id.item_invitation_userlist_img);
        }
    }
}
