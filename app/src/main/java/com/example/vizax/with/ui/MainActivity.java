package com.example.vizax.with.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
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
