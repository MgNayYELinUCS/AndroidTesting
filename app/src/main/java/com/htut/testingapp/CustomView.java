package com.htut.testingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;


public class CustomView extends View {
    private static final String Text = "Welcome To Hamad's Blog";
    private Path myArc;
    private Paint mPaintText;

    public CustomView(Context context) {
        super(context);
        //create Path object
        myArc = new Path();
        //create RectF Object
        RectF oval = new RectF(0,0,0,0);
        //add Arc in Path with start angle -180 and sweep angle 200
        myArc.addArc(oval, -180, 200);
        //create paint object
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        //set style
        mPaintText.setStyle(Paint.Style.FILL_AND_STROKE);
        //set color
        mPaintText.setColor(Color.RED);
        //set text Size
        mPaintText.setTextSize(20f);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw Text on Canvas
        canvas.drawTextOnPath(Text, myArc, 0, 0, mPaintText);
        invalidate();
    }
}
