package com.example.vizax.with.ui.myconcern;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.vizax.with.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by apple1 on 2016/9/20.
 */
public class TestAdapter extends BaseQuickAdapter<MyConcern> {

    private Context context;

    public TestAdapter(int layoutResId, List<MyConcern> data, Context context) {
        super(R.layout.user_tab_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, MyConcern data) {
        ImageView imageView = holder.getView(R.id.head_imgvi);
        Picasso.with(context)
                .load(data.getData().get(0).getHeadUrl())
                .into(imageView);

    }
}
