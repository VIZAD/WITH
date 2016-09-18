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
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/9/17.
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeInvitationBean.DataBean> {
    private Context context;
    public HomeAdapter(Context context, List<HomeInvitationBean.DataBean> data) {
        super(data);
        this.context=context;
        addItemType(HomeInvitationBean.DataBean.HOME_HEAD, R.layout.home_head_item);
        addItemType(HomeInvitationBean.DataBean.HOME_OTHER, R.layout.item_invitation);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeInvitationBean.DataBean dataBean) {

        Log.i("HomeAdapter",dataBean.toString());
        Log.i("HomeAdapter","getItemViewType():"+baseViewHolder.getItemViewType()+"");
        switch (baseViewHolder.getItemViewType()) {
            case HomeInvitationBean.DataBean.HOME_HEAD:
                break;
            case HomeInvitationBean.DataBean.HOME_OTHER:
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
                break;
        }
    }
}
