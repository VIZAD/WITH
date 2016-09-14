package com.example.vizax.with.ui.demo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.widget.ImageView;

import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseSwipeBackActivity;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.util.AnimationUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.codetail.widget.RevealLinearLayout;

/**
 * Created by Administrator on 2016/9/14.
 */

public class DemoSwipBackActivity extends BaseSwipeBackActivity {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.activity_main)
    RevealLinearLayout activityMain;

    @Override
    protected int initContentView() {
        return R.layout.main_activity;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        /*Picasso.with(this)
                .load(R.drawable.demo)
                .into(img);*/
        AnimationUtil.showCircularReveal(img,2,500,500,2000);
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

}
