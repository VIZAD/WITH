package com.example.vizax.with.ui.home;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.vizax.with.R;
import com.example.vizax.with.bean.HomeInvitationBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class HomeHeadUrlAdapter extends BaseMultiItemQuickAdapter<HomeInvitationBean.DataBean.MembersBean> {
    private Context context;
    public HomeHeadUrlAdapter(Context context,List<HomeInvitationBean.DataBean.MembersBean> data) {
        super(data);
        this.context=context;
        addItemType(1, R.layout.item_invitation_user_img);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeInvitationBean.DataBean.MembersBean membersBean) {
        Picasso.with(context).load(membersBean.getHeadUrl()).into((ImageView) baseViewHolder.getView(R.id.item_invitation_userlist_img));
    }
}
