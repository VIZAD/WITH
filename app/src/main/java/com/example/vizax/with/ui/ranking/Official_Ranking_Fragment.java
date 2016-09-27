package com.example.vizax.with.ui.ranking;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.vizax.with.R;
import com.example.vizax.with.util.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class Official_Ranking_Fragment extends Fragment  {


    private View containerView;


    public static Official_Ranking_Fragment newInstance(int color_cl, int color_mi) {
        Official_Ranking_Fragment contentFragment = new Official_Ranking_Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("color_cl", color_cl);
        bundle.putInt("color_mi", color_mi);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);

    }
}

