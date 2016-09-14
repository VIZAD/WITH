package com.example.vizax.with.ui.demo;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.util.AnimationUtil;
import com.example.vizax.with.util.SnackbarUtils;
import com.example.vizax.with.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by prj on 2016/9/14.
 */

public class DemoActivity extends BaseActivity implements DemoContact.View {

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.demo_username_edtTxt)
    EditText demoUsernameEdtTxt;
    @BindView(R.id.demo_username_textInputLayout)
    TextInputLayout demoUsernameTextInputLayout;
    @BindView(R.id.demo_password_edtTxt)
    EditText demoPasswordEdtTxt;
    @BindView(R.id.demo_password_textInputLayout)
    TextInputLayout demoPasswordTextInputLayout;
    @BindView(R.id.demo_commit_btn)
    Button demoCommitBtn;
    @BindView(R.id.root)
    LinearLayout root;
    private DemoPresenter mPresenter;
    private MaterialDialog mDialog;

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
        //先用注解findID，再将视图attach到presenter
        ButterKnife.bind(this);

        mDialog = new MaterialDialog.Builder(this)
                .content("正在登录...")
                .progress(true, 0)
                .build();

        toolbar.setCenterText("DEMO");
        toolbar.setLeftIcon(getResources().getDrawable(R.drawable.back_ic));
        toolbar.setRightIcon(getResources().getDrawable(R.drawable.delete_forever_ic));

        AnimationUtil.showCircularReveal(root,2,2000);

        mPresenter = new DemoPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void dimissLoading() {
        mDialog.dismiss();
    }

    @Override
    public void loginSuccess(String Msg) {
        SnackbarUtils.show(root, Msg, 0, null);
        Intent intent = new Intent(this,DemoSwipBackActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailure(String Error) {
        SnackbarUtils.show(root, Error, 0, null);
    }

    @OnClick(R.id.demo_commit_btn)
    public void onClick() {
        mPresenter.login(TextUtil.getText(demoUsernameEdtTxt),TextUtil.getText(demoPasswordEdtTxt));
    }
}
