package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SweepGradientView extends View {
    public SweepGradientView(Context context) {
        this(context,null);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        SweepGradient sg = new SweepGradient(300,300,new int[]{
                Color.GREEN,
                Color.YELLOW,
                Color.RED,
                Color.GREEN
        },null);
        paint.setShader(sg) ;
        canvas.drawOval(new RectF(0,0,600,600),paint);
    }
}
