package com.example.vizax.with.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.adapter.MyFocusRecyclerViewAdapter;
import com.example.vizax.with.customView.BaseToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFocusActivity extends AppCompatActivity {
    private static final String MY_INVITATION = "my";
    @BindView(R.id.baseToolBar)
    BaseToolBar mBaseToolBar;
    @BindView(R.id.activity_my_focus_recyclerview)
    RecyclerView mActivityMyFocusRecyclerview;
    private RecyclerView mRecyclerView;
    private MyFocusRecyclerViewAdapter mAdapter;
    private String type;
    private String centerTxt;
    private Intent it;
    private int visible;
    MaterialDialog mMaterialDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_focus);
        ButterKnife.bind(this);

        setType();
        //初始化recyclerView
        initRecyclerView();
        //初始化toolbar
        initToolbar();
    }

    private void setType() {
        it = getIntent();
        type = it.getStringExtra("type");
        if(type != null) {
            if (type.equals(MY_INVITATION)) {
                centerTxt = "我发起的";
                visible = View.VISIBLE;
            } else {
                centerTxt = type;
                visible = View.GONE;
            }
        }else {
            centerTxt = "运动";
            visible = View.GONE;
        }
    }


    private void initToolbar() {
        mBaseToolBar.setCenterText(centerTxt);
        mBaseToolBar.setRightIcon(R.drawable.ic_menu);
        mBaseToolBar.setRightViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_my_focus_recyclerview);
        mAdapter = new MyFocusRecyclerViewAdapter(this);
        mAdapter.setExpend(visible);//设置时间右边的expend箭头是否显示 我发起的界面显示，其他界面不显示
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

    }
}
