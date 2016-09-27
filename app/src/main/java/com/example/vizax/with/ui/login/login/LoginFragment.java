package com.example.vizax.with.ui.login.login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.ui.demo.DemoPresenter;
import com.example.vizax.with.ui.demo.DemoSwipBackActivity;
import com.example.vizax.with.util.MaxLengthWatcher;
import com.example.vizax.with.util.TextUtil;

import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/14.
 */

public class LoginFragment extends Fragment implements LoginContact.View {
    @BindView(R.id.usernum_edtTxt)
    EditText mUsernum_edtTxt;
    @BindView(R.id.psw_edtTxt)
    EditText mPsw_edtTxt;
    @BindView(R.id.login_btn)
    Button mLogin_btn;
    @BindView(R.id.for_txtVi)
    TextView mFor_txtVi;
    @BindView(R.id.recLayout)
    LinearLayout recLayout;


    private View mView;
    private Activity mActivity;
    private ProgressDialog mProgressDialog;
    private Handler mHandler;
    private LoginPresenter mPresenter;
    private MaterialDialog mDialog;
    private String mUsernum_str, mPsw_str;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, mView);
        initView();
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        return mView;
    }

    private void initView() {
        mActivity = getActivity();
        mUsernum_edtTxt.addTextChangedListener(new MaxLengthWatcher(11, mUsernum_edtTxt));
        mPsw_edtTxt.addTextChangedListener(new MaxLengthWatcher(20, mPsw_edtTxt));
        mDialog = new MaterialDialog.Builder(mActivity)
                .content("正在登录...")
                .progress(true, 0)
                .build();
    }

    @OnClick({R.id.login_btn, R.id.for_txtVi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();

                break;
            case R.id.for_txtVi:
                forget();
                break;
        }
    }


    /**
     * 登录
     */
    private void login() {

        mUsernum_str = TextUtil.getText(mUsernum_edtTxt);
        mPsw_str = TextUtil.getText(mPsw_edtTxt);
        Matcher m = TextUtil.compileExChar(mUsernum_str);
        if(m.find()){
            Toast.makeText(mActivity, "不允许输入特殊字符", Toast.LENGTH_SHORT).show();
        }else if(mUsernum_str.length()!=11){
            Toast.makeText(mActivity, "请输入正确的账号", Toast.LENGTH_SHORT).show();
        } else if (mUsernum_str.isEmpty() == true) {
            Toast.makeText(mActivity, "用户账号不能为空", Toast.LENGTH_SHORT).show();
        } else if (mPsw_str.isEmpty() == true) {
            Toast.makeText(mActivity, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.login(mUsernum_str, mPsw_str);
        }
    }

    /**
     * 忘记密码
     */
    private void forget() {
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
        //返回Msg数据
        Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT).show();
        //登录成功操作
    }

    @Override
    public void loginFailure(String Error) {
        //登录失败
        Toast.makeText(mActivity, Error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}