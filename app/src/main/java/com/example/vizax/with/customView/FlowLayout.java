package com.example.vizax.with.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局：用于动态添加子View，可用于热门标签
 * Created by prj on 2016/9/11.
 */

public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //warp_content
        int width = 0;
        int height = 0;

        //记录每一行宽度、高度
        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();
        for (int i=0;i<cCount;i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            //得到
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth+childWidth>sizeWidth-getPaddingLeft()-getPaddingRight()){
                width = Math.max(width,lineWidth);
                lineWidth = childWidth;

                height += lineHeight;
                lineHeight = childHeight;
            }else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }

            if (i+1==cCount){
                width = Math.max(lineWidth,width);
                height += lineHeight;
            }
        }

        /*Log.w("haha","width="+sizeWidth);
        Log.w("haha","height="+sizeHeight);*/

        setMeasuredDimension(
                modeWidth==MeasureSpec.EXACTLY?sizeWidth:width+getPaddingLeft()+getPaddingRight(),
                modeHeight==MeasureSpec.EXACTLY?sizeHeight:height+getPaddingTop()+getPaddingBottom());
    }

    private List<List<View>> mAllView = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        mAllView.clear();
        mLineHeight.clear();

        //当前ViewGroup宽度
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();
        int cCount = getChildCount();
        for (int j=0;j<cCount;j++){
            View child = getChildAt(j);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //需要换行
            if (childWidth +lineWidth+lp.leftMargin+lp.rightMargin > width-getPaddingLeft()-getPaddingRight()){
                mLineHeight.add(lineHeight);
                mAllView.add(lineViews);

                lineWidth = 0;
                lineHeight = childHeight+lp.topMargin+lp.bottomMargin;

                lineViews = new ArrayList<>();
            }
            lineWidth += childWidth+lp.leftMargin+lp.rightMargin;
            lineHeight = Math.max(lineHeight,childHeight+lp.topMargin+lp.bottomMargin);
            lineViews.add(child);
        }

        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllView.add(lineViews);

        //设置子view位置
        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lineNum = mAllView.size();
        for (int j=0;j<lineNum;j++){
            lineViews = mAllView.get(j);
            lineHeight = mLineHeight.get(i);

            for (int k=0;k<lineViews.size();k++){
                View child = lineViews.get(k);
                if (child.getVisibility()==View.GONE){
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left+lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc,tc,rc,bc);

                left += child.getMeasuredWidth() + lp.leftMargin+lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
