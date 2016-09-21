package com.example.vizax.with.ui.myconcern;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class MyConcernAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> list_title;
    public MyConcernAdapter(FragmentManager fm, List<Fragment> mFragments, List<String> list_title) {
        super(fm);
        this.mFragments = mFragments;
        this.list_title = list_title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return list_title.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_title.get(position % list_title.size());
    }
}
