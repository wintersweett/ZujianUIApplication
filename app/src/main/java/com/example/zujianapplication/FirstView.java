package com.example.zujianapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.zujianapplication.tools.Utils;
import com.example.zujianuiapplication.R;

public class FirstView extends View {
    private static final String TEXT = "lalalal" ;
    private Paint paint ;
    public FirstView(Context context) {
        this(context,null);
    }

    public FirstView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FirstView) ;
        array.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        paint.setTextSize(100);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect textRect = getTextRect() ;
        int viewWidth = getMeasuredWidth() ;
        int viewHeight = getMeasuredHeight() ;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int x =(viewWidth-textRect.width())/2 ;
        int y =(int)(viewHeight/2+
                (fontMetrics.descent-fontMetrics.ascent)/2
                -fontMetrics.descent
                );
        canvas.drawText(TEXT,x,y,paint);
    }

    private Rect getTextRect() {
        Rect rect = new Rect() ;
        //文字所占的区域大小保存在rect中
        paint.getTextBounds(TEXT,0,TEXT.length(),rect);
        Log.d(Utils.TAG, "getTextRect: ");
        return rect;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec) ;
        int height = measureHeight(heightMeasureSpec) ;
        setMeasuredDimension(width,height);

    }
    private int measureWidth(int widthMeasureSpec){
        int mode= MeasureSpec.getMode(widthMeasureSpec) ;
        int size = MeasureSpec.getSize(widthMeasureSpec) ;
        int width = 0 ;
        if(mode == MeasureSpec.EXACTLY){
            width = size ;
        }else if(mode == MeasureSpec.AT_MOST){
            width = getPaddingLeft() +getTextRect().width()+getPaddingRight() ;

        }
        return width ;
    }
    private int measureHeight(int heightMeasureSpec){
        int mode = MeasureSpec.getMode(heightMeasureSpec) ;
        int size = MeasureSpec.getSize(heightMeasureSpec) ;
        int height = 0 ;
        if(mode == MeasureSpec.EXACTLY){
            height = size ;
        }else if(mode == MeasureSpec.AT_MOST){

        }
        return height ;
    }
}
