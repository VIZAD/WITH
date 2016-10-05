package com.example.vizax.with.ui.userInformation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.vizax.with.App;
import com.example.vizax.with.R;
import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.util.CircleTransformation;
import com.example.vizax.with.util.SharedUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.sunny.thousand.selectavatar.AvatarImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInformationActivity extends BaseActivity implements UserInformationContact.View, View.OnClickListener {

    @BindView(R.id.baseToolBar)
    BaseToolBar baseToolBar;
    @BindView(R.id.user_infor_avatar)
    AvatarImageView userInforAvatar;
    @BindView(R.id.user_infor_img)
    ImageView userInforImg;
    @BindView(R.id.user_infor_name_txt)
    EditText userInforNameTxt;
    @BindView(R.id.user_infor_num_txt)
    EditText userInforNumTxt;
    @BindView(R.id.user_infor_phonenum_txt)
    EditText userInforPhonenumTxt;
    @BindView(R.id.user_infor_QQ_txt)
    EditText userInforQQTxt;
    Button follow;
    private MaterialDialog uploadDialog;
    private String avatarId;
    private boolean ifMy = true;    //如果为true显示我的信息，如果为false
    private UserInformationPresenter mUserInforPresenter;
    private UserInforBean.DataBean mUserInforBean;
    private String path = "/WITH/";
    private RecyclerViewHeader header;
    private InvitationRecyclerViewAdapter mInvitationRecyclerViewAdapter;
    public SharedPreferences sp;
    private Context context;

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
        follow.setOnClickListener(this);
        //判断是“我的信息” 还是 “他人信息”
        getTye();
        //初始化头像
        initAvatar();
        //初始化toolbar
        initToolbar();
        //显示信息
        setInfomation(ifMy);
        //设置头像回调事件
        Log.w("haha",SharedUtil.getString(this, FieldConstant.token)+"!!!");

        uploadDialog  = new MaterialDialog
                .Builder(this)
                .content("正在上传...")
                .progress(true, 0)
                .build();
    }

    private void initAvatar() {
        if(ifMy){
            userInforImg.setVisibility(View.GONE);
            userInforAvatar.setAfterCropListener(new AvatarImageView.AfterCropListener() {
                @Override
                public void afterCrop(String url) {
                    mUserInforPresenter.setAvatar(url);
                }
            });
        }else {
            userInforAvatar.setVisibility(View.GONE);
        }
    }

    private void initToolbar() {
        if (ifMy)
            baseToolBar.setCenterText("我的资料");
        else
            baseToolBar.setCenterText("用户资料");
        baseToolBar.setLeftViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
            userInforNameTxt.setText(SharedUtil.getString(App.instance,FieldConstant.realName));
            userInforNumTxt.setText(SharedUtil.getString(App.instance,FieldConstant.studentID));
            userInforPhonenumTxt.setText(SharedUtil.getString(App.instance,FieldConstant.phone));
            userInforQQTxt.setText(SharedUtil.getString(App.instance,FieldConstant.qq));
            avatarId = String.valueOf(SharedUtil.getInt(App.instance,FieldConstant.userId));
            follow.setVisibility(View.GONE);
            userInforAvatar.setFile(avatarId, path);
            Picasso.with(this)
                    .load(SharedUtil.getString(App.instance,FieldConstant.userUrl))
                    .placeholder(R.drawable.user0)
                    .transform(new CircleTransformation()).into(userInforAvatar);




        } else {
            userInforNameTxt.setText(mUserInforBean.getName());
            userInforNumTxt.setText(mUserInforBean.getStudentId());
            userInforPhonenumTxt.setText(String.valueOf(mUserInforBean.getPhone()));
            userInforQQTxt.setText(mUserInforBean.getQq());
            follow.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(mUserInforBean.getHeadUrl())
                    .placeholder(R.drawable.user0)
                    .transform(new CircleTransformation()).into(userInforImg);
            if(mUserInforBean.isIsConcerned()){
                follow.setBackgroundColor(getResources().getColor(R.color.my_follow_follow_btn));
                follow.setText("取消关注");
            }else {
                follow.setBackgroundColor(getResources().getColor(R.color.my_follow_unfollow_btn));
                follow.setText("关注");
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
    public void showUploadDialog() {
        uploadDialog.show();
    }

    @Override
    public void dimissUploadDialog() {
        uploadDialog.dismiss();
    }

    @Override
    public void disConcerned() {
        follow.setBackgroundColor(getResources().getColor(R.color.my_follow_unfollow_btn));
        follow.setText("关注");
        Toast.makeText(UserInformationActivity.this, "取关成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void concerned() {
        follow.setBackgroundColor(getResources().getColor(R.color.my_follow_follow_btn));
        follow.setText("取消关注");
        Toast.makeText(UserInformationActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.user_infor_follow) {
            mUserInforPresenter.follow(String.valueOf(mUserInforBean.getUserId()));
        }
    }
}
