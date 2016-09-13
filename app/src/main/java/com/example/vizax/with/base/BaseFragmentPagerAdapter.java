package com.example.vizax.with.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by prj on 2016/9/13.
 */
public class BaseFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragmentsList;
    public String[] title ;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] title) {
        super(fm);
        this.fragmentsList = fragments;
        this.title = title;
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentsList.get(arg0);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}