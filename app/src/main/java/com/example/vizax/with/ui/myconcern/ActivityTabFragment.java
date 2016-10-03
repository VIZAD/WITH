package com.example.vizax.with.ui.myconcern;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vizax.with.R;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.bean.InvitationBean;
import com.example.vizax.with.bean.MembersBean;
import com.example.vizax.with.bean.UserInforBean;
import com.example.vizax.with.ui.invitation.LuanchInvitationActivity;
import com.example.vizax.with.ui.invitationList.InvitationActivity;
import com.example.vizax.with.ui.invitationList.InvitationContact;
import com.example.vizax.with.ui.invitationList.InvitationDetailsActivity;
import com.example.vizax.with.ui.invitationList.InvitationPresenter;
import com.example.vizax.with.ui.userInformation.UserInformationActivity;
import com.example.vizax.with.util.AnimationUtil;

import net.mobctrl.views.SuperSwipeRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.visible;

/**
 * Created by apple1 on 2016/9/13.
 */
public class ActivityTabFragment extends Fragment implements InvitationContact.View {

    private InvitationPresenter mInvitationListPresenter;
    @BindView(R.id.activity_tab_fragment_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.concern_refresh)
    SuperSwipeRefreshLayout concern_refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab_fragment, container, false);
        ButterKnife.bind(this, view);
        mInvitationListPresenter = new InvitationPresenter();
        mInvitationListPresenter.attachView(this);
        //初始化recyclerView
        mInvitationListPresenter.getDataAndSetAdapter(getContext(), mRecyclerView,visible, null, "-1");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //int spacingInPixels = 36;
        //mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        initSuperSwipeRefresh();
        return view;

    }

    @Override
    public void showRightIcon() {

    }

    @Override
    public void showDialog(boolean type, @Nullable String contents, int position) {

    }

    @Override
    public void showDiaolog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void loadDataFailure() {

    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openEdit() {
    }

    @Override
    public void openLaunch() {

    }

    @Override
    public void OpenDetail(int position, InvitationBaseBean invitationBean) {
        Intent it = new Intent(getContext(), InvitationDetailsActivity.class);
        Bundle lBundle = new Bundle();
        Bundle memBundle = new Bundle();
        Bundle invitationBaseBeanBundle = new Bundle();
        memBundle.putParcelableArrayList("members",invitationBean.getData().get(position).getMembers());
        // lBundle.putParcelable("users",mData.getData().get(position));
        invitationBaseBeanBundle.putParcelableArrayList("invitationlist", invitationBean.getData());
        invitationBaseBeanBundle.putInt("index",position);
        //it.putExtras(lBundle);
        it.putExtras(memBundle);
        it.putExtras(invitationBaseBeanBundle);
        startActivityForResult(it,1);

    }

    @Override
    public void OpenUserInfor(UserInforBean userInforBean) {
        Intent it = new Intent(getContext(), UserInformationActivity.class);
        Bundle lBundle = new Bundle();
        lBundle.putParcelable("userInforBean", new UserInforBean().getData());
        it.putExtras(lBundle);
        startActivity(it);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean join = data.getBooleanExtra("join",false);
        int index = data.getIntExtra("index",0);
        MembersBean mem = data.getParcelableExtra("member");
        InvitationBaseBean invitationBaseBean = new InvitationBaseBean();
        InvitationPresenter.mInvitationBaseBean.getData().get(index).setJoin(join);
        mInvitationListPresenter.setAdapter(getContext(), mRecyclerView,invitationBaseBean,visible);
    }

    @Override
    public void stopRefresh() {
        concern_refresh.setLoadMore(false);
        concern_refresh.setRefreshing(false);
    }


    private void initSuperSwipeRefresh() {
        concern_refresh.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                mInvitationListPresenter.getDataAndSetAdapter(getActivity().getApplicationContext(), mRecyclerView, visible, null, "-1");
                concern_refresh.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        concern_refresh.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mInvitationListPresenter.pullLoadMore(getActivity().getApplicationContext(), mRecyclerView, visible, null, null);


            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
    }

}
