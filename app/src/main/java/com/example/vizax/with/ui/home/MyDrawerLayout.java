package com.example.vizax.with.ui.home;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Created by Xuhai on 2016/9/12.
 */
public class MyDrawerLayout extends DrawerLayout {
    private Context context;
    private LinearLayout linearLayout;
    private float lastX;
    private float lastY;

    public MyDrawerLayout(Context context) {
        super(context);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float rawX = event.getRawX();
        float rawY = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = rawX;
                lastY = rawY;
                break;

            case MotionEvent.ACTION_MOVE:
                float offsetX = rawX - lastX;
                float offsetX2 = lastX - rawX;
                float offsetY = rawY - lastY;

                Log.w("haha","X="+offsetX+"");
                Log.w("haha","Y="+offsetY+"");
                Log.w("haha","X2="+offsetX2+"");

                if (offsetX>Math.abs(offsetY)&&offsetX>30) {
                    this.openDrawer(linearLayout);
                }
                else if (offsetX2>30) {
                    this.closeDrawer(linearLayout);
                }
                lastX = rawX;
                lastY = rawY;

                if (Math.abs(offsetY)>Math.abs(offsetX))
                    return false;
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

}
