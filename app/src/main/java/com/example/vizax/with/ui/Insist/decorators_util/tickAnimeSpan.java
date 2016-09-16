package com.example.vizax.with.ui.Insist.decorators_util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

/**
 * Span to draw a dot centered under a section of text
 */
public class tickAnimeSpan   implements LineBackgroundSpan {

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
     * @see #tickAnimeSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
    public tickAnimeSpan() {
        this.radius = DEFAULT_RADIUS;
        this.color = 0;
    }

    /**
     * Create a span to draw a dot using a specified color
     *
     * @param color color of the dot
     * @see #tickAnimeSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
    public tickAnimeSpan(int color) {
        this.radius = DEFAULT_RADIUS;
        this.color = color;
    }

    /**
     * Create a span to draw a dot using a specified radius
     *
     * @param radius radius for the dot
     * @see #tickAnimeSpan(float, int)
     */
    public tickAnimeSpan(float radius) {
        this.radius = radius;
        this.color = 0;
    }

    /**
     * Create a span to draw a dot using a specified radius and color
     *  @param radius radius for the dot
     * @param color  color of the dot
     */
    public tickAnimeSpan(float radius, int color) {

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
        canvas.drawLine(50, 40, 70, 70, paint);
//            canvas.drawLine(70, 70, 120, 30, paint);
        int oldColor = paint.getColor();
        paint.setColor(oldColor);
        if (color != 0) {
            paint.setColor(color);
        }
        paint.setStrokeWidth(10);
        //Thread.sleep(10);
        //paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        for(int lenth=0;lenth<20;lenth++) {
            canvas.drawLine(50, 40, 50+lenth, 50+lenth, paint);
        }
        canvas.drawLine(70, 70, 160, 0, paint);
        System.out.println("progress="+progress);
        progress++;
    }


}
