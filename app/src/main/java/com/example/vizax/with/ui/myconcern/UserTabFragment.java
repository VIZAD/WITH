package com.example.vizax.with.ui.myconcern;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by apple1 on 2016/9/13.
 */
public class UserTabFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<MyConcern.DataBean> mDatas;
    private UserTabItemAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public UserTabFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_tab_fragment, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.user_tab_fragment_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 36;
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));


        mDatas = new ArrayList<MyConcern.DataBean>();
        initData();
        mAdapter = new UserTabItemAdapter(getActivity(), mDatas);

        // Log.i("aaaa",mAdapter.toString());

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int i) {
                String content = null;
                MyConcern data = (MyConcern) adapter.getItem(i);
                switch (view.getId()) {
                    case R.id.head_imgvi:
                        content = data.getData().get(i).getName();
                        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.concern_btn:


                        break;
                }
            }
                                                         }
        );
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //setContentView(R.layout.activity_main);


//        mRecyclerView.setAdapter(mAdapter);
    }


    protected void initData() {

        OkHttpUtils.post()
                .url(APIConstant.INVITATION_GETCONCERNEDUSERS)
                .addParams("token","token")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getContext(),e+"",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyConcern myConcern = GsonUtil.toListString(response,MyConcern.class);
                        for (int i = 0; i < myConcern.getData().size(); i++) {
                            MyConcern.DataBean dataBean = new MyConcern.DataBean( myConcern.getData().get(i).getConcernedUserId(),
                                    myConcern.getData().get(i).isIsConcerned(), myConcern.getData().get(i).getName(),"R.drawable.user0");
                            mDatas.add(dataBean);
                        }

                    }
                });

    }
}
