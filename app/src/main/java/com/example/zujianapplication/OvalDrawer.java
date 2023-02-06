package com.example.zujianapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.example.zujianapplication.tools.AttributesTool;

public class OvalDrawer extends RectDrawer{
    public OvalDrawer(View view) {
        super(view);
    }

    @Override
    public void drawShape(Canvas canvas, float firstX, float firstY, float curX, float curY) {
        Paint paint = AttributesTool.getInstance().getPaint();
        //判断手指的方向
        if(firstX < curX && firstY < curY){
            canvas.drawOval(firstX,firstY,curX,curY,paint);
        }else if(firstX > curX && firstY > curY){
            canvas.drawOval(curX,curY,firstX,firstY,paint);
        }else if(firstX > curX && firstY < curY){
            canvas.drawOval(curX,firstY,firstX,curY,paint);
        }else if(firstX < curX && firstY>curY){
            canvas.drawOval(firstX,curY,curX,firstY,paint);
        }
    }
}
