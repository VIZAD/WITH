package com.example.vizax.with.ui.myconcern;

/**
 * Created by Administrator on 2016/9/18.
 */
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by moon.zhong on 2015/2/11.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = space;


        if (parent.getChildPosition(view) == 0)
            outRect.top = space;
    }


//    private int DEFAULT_COLOR = 0xFFFFFF;
//    /*
//    * RecyclerView的布局方向，默认先赋值
//    * 为纵向布局
//    * RecyclerView 布局可横向，也可纵向
//    * 横向和纵向对应的分割想画法不一样
//    * */
//    private int mOrientation = LinearLayoutManager.VERTICAL ;
//
//    /**
//     * item之间分割线的size，默认为1
//     */
//    private int mItemSize = 9 ;
//
//    /**
//     * 绘制item分割线的画笔，和设置其属性
//     * 来绘制个性分割线
//     */
//    private Paint mPaint ;
//
//    /**
//     * 构造方法传入布局方向，不可不传
//     * @param context
//     * @param orientation
//     */
//
}
