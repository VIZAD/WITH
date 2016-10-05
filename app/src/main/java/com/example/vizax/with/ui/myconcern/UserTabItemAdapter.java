package com.example.vizax.with.ui.myconcern;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.vizax.with.R;
import com.example.vizax.with.util.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class UserTabItemAdapter extends BaseQuickAdapter<MyConcern.DataBean> {

        private Context context;
        private int i = 0;
        private int changed = 0;

        public UserTabItemAdapter( Context context ,List<MyConcern.DataBean> data) {
            super(R.layout.user_tab_item, data);
            this.context = context;
        }

        @Override
        protected void convert(BaseViewHolder holder, MyConcern.DataBean data) {
            System.out.println("convert");
            ImageView imageView = holder.getView(R.id.head_imgvi);
            Picasso.with(context)
                    .load(data.getHeadUrl())
                    .placeholder(R.drawable.user0)
                    .transform(new CircleTransformation())
                    .into(imageView);
            holder.setText(R.id.name_label_txtvi,data.getName())
                    .setText(R.id.description_hint_txtvi,"hint")
                    .setText(R.id.concern_btn,data.isConcerned()==true?"取关":"关注");

            holder.getView(R.id.name_label_txtvi);
            holder.getView(R.id.description_hint_txtvi);
            holder.getView(R.id.concern_btn);

            holder.setOnClickListener(R.id.head_imgvi, new OnItemChildClickListener())
                    .setOnClickListener(R.id.name_label_txtvi, new OnItemChildClickListener())
                    .setOnClickListener(R.id.concern_btn,new OnItemChildClickListener());

        }

        public void TextChange(int position) {

            notifyItemChanged(position);
        }

    }
//    private Context context;
//    private List<Data> mDatas;
//
//    public UserTabItemAdapter(Context context, List<Data> mDatas) {
//        this.context = context;
//        this.mDatas = mDatas;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.user_tab_item, parent,
//                false));
//
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.headIv.setImageResource(mDatas.get(position).getHead());
//        holder.text1Tv.setText(mDatas.get(position).getText1());
//        holder.text2Tv.setText(mDatas.get(position).getText2());
//        holder.followIv.setBottom(mDatas.get(position).getFollow());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDatas.size();
//    }
//
//
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView headIv;
//        TextView text1Tv;
//        TextView text2Tv;
//        Button followIv;
//
//        public MyViewHolder(View view) {
//            super(view);
//            headIv = (ImageView) view.findViewById(R.id.head_imgvi);
//            text1Tv = (TextView) view.findViewById(R.id.name_label_txtvi);
//            text2Tv = (TextView) view.findViewById(R.id.description_hint_txtvi);
//            followIv = (Button) view.findViewById(R.id.concern_btn);
//        }
//    }


