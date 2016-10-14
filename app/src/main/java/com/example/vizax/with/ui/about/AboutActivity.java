package com.example.vizax.with.ui.about;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.util.AnimationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by prj on 2016/10/13.
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.about_avatar_iv)
    ImageView aboutAvatarIv;
    @BindView(R.id.tv_pro_address)
    TextView tvProAddress;
    @BindView(R.id.about_profile_rl)
    RelativeLayout aboutProfileRl;
    @BindView(R.id.ll_introduce)
    LinearLayout llIntroduce;
    @BindView(R.id.ll_reveal)
    LinearLayout llReveal;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @Override
    protected int initContentView() {
        return R.layout.activity_about;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);

        toolbar.setCenterText("项目简介");
        toolbar.setLeftIcon(R.drawable.ic_arrow_back_black_36dp);
        toolbar.setLeftViewOnClickListener(v -> finish());

        AnimationUtil.showCircularReveal(llReveal,2,2000);
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }
}
