package com.example.vizax.with.ui.myconcern;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.vizax.with.R;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.util.AnimationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.codetail.widget.RevealLinearLayout;

public class MyConcernActivity extends AppCompatActivity {

        @BindView(R.id.toolbar)
        BaseToolBar mToolBar;
        @BindView(R.id.my_concern_fragment)
        View fragment;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_myconcern);
            ButterKnife.bind(this);
            mToolBar.setCenterText("我的关注");
            mToolBar.setLeftIcon(R.drawable.ic_keyboard_arrow_left);
            AnimationUtil.showCircularReveal(fragment,2,2000);

        }

}


