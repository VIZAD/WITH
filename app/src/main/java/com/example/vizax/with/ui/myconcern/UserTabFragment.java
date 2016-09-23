package com.example.vizax.with.ui.myconcern;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.vizax.with.R;
import com.example.vizax.with.bean.Test;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.example.vizax.with.constant.APIConstant.INVITATION_GETCONCERNEDUSERS;

/**
 * Created by apple1 on 2016/9/13.
 */
public class UserTabFragment extends Fragment implements MyConcernContact.View {
    private RecyclerView mRecyclerView;
    private List<MyConcern.DataBean> mDatas;
    private UserTabItemAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MyConcernPresenter myConcernPresenter;
    public UserTabFragment() {

    }

    @BindView(R.id.user_tab_fragment_rl)
    RelativeLayout mRelativeLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_tab_fragment, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.user_tab_fragment_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mDatas = new ArrayList<MyConcern.DataBean>();
        initData();
        System.out.println("mdata:="+mDatas);
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //setContentView(R.layout.activity_main);


//        mRecyclerView.setAdapter(mAdapter);
    }


    protected void initData() {

        myConcernPresenter = new MyConcernPresenter();
        myConcernPresenter.attachView(this);
        myConcernPresenter.getMyCocernData( getContext());

    }

    @Override
    public void setData (List<MyConcern.DataBean> items) {
        this.mDatas =items;
        int spacingInPixels= (int) (mRelativeLayout.getHeight()*0.025);

        mAdapter = new UserTabItemAdapter(getActivity(), mDatas);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
                                                             @Override
                                                             public void onItemChildClick(BaseQuickAdapter adapter, View view, int i) {
                                                                 String content = null;
                                                                 MyConcern.DataBean data = ( MyConcern.DataBean) adapter.getItem(i);
                                                                 switch (view.getId()) {
                                                                     case R.id.head_imgvi:
                                                                         content = "name";
                                                                         Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
                                                                         break;
                                                                     case R.id.concern_btn:
                                                                            Button concern_btn = (Button) view.findViewById(R.id.concern_btn);
                                                                         if(concern_btn.getText().equals("取关")) {
                                                                             concern_btn.setText("关注");
                                                                             concern_btn.setBackgroundResource(R.color.lightgray_text_def);
                                                                             myConcernPresenter.IsCocern(getContext());
                                                                         } else {
                                                                             concern_btn.setText("取关");
                                                                             concern_btn.setBackgroundResource(R.color.colorPrimary);
                                                                             myConcernPresenter.IsCocern(getContext());
                                                                         }
                                                                         break;
                                                                 }
                                                             }
                                                         }
        );

    }
}
