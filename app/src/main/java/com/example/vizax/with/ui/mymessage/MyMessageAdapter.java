package com.example.vizax.with.ui.mymessage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vizax.with.R;
import com.example.vizax.with.bean.MyMessageBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Xuhai on 2016/9/19.
 */

public class MyMessageAdapter extends SwipeMenuAdapter<MyMessageAdapter.DefaultViewHolder> {

    private MyMessageBean mDatas;

    public MyMessageAdapter(){}
    public MyMessageAdapter(MyMessageBean mDatas) {
        this.mDatas = mDatas;
    }

    private OnItemClickListener mOnItemClickListener;

    public MyMessageBean getmDatas() {
        return mDatas;
    }

    public void setmDatas(MyMessageBean mDatas) {
        this.mDatas = mDatas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    @Override
    public int getItemCount() {
        return mDatas.getData().size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_message,parent,false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, final int position) {
        if (holder!=null){
            holder.name.setText(mDatas.getData().get(position).getName());
            holder.content.setText(mDatas.getData().get(position).getContent());

            Date date = new Date(mDatas.getData().get(position).getSendTime());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            holder.time.setText(sdf.format(date));
            if (mDatas.getData().get(position).isReaded()){
                holder.unread.setVisibility(View.INVISIBLE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(position);
            }
        });

    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView content;
        TextView time;
        ImageView unread;

        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.message_item_name);
            content = (TextView) itemView.findViewById(R.id.message_item_content);
            time = (TextView) itemView.findViewById(R.id.message_item_time);
            unread = (ImageView) itemView.findViewById(R.id.message_item_unread);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
