package com.example.vizax.with.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.ui.login.login.LoginFragment;
import com.example.vizax.with.ui.login.verify.VerifyFragment;
import com.example.vizax.with.ui.myhome.HomeActivity;
import com.example.vizax.with.util.AnimationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements MainContact.View {


    @BindView(R.id.main_tabLayout)
    TabLayout mTablayout;
    @BindView(R.id.main_viewPage)
    ViewPager mViewPager;
    LinearLayout lReg_lLayout;
    LinearLayout lVerify_lLayout;
    MainPresenter mPresenter;
    @BindView(R.id.root)
    RelativeLayout mRoot;

    @Override
    protected int initContentView() {
        return R.layout.main_activity;
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        //动画效果
        //AnimationUtil.showCircularReveal(mRoot, 2, 1000);
        mPresenter = new MainPresenter(getApplicationContext());
        if (!mPresenter.isFirstLogin() && mPresenter.isHadLogin()) {//不是第一次，并且已经登录,直接进入主页
            startActivity();
            finish();
        }
        mPresenter.attachView(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            private String[] mTitles = new String[]{"注册", "登陆"};

            @Override
            public Fragment getItem(int position) {
                if (position == 1) {
                    return new LoginFragment();
                }
                return new VerifyFragment();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }

        });
        mTablayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        //状态栏是否透明
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        //是否将状态栏颜色设置为colorPrimay
        return true;
    }

    public void showLoign() {
        mViewPager.setCurrentItem(1);
    }

    EditText mRegUsernumEdtTxt, mRegVerifynumEdtTxt, mPswRegEdtTxt, mRegConpswEdtTxt;

    public void setLayout(LinearLayout lVerify_lLayout, LinearLayout lReg_lLayout,
                          EditText mRegUsernumEdtTxt,
                          EditText mRegVerifynumEdtTxt,
                          EditText mPswRegEdtTxt,
                          EditText mRegConpswEdtTxt) {
        this.lVerify_lLayout = lVerify_lLayout;
        this.lReg_lLayout = lReg_lLayout;
        this.mRegUsernumEdtTxt = mRegUsernumEdtTxt;
        this.mRegVerifynumEdtTxt = mRegVerifynumEdtTxt;
        this.mPswRegEdtTxt = mPswRegEdtTxt;
        this.mRegConpswEdtTxt = mRegConpswEdtTxt;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (lVerify_lLayout != null && lVerify_lLayout.getVisibility() == View.GONE) {
                mRegUsernumEdtTxt.setText("");
                mRegVerifynumEdtTxt.setText("");
                mPswRegEdtTxt.setText("");
                mRegConpswEdtTxt.setText("");
                lVerify_lLayout.setVisibility(View.VISIBLE);
                lReg_lLayout.setVisibility(View.GONE);
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void startActivity() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mPresenter.de
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}