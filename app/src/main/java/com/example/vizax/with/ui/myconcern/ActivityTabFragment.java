package com.example.vizax.with.ui.myconcern;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vizax.with.R;

/**
 * Created by apple1 on 2016/9/13.
 */
public class ActivityTabFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_tab_fragment,container,false);
    }
}
