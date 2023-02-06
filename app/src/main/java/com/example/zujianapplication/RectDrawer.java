package com.example.zujianapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.example.zujianapplication.tools.AttributesTool;
import com.example.zujianapplication.tools.BitmapBuffer;
import com.example.zujianapplication.tools.ShapeDrawer;

public class RectDrawer extends ShapeDrawer {
    private float firstX ;
    private float firstY ;
    private float curX ;
    private float curY ;
    public RectDrawer(View view) {
        super(view);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawShape(canvas,firstX,firstY,curX,curY) ;
    }

    public void drawShape(Canvas canvas, float firstX, float firstY, float curX, float curY) {
        Paint paint = AttributesTool.getInstance().getPaint();
        //判断手指的方向
        if(firstX < curX && firstY < curY){
            canvas.drawRect(firstX,firstY,curX,curY,paint);
        }else if(firstX > curX && firstY > curY){
            canvas.drawRect(curX,curY,firstX,firstY,paint);
        }else if(firstX > curX && firstY < curY){
            canvas.drawRect(curX,firstY,firstX,curY,paint);
        }else if(firstX < curX && firstY>curY){
            canvas.drawRect(firstX,curY,curX,firstY,paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX() ;
        float y = event.getY() ;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstX = x;
                firstY = y ;
                break;
                case MotionEvent.ACTION_MOVE:
                    curX = x;
                    curY = y;
                    getView().invalidate();
                    break;
            case MotionEvent.ACTION_UP:
                //将最终的矩形绘制在缓冲区
                Canvas canvas = BitmapBuffer.getInstance().getCanvas();
                drawShape(canvas,firstX,firstY,curX,curY);
                getView().invalidate();
                //保存到撤销栈中
                BitmapBuffer.getInstance().pushBitmap();
                break;
        }
        return true;
    }

    @Override
    public void logic() {

    }
}
