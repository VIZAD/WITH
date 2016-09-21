package com.example.vizax.with.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.vizax.with.R;
import com.example.vizax.with.bean.HomeInvitationBean;
import com.example.vizax.with.bean.InvitationBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/9/17.
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<InvitationBean> {
    private Context context;
    public HomeAdapter(Context context, List<InvitationBean> data) {
        super(data);
        this.context=context;
        addItemType(InvitationBean.HOME_HEAD, R.layout.home_head_item);
        addItemType(InvitationBean.HOME_OTHER, R.layout.invitation_item);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, InvitationBean dataBean) {

        //Log.i("HomeAdapter",dataBean.toString());
        //Log.i("HomeAdapter","getItemViewType():"+baseViewHolder.getItemViewType()+"");
        switch (baseViewHolder.getItemViewType()) {
            case InvitationBean.HOME_HEAD:
                baseViewHolder.setOnClickListener(R.id.ll_sport,new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.ll_study,new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.ll_board_game,new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.ll_online_game,new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.ll_date,new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.ll_other,new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.iv_sport1,new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.iv_sport2,new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.iv_sport3,new OnItemChildClickListener());
                break;
            case InvitationBean.HOME_OTHER:
                //Picasso.with(context).load(dataBean.getOriginatorHeadUrl()).into((ImageView) baseViewHolder.getView(R.id.item_invitation_originator_imagVi));
                baseViewHolder.setText(R.id.item_invitation_originator_name,dataBean.getOriginatorNickname());
                baseViewHolder.setText(R.id.item_invitation_title,dataBean.getTitle());
                baseViewHolder.setText(R.id.item_invitation_publish_time,dataBean.getPublishTime());
                baseViewHolder.setText(R.id.item_invitation_contents,dataBean.getContent());
                baseViewHolder.setText(R.id.item_invitation_invitation_time,"时间："+dataBean.getInvitationTime());
                baseViewHolder.setText(R.id.item_invitation_place,"地点："+dataBean.getPlace());
                baseViewHolder.setText(R.id.item_invitation_sex_require,"性别要求："+dataBean.getSexRequire());
                baseViewHolder.setText(R.id.item_invitation_number,dataBean.getCurrentNumber()+"/"+dataBean.getTotalNumber());
                //HomeHeadUrlAdapter homeHeadUrlAdapter=new HomeHeadUrlAdapter(context,dataBean.getMembers());
                //((RecyclerView)baseViewHolder.getView(R.id.item_invitation_userimglist)).setAdapter(homeHeadUrlAdapter);
                if (dataBean.isJoin()){
                    baseViewHolder.setImageResource(R.id.item_invitation_join_btn,R.drawable.join_selected);
                }else if (!dataBean.isJoin()){
                    baseViewHolder.setImageResource(R.id.item_invitation_join_btn,R.drawable.join_unselected);
                }

                baseViewHolder.setOnClickListener(R.id.item_invitation_originator_imagVi,new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.item_invitation_contents,new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.item_invitation_join_btn,new OnItemChildClickListener());
                break;
        }
    }
}
