package com.example.vizax.with.ui.login.login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.App;
import com.example.vizax.with.R;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.ui.demo.DemoPresenter;
import com.example.vizax.with.ui.demo.DemoSwipBackActivity;
import com.example.vizax.with.ui.home.HomeActivity;
import com.example.vizax.with.ui.login.User;
import com.example.vizax.with.ui.login.bean.UserBean;
import com.example.vizax.with.util.MaxLengthWatcher;
import com.example.vizax.with.util.SharedUtil;
import com.example.vizax.with.util.TextUtil;

import java.lang.reflect.Field;
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
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, mView);
        mUsernum_edtTxt.setText(SharedUtil.getString(getContext(),FieldConstant.phone));
        mPsw_edtTxt.setText(SharedUtil.getString(getContext(),FieldConstant.password));
        mPresenter = new LoginPresenter(getContext());
        initView();
        mPresenter.attachView(this);
        return mView;
    }

    private void initView() {
        mActivity = getActivity();
        mSharedPreferences = mActivity.getSharedPreferences("User", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

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
    public void loginSuccess(String Msg,UserBean.DataBean data) {
        //返回Msg数据
        Toast.makeText(mActivity, Msg, Toast.LENGTH_SHORT).show();
        //登录成功操作
        SharedUtil.putString(mActivity,FieldConstant.phone,mUsernum_str);
        SharedUtil.putString(App.instance,FieldConstant.password,mPsw_str);
        SharedUtil.putInt(App.instance,FieldConstant.sex,data.getSex());
        SharedUtil.putInt(App.instance,FieldConstant.unReadedNumber,data.getUnReadedNumber());
        SharedUtil.putString(App.instance,FieldConstant.token,data.getToken());
        SharedUtil.putString(App.instance,FieldConstant.nickName,data.getNickName());
        SharedUtil.putString(App.instance,FieldConstant.userUrl,data.getUserUrl());
        SharedUtil.putInt(App.instance,FieldConstant.userId,data.getUserId());
        SharedUtil.putString(App.instance,FieldConstant.classX,data.getClassX());
        SharedUtil.putString(App.instance,FieldConstant.studentID,data.getStudentID());
        SharedUtil.putString(App.instance,FieldConstant.realName,data.getStudentID());
        SharedUtil.putString(App.instance,FieldConstant.qq,data.getQq());

        SharedUtil.putBoolean(App.instance,FieldConstant.ishadlogin,true);
        SharedUtil.putBoolean(App.instance,FieldConstant.isfirstlogin,false);
        Log.w("haha",SharedUtil.getString(App.instance, FieldConstant.token)+"!!!");
        //getActivity().finish();
    }

    @Override
    public void loginFailure(String Error) {
        //登录失败
        Toast.makeText(mActivity, Error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivity() {
        getActivity().startActivity(new Intent(getContext(), HomeActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}