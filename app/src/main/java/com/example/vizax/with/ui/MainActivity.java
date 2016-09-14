package com.example.vizax.with.ui;

import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int initContentView() {
        return R.layout.main_activity;
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
