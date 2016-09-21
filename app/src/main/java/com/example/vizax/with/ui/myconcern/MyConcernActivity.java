package com.example.vizax.with.ui.myconcern;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.vizax.with.R;
import com.example.vizax.with.customView.BaseToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyConcernActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    BaseToolBar mToolBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myconcern);
        ButterKnife.bind(this);
        mToolBar.setCenterText("我的关注");
        mToolBar.setLeftIcon(R.drawable.ic_arrow_back_black_36dp);

    }

}


