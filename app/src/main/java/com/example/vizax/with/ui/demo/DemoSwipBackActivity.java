package com.example.vizax.with.ui.demo;

import android.widget.ImageView;

import com.example.vizax.with.R;
import com.example.vizax.with.base.BaseActivity;
import com.example.vizax.with.util.AnimationUtil;
import com.example.vizax.with.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.codetail.widget.RevealLinearLayout;

/**
 * 透明状态栏：图片可覆盖状态栏
 * CircularReveal动画效果
 * swipeback效果暂未实现，待添加
 * Created by prj on 2016/9/14.
 */
public class DemoSwipBackActivity extends BaseActivity {

//    @BindView(R.id.img)
//    ImageView img;
//    @BindView(R.id.activity_main)
//    RevealLinearLayout activityMain;

    @Override
    protected int initContentView() {
        return R.layout.main_activity;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);

        /**
         * 如需实现该方法，设置所要显示动画的界面的父类为RevealLinearLayout或者RevealFrameLayout
         * 然后调用以下接口可以。有两个接口可选，自己看介绍，不懂问prj
         * 参数依次为
         * @param view 展开动画的view.注意：view需为RevealLinearLayout或者RevealFrameLayout的直接子类
         * @param centerX 从具体某个点的X坐标开始扩散
         * @param centerY 从具体某个点的Y坐标开始扩散
         * @param MultipleRadius 半径倍数
         * @param Duration 动画时间
         */
//        AnimationUtil.showCircularReveal(img,
//                ScreenUtil.getScreenWidth(this)/2,
//                ScreenUtil.getScreenHeight(this)/2,
//                2,1000);
        /**
         * 不带x，y坐标，默认从左上角开启动画效果
         * @param view 展开动画的view.注意：view需为RevealLinearLayout或者RevealFrameLayout的直接子类
         * @param MultipleRadius 半径倍数
         * @param Duration 动画时间
         */
        //AnimationUtil.showCircularReveal(img,2,1000);
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

}
