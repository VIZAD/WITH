package com.example.vizax.with.ui.Insist.decorators_util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;
import android.view.View;

/**
 * Span to draw a dot centered under a section of text
 */
public class tickSpan   implements LineBackgroundSpan {

    /**
     * Default radius used
     */
    public static final float DEFAULT_RADIUS = 3;

    private final float radius;
    private final int color;
    private int progress = 0;
    //线1的x轴
    private int line1_x = 0;
    //线1的y轴
    private int line1_y = 0;
    //线2的x轴
    private int line2_x = 0;
    //线2的y轴
    private int line2_y = 0;


    /**
     * Create a span to draw a dot using default radius and color
     *
     * @see #tickSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
    public tickSpan() {
        this.radius = DEFAULT_RADIUS;
        this.color = 0;
    }

    /**
     * Create a span to draw a dot using a specified color
     *
     * @param color color of the dot
     * @see #tickSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
    public tickSpan(int color) {
        this.radius = DEFAULT_RADIUS;
        this.color = color;
    }

    /**
     * Create a span to draw a dot using a specified radius
     *
     * @param radius radius for the dot
     * @see #tickSpan(float, int)
     */
    public tickSpan(float radius,View view) {
        super();
        this.radius = radius;
        this.color = 0;
    }

    /**
     * Create a span to draw a dot using a specified radius and color
     *  @param radius radius for the dot
     * @param color  color of the dot
     */
    public tickSpan(float radius, int color) {
        this.radius = radius;
        this.color = color;
    }



    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        int oldColor = paint.getColor();
        if (color != 0) {
            paint.setColor(color);
        }
        // canvas.drawCircle((left + right) / 2, bottom + radius, radius, paint);
        //canvas.drawLine(30,0,0,30,paint);
        paint.setStrokeWidth(5);
        canvas.drawLine(10,10,30,30,paint);
        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(50,50,30,paint);
        paint.setStrokeWidth(5);
        //canvas.drawLine(50,40,70,70,paint);
        canvas.drawLine(30,30,70,-15,paint);
        paint.setColor(oldColor);


    }
}
