package com.example.zujianapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Sweep extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
    private float mRotate ;
    private Matrix matrix = new Matrix();
    private Shader mShader ;

    public Sweep(Context context) {
        this(context,null);
    }

    public Sweep(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);

        float x =160 ;
        float y = 100 ;
        mShader = new SweepGradient(x,y,
                new int[]{Color.GRAY,
                Color.RED,Color.BLUE,Color.GREEN},null);
        paint.setShader(mShader) ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint1 = paint ;
        float x = 160 ;
        float y = 100 ;
       canvas.translate(300,300);
        canvas.drawColor(Color.WHITE);
        matrix.setRotate(mRotate,x,y);
        mShader.setLocalMatrix(matrix);
        mRotate +=3;
        if(mRotate >= 360){
            mRotate = 0 ;
        }
        invalidate();
        canvas.drawCircle(x,y,380,paint1);
    }
}
