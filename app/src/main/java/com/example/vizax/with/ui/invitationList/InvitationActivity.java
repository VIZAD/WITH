package com.example.vizax.with.ui.invitationList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.R;
import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.customView.BaseToolBar;
import com.example.vizax.with.util.SnackbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvitationActivity extends AppCompatActivity implements InvitationView {
    private static final String MY_INVITATION = "my";
    @BindView(R.id.baseToolBar)
    BaseToolBar mBaseToolBar;
    @BindView(R.id.activity_my_focus_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.invitation_root)
    LinearLayout mRoot;
    private InvitationRecyclerViewAdapter mAdapter;
    private String type;
    private String centerTxt;
    private Intent it;
    private int visible;
    private InvitationListPresenter mInvitationListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        ButterKnife.bind(this);
        mInvitationListPresenter = new InvitationListPresenter(this);
        //设置邀约列表的类型
        setType();
        //初始化recyclerView
        mInvitationListPresenter.setAdapter(this, mRecyclerView, visible,null,null);
        //初始化toolbar
        initToolbar();
    }

    private void setType() {
        it = getIntent();
        type = it.getStringExtra("type");
        if (type != null) {
            if (type.equals(MY_INVITATION)) {
                centerTxt = "我发起的";
                visible = View.VISIBLE;
            } else {
                centerTxt = type;
                visible = View.GONE;
            }
        } else {
            centerTxt = "我发起的";
            visible = View.VISIBLE;
        }

    }

    private void initToolbar() {
        mBaseToolBar.setCenterText(centerTxt);
        mBaseToolBar.setRightIcon(R.drawable.ic_menu);
        mBaseToolBar.setRightViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(InvitationActivity.this)
                        //.items(R.array.sports)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void loadDataFailure() {
        SnackbarUtils.show(mRoot, "获取数据失败，请检查网络", 0, null);
    }


}
