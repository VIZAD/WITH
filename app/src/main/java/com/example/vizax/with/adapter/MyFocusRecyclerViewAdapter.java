package com.example.vizax.with.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vizax.with.ui.EventDetailsActivity;
import com.example.vizax.with.R;

/**
 * Created by Young on 2016/9/13.
 */
public class MyFocusRecyclerViewAdapter extends RecyclerView.Adapter<MyFocusRecyclerViewAdapter.MyViewHolder> {
    Context context;
    UserImgListecyclerViewAdapter mAdapter;
    int visible = View.GONE;
    public MyFocusRecyclerViewAdapter(Context context)
    {
        this.context = context;
    }
    @Override
    public MyFocusRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_invitation, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyFocusRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textView.setText("阿道克"+position);
        mAdapter = new UserImgListecyclerViewAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        holder.mRecyclerView.setAdapter(mAdapter);
        holder.expend.setVisibility(visible);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, EventDetailsActivity.class);
                context.startActivity(it);
            }
        });

    }
    public void setExpend(int visible){
        this.visible = visible;
    }
    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView mRecyclerView;
        LinearLayout linearLayout;
        ImageView expend;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_invitation_name);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_invitation_userimglist);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_invitation_card);
            expend = (ImageView) itemView.findViewById(R.id.item_invitation_expend);
        }
    }

}
