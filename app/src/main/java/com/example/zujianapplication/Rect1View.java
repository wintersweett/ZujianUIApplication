package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


public class Rect1View extends View {
    private int firstX,firstY ;
    private Path path ;
    private Paint paint ;
    public Rect1View(Context context) {
        this(context,null);
    }

    public Rect1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        path = new Path() ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
                firstX = x;
                firstY = y ;
                break;
            case MotionEvent.ACTION_MOVE:
                //绘制矩形时候，要先清除前一次的结果
                path.reset();
                path.addRect(firstX,firstY,x,y, Path.Direction.CCW);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }
        return true;
    }
}











