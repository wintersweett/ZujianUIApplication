package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BallMoveView extends View {
    private int x ;
    private static final int Y = 100 ;
    private static final int RADIUS =30 ;
    private static final int COLOR = Color.RED ;
    private Paint paint ;
    private boolean direction ;
    public BallMoveView(Context context) {
        this(context,null);
    }

    public BallMoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        paint.setColor(COLOR);
        x = RADIUS ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x,Y,RADIUS,paint);
        int width = this.getMeasuredWidth() ;
        if(x <= RADIUS){
            direction = true ;
        }
        if(x >= width-RADIUS){
            direction = false ;
        }
        x = direction ?x+5 :x-5 ;
    }
}
