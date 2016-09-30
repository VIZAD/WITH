package com.example.vizax.with.ui.ranking;

import android.os.Bundle;

import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;

public class RankingActivity extends BaseActivity {

    @Override
    protected int initContentView() {
        return R.layout.ranking_activity;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    public void initUiAndListener() {

    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }
}
