package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
/***
*@Author: WinterSweett
*@Description: 案例：可在白纸上写任何 画任何
 *双缓存技术：用bitmapBuffer保存历史，绘制到view上
*/
public class Line1View extends View {
    private int preX, preY;
    private int curX, curY;
    private Bitmap bitmapBuffer;
    private Canvas bitmapCanvas;
    private Paint paint;

    public Line1View(Context context) {
        this(context, null);
    }

    public Line1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
    }

    /**
     * @Description 组件大小发生改变时
     * @author WinterSweett
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(bitmapBuffer == null){
            int width = getMeasuredWidth() ;
            int height = getMeasuredHeight() ;
            bitmapBuffer = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmapBuffer) ;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将bitmap中的内容 绘制在view上
        canvas.drawBitmap(bitmapBuffer,0,0,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //手指按下，记录第一个点的坐标
                preX = x ;
                preY = y ;
                break;
            case MotionEvent.ACTION_MOVE:
                //手指移动，记录当前点的坐标
                curX = x ;
                curY = y ;
                bitmapCanvas.drawLine(preX,preY,curX,curY,paint);
                this.invalidate();
                //当前点的坐标 成为下一个点的起始坐标
                preX = curX ;
                preY = curY ;
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }
        return true;
    }
}











