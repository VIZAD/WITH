package com.example.vizax.with.ui.myconcern;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vizax.with.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class MyConcernFragment extends Fragment {

    private TabLayout tabTitle;                            //定义TabLayout
    private ViewPager mViewPager;                             //定义viewPager
    private FragmentPagerAdapter mAdapter;                               //定义adapter

    private List<Fragment> mFragments;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    private ActivityTabFragment event;              //热门推荐fragment
    private UserTabFragment user;            //热门收藏fragment
                                      //今日热榜fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_concern_fragment, container, false);

        initControls(view);

        return view;
    }

    /**
     * 初始化各控件
     * @param view
     */
    private void initControls(View view) {

        tabTitle = (TabLayout)view.findViewById(R.id.my_concern_fragment_tab_title);
        mViewPager = (ViewPager)view.findViewById(R.id.my_concern_fragment_tab_context);

        //初始化各fragment
        event = new ActivityTabFragment();
        user = new UserTabFragment();


        //将fragment装进列表中
        mFragments = new ArrayList<>();
        mFragments.add(event);
        mFragments.add(user);


        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("活动");
        list_title.add("关注");

        //设置TabLayout的模式

        tabTitle.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tabTitle.addTab(tabTitle.newTab().setText(list_title.get(0)));
        tabTitle.addTab(tabTitle.newTab().setText(list_title.get(1)));

        mAdapter = new MyConcernAdapter(getActivity().getSupportFragmentManager(),mFragments,list_title);

        //viewpager加载adapter
        mViewPager.setAdapter(mAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tabTitle.setupWithViewPager(mViewPager);
        //tab_FindFragment_title.set
    }


}