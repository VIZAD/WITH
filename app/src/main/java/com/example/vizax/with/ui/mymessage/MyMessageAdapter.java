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

    private List<MyMessageBean.DataBean> mDatas;

    public List<MyMessageBean.DataBean> getmDatas() {
        return mDatas;
    }

    private OnItemClickListener mOnItemClickListener;

    public MyMessageAdapter(List<MyMessageBean.DataBean> mDatas) {
        this.mDatas = mDatas;
    }

    public void setmDatas(List<MyMessageBean.DataBean> mDatas) {
        this.mDatas = mDatas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
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
            holder.name.setText(mDatas.get(position).getName());
            holder.content.setText(mDatas.get(position).getContent());

            Date date = new Date(mDatas.get(position).getSendTime());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            holder.time.setText(sdf.format(date));
            if (mDatas.get(position).isReaded()){
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