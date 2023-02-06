package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


public class Line2View extends View {
    private Path path ;
    private int preX, preY ;
    private Paint paint ;
    private Bitmap bitmapBuffer ;
    private Canvas bitmapCanvas ;
    public Line2View(Context context) {
        this(context,null);
    }

    public Line2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        path = new Path() ;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(bitmapBuffer == null){
            int width = getMeasuredWidth() ;
            int height = getMeasuredHeight() ;
            bitmapBuffer = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888) ;
            bitmapCanvas = new Canvas(bitmapBuffer) ;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmapBuffer,0,0,null);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
                preX = x ;
                preY = y ;
                path.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                int controlX = (x+preX)/2 ;
                int controlY = (y+preY)/2 ;
                path.quadTo(controlX,controlY,x,y);
                invalidate();
                preY = x ;
                preY = y;
                break;
            case MotionEvent.ACTION_UP:
                //手指松开后将最终的绘图结果绘制在bitmapBuffer中，同时绘制到view上
                bitmapCanvas.drawPath(path,paint);
                invalidate();
                break;
        }
        return true;
    }
}
