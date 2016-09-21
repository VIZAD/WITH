package com.example.vizax.with.ui.myconcern;


import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vizax.with.R;
import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.ui.invitationList.InvitationContact;
import com.example.vizax.with.ui.invitationList.InvitationPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.visible;

/**
 * Created by apple1 on 2016/9/13.
 */
public class ActivityTabFragment extends Fragment {
    private InvitationContact.View mInvitationActivity;
    private InvitationContact.InvitationlModel mInvitationModel;
    private InvitationRecyclerViewAdapter mAdapter;
    private InvitationPresenter mInvitationListPresenter;
    private InvitationBaseBean mData;

    @BindView(R.id.activity_tab_fragment_recyclerview)
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab_fragment, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new InvitationRecyclerViewAdapter(getContext(),mData,visible);
        int spacingInPixels = 36;
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));


        return view;

    }
}
