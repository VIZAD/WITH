package com.example.vizax.with.ui.demo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.customView.BaseToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/14.
 */

public class DemoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.textInputUserName)
    TextInputLayout textInputUserName;
    @BindView(R.id.textInputPassword)
    TextInputLayout textInputPassword;
    @BindView(R.id.btnCommit)
    Button btnCommit;
    @BindView(R.id.root)
    LinearLayout root;

    @Override
    protected int initContentView() {
        return R.layout.demo_activity;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    public void initUiAndListener() {

    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnCommit)
    public void onClick() {
    }
}
