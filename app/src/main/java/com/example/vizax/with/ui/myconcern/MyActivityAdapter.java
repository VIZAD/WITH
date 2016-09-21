package com.example.vizax.with.ui.myconcern;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.vizax.with.R;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by VIZAX on 2016/09/21.
 */

public class MyActivityAdapter extends BaseQuickAdapter<InvitationBaseBean> {

    private Context context;

    public MyActivityAdapter(Context context , List<InvitationBaseBean> data) {
        super(R.layout.invitation_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, InvitationBaseBean data) {
        System.out.println("convert");
        ImageView imageView = holder.getView(R.id.head_imgvi);
//        Picasso.with(context)
//                .load(data.getHeadUrl())
//                .into(imageView);
//        holder.setText(R.id.name_label_txtvi,data.getName())
//                .setText(R.id.description_hint_txtvi,"hint")
//                .setText(R.id.concern_btn,data.isIsConcerned()==true?"取关":"关注");

        holder.getView(R.id.name_label_txtvi);
        holder.getView(R.id.description_hint_txtvi);
        holder.getView(R.id.concern_btn);

        holder.setOnClickListener(R.id.head_imgvi, new OnItemChildClickListener())
                .setOnClickListener(R.id.name_label_txtvi, new OnItemChildClickListener())
                .setOnClickListener(R.id.concern_btn,new OnItemChildClickListener());

    }
}
