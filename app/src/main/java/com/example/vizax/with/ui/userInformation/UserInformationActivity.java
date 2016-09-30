package com.example.vizax.with.ui.userInformation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.vizax.with.R;
import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.customView.BaseToolBar;
import com.sunny.thousand.selectavatar.AvatarImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInformationActivity extends BaseActivity implements UserInformationContact.View, View.OnClickListener {
    @BindView(R.id.baseToolBar)
    BaseToolBar baseToolBar;
    @BindView(R.id.user_infor_avatar)
    AvatarImageView userInforAvatar;
    @BindView(R.id.user_infor_name_txt)
    EditText userInforNameTxt;
    @BindView(R.id.user_infor_num_txt)
    EditText userInforNumTxt;
    @BindView(R.id.user_infor_phonenum_txt)
    EditText userInforPhonenumTxt;
    @BindView(R.id.user_infor_QQ_txt)
    EditText userInforQQTxt;
    Button follow;
    private String avatarId;
    private boolean ifMy = true;    //如果为true显示我的信息，如果为false
    private UserInformationPresenter mUserInforPresenter;
    private UserInforBean.DataBean mUserInforBean;
    private String path = "/WITH/";
    private RecyclerViewHeader header;
    private InvitationRecyclerViewAdapter mInvitationRecyclerViewAdapter;
    public SharedPreferences sp;

    @Override
    protected int initContentView() {
        return R.layout.user_information_activity;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mUserInforPresenter = new UserInformationPresenter();
        mUserInforPresenter.attachView(this);
        follow = (Button) findViewById(R.id.user_infor_follow);

        initRecyclerView();
        //初始化toolbar
        initToolbar();
        //判断是“我的信息” 还是 “他人信息”
        getTye();
        //显示信息
        setInfomation(ifMy);
        //设置头像回调事件
        userInforAvatar.setAfterCropListener(new AvatarImageView.AfterCropListener() {
            @Override
            public void afterCrop(String url) {
                mUserInforPresenter.setAvatar(avatarId, url.toString());
            }
        });
    }

    private void initRecyclerView() {
      //TODO
    }

    private void initToolbar() {
        if (ifMy)
            baseToolBar.setCenterText("我的资料");
        else
            baseToolBar.setCenterText("用户资料");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (userInforAvatar != null) {
            userInforAvatar.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getTye() {
        Intent it = getIntent();
        mUserInforBean = it.getParcelableExtra("userInforBean");
        if (mUserInforBean == null) {
            ifMy = true;
        } else {
            ifMy = false;
        }

    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    public void setInfomation(boolean ifMy) {
        if (ifMy) {
            /**
             * 临时数据
             */
            sp = getSharedPreferences("loginFile",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("userId","0001");
            editor.putString("userName","吕鹏");
            editor.putString("num","201324133112");
            editor.putString("phoneNum","18318742105");
            editor.putString("qq",null);
            editor.commit();

            userInforNameTxt.setText(sp.getString("userName", null));
            userInforNumTxt.setText(sp.getString("num", null));
            userInforPhonenumTxt.setText(sp.getString("phoneNum", null));
            userInforQQTxt.setText(sp.getString("qq", null));
            avatarId = sp.getString("userId", null);
            follow.setVisibility(View.GONE);
            userInforAvatar.setFile(avatarId, path);

        } else {
            userInforNameTxt.setText(mUserInforBean.getName());
            userInforNumTxt.setText(mUserInforBean.getStudentId());
            userInforPhonenumTxt.setText(String.valueOf(mUserInforBean.getPhone()));
            userInforQQTxt.setText(mUserInforBean.getQq());
            follow.setVisibility(View.VISIBLE);
            avatarId = mUserInforBean.getStudentId();
            userInforAvatar.setFile(avatarId, path);
           if(mUserInforBean.isIsConcerned()){
               userInforAvatar.setBackgroundColor(getResources().getColor(R.color.my_follow_follow_btn));
           }else {
               userInforAvatar.setBackgroundColor(getResources().getColor(R.color.my_follow_unfollow_btn));
           }
        }
    }

    @Override
    public void changeUserAvatar() {

    }

    @Override
    public void changePassword() {

    }

    @Override
    public void upLoadSuccess() {
        Toast.makeText(UserInformationActivity.this, "设置新的头像成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void upLoadFailure() {
        Toast.makeText(UserInformationActivity.this, "设置新的头像失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }




   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }*/


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.user_infor_follow) {
            if(mUserInforPresenter.follow(mUserInforBean.getStudentId())){
                userInforAvatar.setBackgroundColor(getResources().getColor(R.color.my_follow_follow_btn));
                Toast.makeText(UserInformationActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
            }else {
                userInforAvatar.setBackgroundColor(getResources().getColor(R.color.my_follow_unfollow_btn));
                Toast.makeText(UserInformationActivity.this, "关注失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
