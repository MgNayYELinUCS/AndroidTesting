package com.htut.testingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;


public class CustomView extends View {
    private String QUOTE = "Nay Ye Lin";
    private Path circle;
    private Paint mCirlcePaint;
    private Paint tPaint;
    private Rect textBounds;
    private int mTextWidth, mTextHeight, centerX, centerY;
    private float radius;

    public CustomView(Context context) {
        super(context);
        circle = new Path();

        tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        tPaint.setColor(Color.WHITE);
        tPaint.setTextSize(60f);
        textBounds = new Rect();

        tPaint.getTextBounds(QUOTE, 0, QUOTE.length(), textBounds);
        mTextWidth = Math.round(tPaint.measureText(QUOTE.toString())); // Use measureText to calculate width
        mTextHeight = textBounds.height(); // Use height from getTextBounds()

        mCirlcePaint = new Paint();
       // mCirlcePaint.setStyle(Paint.Style.FILL);
        mCirlcePaint.setColor(getResources().getColor(R.color.transparent));

        radius = (float) ((mTextWidth) / (Math.PI));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.rotate(180, getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(centerX, centerY, radius, mCirlcePaint);
        circle.addCircle(centerX, centerY, radius, Path.Direction.CW);
        canvas.drawTextOnPath(QUOTE, circle, 0, 0, tPaint);

    }
}